package com.zephyrus.wind.workflow;

import java.util.concurrent.locks.Lock;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PORT_STATUS;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.managers.LockManager;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.PortStatus;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;

/**
 * This class provides functionality for "New" scenario workflow
 * @see com.zephyrus.wind.workflow.Workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow extends Workflow {

    /**
     * This method creates NewScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param factory DAO implementations' factory
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "New" scenario
     * workflow
     */
    public NewScenarioWorkflow(OracleDAOFactory factory, ServiceOrder order) {
        super(factory, order);
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
    public void proceedOrder() {
    	lock.lock();
        try {
            if (order.getOrderStatus().getId() != ORDER_STATUS.ENTERING.getId()) {
                throw new WorkflowException("Cannot proceed Order: wrong order state");
            }

            changeOrderStatus(ORDER_STATUS.PROCESSING);

            // Link Order with SI
            IServiceOrderDAO orderDAO = factory.getServiceOrderDAO();
            ServiceInstance serviceInstance = createServiceInstance();
            order.setServiceInstance(serviceInstance);
            orderDAO.update(order);

            /*
             * task for IE is created, whether or not we have free ports,
             * because physical link to customer is always absent for "new"
             * scenario, so we have to create it manually
             */
            createTask(ROLE.INSTALLATION);
        } catch (Exception exc) {
        	throw new WorkflowException("Exception while proceeding order", exc);
		} finally {
			lock.unlock();
		}
    }

    private ServiceInstance createServiceInstance() throws Exception {
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
     * @return created Device
     */
    public Device createRouter(int taskID, String serialNumber, int portQuantity) {
        Lock deviceLock = LockManager.getLock(Workflow.class);
        deviceLock.lock();
    	try {
            if (!isTaskValid(taskID, ROLE.INSTALLATION.getId())) {
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

            IPortStatusDAO portStatusDAO = factory.getPortStatusDAO();
            PortStatus freePortStatus = portStatusDAO.findById(PORT_STATUS.FREE.getId());
            for (int portNumber = 1; portNumber <= portQuantity; portNumber++) {
                Port port = new Port();
                port.setDevice(device);
                port.setPortStatus(freePortStatus);		
                port.setPortNumber(portNumber);
                portDAO.insert(port);
            }
            
            return device;
        } catch (Exception exc) {
			throw new WorkflowException("Router creation failed", exc);
		} finally {
			deviceLock.unlock();
		}
    }

    /**
     * This method creates Cable for current Service Location
     * @param taskID ID of task for installation engineer
     * @return created Cable
     */
    public Cable createCable(int taskID) {
    	lock.lock();
        try {
            if (!isTaskValid(taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }

            ICableDAO cableDAO = factory.getCableDAO();

            Cable cable = new Cable();
            cable.setCableType("UTP");
            cable.setPort(null); // no port associated with device so far
            cable.setServiceLocation(order.getServiceLocation());
            cableDAO.insert(cable);
            
            return cable;
        } catch (Exception exc) {
        	throw new WorkflowException("Cable creation failed", exc);
		} finally {
			lock.unlock();
		}
    }

    /**
     * This method plugs Cable to available free Port.
     * It changes status of Task to "Completed" after execution
     * After it's done method automatically creates task for Provisioning Engineer
     * @param taskID taskID ID of task for installation engineer
     */
    public void plugCableToPort(int taskID) {
    	lock.lock();
        try {
            if (!isTaskValid(taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
            
            IPortDAO portDAO = factory.getPortDAO();
            int portID = portDAO.findFreePortID();
            if (portID == 0) {
                throw new WorkflowException("No free ports are available. "
                		+ "Create Router first.");
            }
            Port port = portDAO.findById(portID);
            
            // update Port status to "Busy"
            PortStatus portStatus = factory.getPortStatusDAO().findById(PORT_STATUS.BUSY.getId());
            port.setPortStatus(portStatus);
            portDAO.update(port);
            
            // link Cable with Port
            ICableDAO cableDAO = factory.getCableDAO();
            Cable cable = cableDAO.findCableFromServLoc(order.getServiceLocation().getId());
            if(cable == null) {
            	throw new WorkflowException("No Cable found for current SI");
            }
            cable.setPort(port);
            cableDAO.update(cable);

            this.completeTask(taskID);
            this.createTask(ROLE.PROVISION);
        } catch (Exception exc) {
        	throw new WorkflowException("Failed to plug Cable to the Port", exc);
		} finally {
			lock.unlock();
		}
    }

    /**
     * Creates new Circuit Instance. SI is automatically linked with Circuit.
     * It automatically completes current Order and sends notification to User. 
     * It also sets SI status to "Active".
     * @param taskID taskID ID of task for provisioning engineer
     * @param circuitConfig logical port configuration
     * @return created Circuit
     */
    public Circuit createCircuit(int taskID, String circuitConfig) {
    	lock.lock();
        try {
        	if (!isTaskValid(taskID, ROLE.PROVISION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
        	
            ICircuitDAO circuitDAO = factory.getCircuitDAO();
            IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
            
            Port port = getPortByCustomer();
            
            Circuit circuit = new Circuit();
            circuit.setPort(port);
            circuit.setConfig(circuitConfig);
            circuit = circuitDAO.insert(circuit);

            // link Circuit with SI
            ServiceInstance si = order.getServiceInstance();
            if(si.getCircuit() != null) {
            	throw new WorkflowException("Circuit for current SI already exists");
            }
            si.setCircuit(circuit);
            siDAO.update(si);
            
            completeTask(taskID);

            updateServiceInstanceDate(order.getServiceInstance());
            changeServiceInstanceStatus(SERVICEINSTANCE_STATUS.ACTIVE);
            this.completeOrder();
            
            return circuit;
        } catch (Exception exc) {
        	throw new WorkflowException("Circuit creation failed", exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * Method obtains Port assigned by Installation engineer to current Order
     * @return Port for current order
     */
    private Port getPortByCustomer() throws Exception {
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
}

