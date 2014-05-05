package com.zephyrus.wind.workflow;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PORT_STATUS;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.PortStatus;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;

/**
 * This class provides functionality for "Modify" scenario workflow
 * @see com.zephyrus.wind.workflow.Workflow
 * @author Igor Litvinenko
 */
public class ModifyScenarioWorkflow extends Workflow {
	
	/**
     * This method creates ModifyScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param factory DAO implementations' factory
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "Modify" scenario
     * workflow or if SI is not active
     */
    public ModifyScenarioWorkflow(OracleDAOFactory factory, ServiceOrder order) {
        super(factory, order);
        
		if (order.getOrderType().getId() != ORDER_TYPE.MODIFY.getId()) {
            throw new WorkflowException("Cannot proceed Order: wrong order scenario");
        }
    }
    
    /**
     * This method proceeds given Order under Modify scenario.
     * No tasks are created for Workflow under Modify scenario.
     * It automatically completes current Order and sends notification to User.
     * Order should have status "Entering" and workflow scenario "Modify"
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
            createTask(ROLE.PROVISION);
        } catch (Exception exc) {
        	throw new WorkflowException("Exception while proceeding order", exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * This method allows Provisioning Engineer to update Circuit configuration
     * as part of the Modify scenario workflow. Note that this action automatically
     * completes Order. It also updates SI date because of the billing that should
     * now be performed differently due to service price change.
     * @param taskID ID of task for Provisioning Engineer
     * @param circuitConfig configuration of circuit
     */
    public void updateCircuitConfig(int taskID, String circuitConfig) {
    	lock.lock();
    	try {
            if (!isTaskValid(taskID, ROLE.PROVISION.getId())) {
                throw new WorkflowException("Given Task is not valid");
            }
            
            if(circuitConfig == null) {
            	throw new WorkflowException("Wrong circuit config");
            }
            
            // Change product ID for Service Instance
            ServiceInstance si = order.getServiceInstance();
            IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
            si.setProductCatalog(order.getProductCatalog());
            siDAO.update(si);
            
            // Update circuit config
            Circuit circuit = si.getCircuit();
            ICircuitDAO circuitDAO = factory.getCircuitDAO();
            circuit.setConfig(circuitConfig);
            circuitDAO.update(circuit);
            
            completeTask(taskID);

            updateServiceInstanceDate(order.getServiceInstance());
            this.completeOrder();
        } catch (Exception exc) {
			throw new WorkflowException("Circuit configuration failed", exc);
		} finally {
			lock.unlock();
		}
    }
}
