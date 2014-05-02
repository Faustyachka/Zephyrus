package com.zephyrus.wind.workflow;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
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
		
		ServiceInstance si = order.getServiceInstance();
		if(si.getServInstanceStatus().getId() != SERVICEINSTANCE_STATUS.ACTIVE.getId()) {
			throw new WorkflowException("Service Instance associated with current Order "
					+ "is not Active");
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
            
            // Change product ID for Service Instance
            IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
            si.setProductCatalog(order.getProductCatalog());
            siDAO.update(si);
            
            updateServiceInstanceDate(order.getServiceInstance());
            changeOrderStatus(ORDER_STATUS.COMPLETED);
        } catch (Exception exc) {
        	throw new WorkflowException("Exception while proceeding order", exc);
		} finally {
			lock.unlock();
		}
    }
}
