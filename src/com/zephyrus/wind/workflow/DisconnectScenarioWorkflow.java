package com.zephyrus.wind.workflow;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PORT_STATUS;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.PortStatus;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;

/**
 * This class provides functionality for "Disconnect" scenario workflow
 * @see com.zephyrus.wind.workflow.Workflow
 * @author Igor Litvinenko
 */
public class DisconnectScenarioWorkflow extends Workflow {
	
	/**
     * This method creates DisconnectScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param factory DAO implementations' factory
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "Disconnect" scenario
     * workflow
     */
    public DisconnectScenarioWorkflow(OracleDAOFactory factory, ServiceOrder order) {
        super(factory, order);
		if (order.getOrderType().getId() != ORDER_TYPE.DISCONNECT.getId()) {
            throw new WorkflowException("Cannot proceed Order: wrong order scenario");
        }
    }
    
    /**
     * This method proceeds given Order under Disconnect scenario.
     * Order should have status "Entering" and workflow scenario "Disconnect"
     */
    @Override
    public void proceedOrder() {
    	lock.lock();
        try {
            if (order.getOrderStatus().getId() != ORDER_STATUS.ENTERING.getId()) {
                throw new WorkflowException("Cannot proceed Order: wrong order state");
            }
            
            ServiceInstance si = order.getServiceInstance();
    		if(si.getServInstanceStatus().getId() != SERVICEINSTANCE_STATUS.ACTIVE.getId()) {
    			throw new WorkflowException("Service Instance associated with current Order "
    					+ "is not Active");
    		}
            
            changeOrderStatus(ORDER_STATUS.PROCESSING);
            changeServiceInstanceStatus(SERVICEINSTANCE_STATUS.PENDINGDISCONNECTION);
            createTask(ROLE.PROVISION);
        } catch (Exception exc) {
        	throw new WorkflowException("Exception while proceeding order", exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * This method deletes Circuit, connected with current SI.
     * It also sets SI status to "Disconnected"
     * @param taskID ID of task for Provisioning Engineer
     */
    public void deleteCircuit(int taskID) {
    	lock.lock();
    	try {
            if (!isTaskValid(taskID, ROLE.PROVISION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
            
            ServiceInstance si = order.getServiceInstance();
            if(si.getCircuit() == null) {
            	throw new WorkflowException("No Circuit exist for current SI");
            }
            
            // delete Circuit
            ICircuitDAO circuitDAO = factory.getCircuitDAO();
            circuitDAO.remove(si.getCircuit());
            
            // unlink Circuit from SI
            si.setCircuit(null);
            this.changeServiceInstanceStatus(SERVICEINSTANCE_STATUS.DISCONNECTED);
            
            completeTask(taskID);
            createTask(ROLE.INSTALLATION);
        } catch (Exception exc) {
			throw new WorkflowException("Circuit deletion failed: " + exc, exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * This method unplugs Cable from Port, associated with current Service Instance.
     * It sets Port status to "Free" after execution.
     * @param taskID ID of task for Provisioning Engineer
     */
    public void unplugCableFromPort(int taskID) {
    	lock.lock();
        try {
            if (!isTaskValid(taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
            
            Circuit circuit = order.getServiceInstance().getCircuit();
            Port port = circuit.getPort();
            
            PortStatus portStatus = port.getPortStatus();
            if(portStatus.getId() != PORT_STATUS.BUSY.getId()) {
            	throw new WorkflowException("Port isn't busy at the moment");
            }
            
            // update Port status to "Free"
            IPortDAO portDAO = factory.getPortDAO();
            portStatus = factory.getPortStatusDAO().findById(PORT_STATUS.FREE.getId());
            port.setPortStatus(portStatus);
            portDAO.update(port);
            
            // unlink Cable from Port
            ICableDAO cableDAO = factory.getCableDAO();
            Cable cable = cableDAO.findCableFromServLoc(order.getServiceLocation().getId());
            cable.setPort(null);
            cableDAO.update(cable);
            
        } catch (Exception exc) {
        	throw new WorkflowException("Failed to unplug Cable from Port", exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * This method deletes Cable associated with current Service Location
     * @param taskID ID of task for Provisioning Engineer
     */
    public void deleteCable(int taskID) {
    	lock.lock();
    	try {
            if (!isTaskValid(taskID, ROLE.INSTALLATION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
            
            ICableDAO cableDAO = factory.getCableDAO();
            Cable cable = cableDAO.findCableFromServLoc(order.getServiceLocation().getId());
            if(cable == null) {
            	throw new WorkflowException("No Cable found for current Service Location");
            }
            cableDAO.remove(cable);
            
            completeTask(taskID);
            changeOrderStatus(ORDER_STATUS.COMPLETED);
        } catch (Exception exc) {
			throw new WorkflowException("Cable deletion failed: " + exc, exc);
		} finally {
			lock.unlock();
		}
    }
}
