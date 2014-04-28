package com.zephyrus.wind.workflow;

import java.sql.Date;
import java.util.Calendar;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;

/**
 * This class provides functionality for "New" scenario workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow extends Workflow {

    /**
     * This method creates NewScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "New" scenario
     * workflow
     */
    public NewScenarioWorkflow(ServiceOrder order) {
        super(order);
		if (order.getOrderType().getId() != ORDER_TYPE.NEW.getId()) {
            throw new WorkflowException("Cannot proceed Order: wrong order scenario");
        }
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution.
     * Order should have status "Entering" and workflow scenario "New"
     */
    @Override
    public void proceedOrder(OracleDAOFactory factory) {
        try {
            if (order.getOrderStatus().getId() != ORDER_STATUS.ENTERING.getId()) {
                throw new WorkflowException("Cannot proceed Order: wrong order state");
            }

            IOrderStatusDAO orderStatusDAO = factory.getOrderStatusDAO();
            IServiceOrderDAO orderDAO = factory.getServiceOrderDAO();
            
            OrderStatus orderStatus = orderStatusDAO.findById(ORDER_STATUS.PROCESSING.getId());
            order.setOrderStatus(orderStatus);
            ServiceInstance serviceInstance = createServiceInstance(factory);

            // Link Order with SI
            order.setServiceInstance(serviceInstance);
            orderDAO.update(order);

            /*
             * task for IE is created, whether or not we have free ports,
             * because physical link to customer is always absent for "new"
             * scenario, so we have to create it manually
             */
            createTask(factory, ROLE.INSTALLATION);
        } catch (Exception exc) {
        	throw new WorkflowException("Exception while proceeding order", exc);
		}
    }

    private ServiceInstance createServiceInstance(OracleDAOFactory factory) throws Exception {
        IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
        IServiceInstanceStatusDAO sisDAO = factory.getServiceInstanceStatusDAO();

        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setCircuit(null);
        serviceInstance.setUser(order.getServiceLocation().getUser());
        serviceInstance.setProductCatalog(order.getProductCatalog());
        serviceInstance.setStartDate(null); // date is not set because SI wasn't activated yet
       	ServiceInstanceStatus status = sisDAO.findById(SERVICEINSTANCE_STATUS.PLANNED.getId());
        serviceInstance.setServInstanceStatus(status);

        serviceInstance = siDAO.insert(serviceInstance);

        return serviceInstance;
    }

    /**
     * This method creates new Router in system. It also creates Ports
     * and links them with Router.
     * @param taskID ID of task for installation engineer        
     * @param serialNumber String representing serial number of device
     * @param portQuantity amount of Ports that Router accommodates
     */
    public void createRouter(int taskID, String serialNumber, int portQuantity) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        try {
        	factory.beginConnection();
            if (!isTaskValid(factory, taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }

            IDeviceDAO deviceDAO = factory.getDeviceDAO();
            IPortDAO portDAO = factory.getPortDAO();

            if (portDAO.findFreePortID() != 0) {
                throw new WorkflowException("Router creation is not allowed: " +
                        "free ports available");
            }

            Device device = new Device();
            device.setSerialNum(serialNumber);
            device = deviceDAO.insert(device);

            for (int portNumber = 1; portNumber <= portQuantity; portNumber++) {
                Port port = new Port();
                port.setDevice(device);
                port.setPortNumber(portNumber);
                portDAO.insert(port);
            }

            factory.commitTransaction();
        } catch (Exception exc) {
			throw new WorkflowException("Router creation failed", exc);
		} finally {
            factory.endConnection();
        }
    }

    /**
     * This method creates Cable by specified service location and Cable type 
     * @param taskID ID of task for installation engineer
     */
    public void createCable(int taskID) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        try {
        	factory.beginConnection();
            if (!isTaskValid(factory, taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }

            ICableDAO cableDAO = factory.getCableDAO();

            Cable cable = new Cable();
            cable.setCableType("UTP");
            cable.setPort(null); // no port associated with device so far
            cable.setServiceLocation(order.getServiceLocation());
            cableDAO.insert(cable);

            factory.commitTransaction();
        } catch (Exception exc) {
        	throw new WorkflowException("Cable creation failed", exc);
		} finally {
        	factory.endConnection();
        }
    }

    /**
     * This method plugs Cable to specified free Port.
     * It changes status of Task to "Completed" after execution
     * After it's done method automatically creates task for Provisioning Engineer
     * @param taskID taskID ID of task for installation engineer
     * @param cable Cable to plug to the Port
     * @param port Port to plug Cable to
     */
    public void plugCableToPort(int taskID, Cable cable, Port port) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        try {
        	factory.beginConnection();
            if (!isTaskValid(factory, taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
            
            ICableDAO cableDAO = factory.getCableDAO();
            cable.setPort(port);
            cableDAO.update(cable);

            this.completeTask(factory, taskID);
            this.createTask(factory, ROLE.PROVISION);
            factory.commitTransaction();
        } catch (Exception exc) {
        	throw new WorkflowException("Failed to plug Cable to the Port", exc);
		} finally {
        	factory.endConnection();
        }
    }

    /**
     * Creates new Circuit Instance
     * @param taskID taskID ID of task for provisioning engineer
     * @param circuitConfig logical port configuration
     */
    public void createCircuit(int taskID, String circuitConfig) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        try {
        	factory.beginConnection();
        	if (!isTaskValid(factory, taskID, ROLE.PROVISION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
        	
            ICircuitDAO circuitDAO = factory.getCircuitDAO();
            IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
            
            Port port = getPortByCustomer(factory);
            
            Circuit circuit = new Circuit();
            circuit.setPort(port);
            circuit.setConfig(circuitConfig);
            circuit = circuitDAO.insert(circuit);

            // link Circuit with SI
            ServiceInstance si = order.getServiceInstance();
            si.setCircuit(circuit);
            siDAO.update(si);

            this.completeTask(factory, taskID);
            this.createTask(factory, ROLE.SUPPORT);
            factory.commitTransaction();
        } catch (Exception exc) {
        	throw new WorkflowException("Circuit creation failed", exc);
		} finally {
        	factory.endConnection();
        }
    }
    
    /**
     * Method obtains Port assigned by Installation engineer to current Order
     * @param factory DAO implementations factory
     * @return Port for current order
     */
    private Port getPortByCustomer(OracleDAOFactory factory) throws Exception {
    	ServiceLocation location = order.getServiceLocation();
        ICableDAO cableDAO = factory.getCableDAO();
        Cable cable = cableDAO.findCableFromServLoc(location.getId());
        if(cable == null) {
        	throw new WorkflowException("No cable attached to customer location");
        }
        Port port = cable.getPort();
        if(port == null) {
        	throw new WorkflowException("No link between Service Location and Router");
        }
        return port;
    }

    /**
     * This method approves and sends Bill to customer and automatically activates SI
     * by changing it's status to "Active". It also changes Order status to
     * "Completed"
     * @param taskID ID of Task for Support Engineer
     */
    public void approveBill(int taskID) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        try {
        	factory.beginConnection();
        	if (!isTaskValid(factory, taskID, ROLE.SUPPORT.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }

            completeTask(factory, taskID);

            updateServiceInstanceDate(factory, order.getServiceInstance());
            changeServiceInstanceStatus(factory, SERVICEINSTANCE_STATUS.ACTIVE);
            changeOrderStatus(factory, ORDER_STATUS.COMPLETED);
            // TODO: send email here
            factory.commitTransaction();
        } catch (Exception exc) {
        	throw new WorkflowException("Failed to approve Bill", exc);
		} finally {
        	factory.endConnection();
        }
    }

    /**
     * Sets SI creation date to current date
     * @param factory DAO implementations factory
     * @param si Service Instance to update date for
     */
    private void updateServiceInstanceDate(OracleDAOFactory factory, ServiceInstance si) 
    		throws Exception {
    	
        IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        si.setStartDate(date);
        siDAO.update(si);
    }
}

