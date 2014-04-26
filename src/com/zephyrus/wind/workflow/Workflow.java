package com.zephyrus.wind.workflow;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.enums.TASK_STATUS;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.TaskStatus;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * This class provides functionality of managing workflow for particular Order.
 * All task and order management focused here.
 * @author Igor Litvinenko
 */
public abstract class Workflow {

    protected ServiceOrder order; // Service Order for which workflow was created

    public Workflow(ServiceOrder order) {
        this.order = order;
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution
     */
    public abstract void proceedOrder();

    /**
     * This method assigns task to particular user of user group
     * responsible for task execution
     * @param taskID ID of task to assign user to
     * @param userID ID of user to assign
     * @throws WorkflowException if task is not valid
     */
    public void assignTask(int taskID, int userID) {
    	OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			ITaskDAO taskDAO = factory.getTaskDAO();

            Task task = taskDAO.findById(taskID);
            User user = task.getUser();
            if (isTaskValid(factory, taskID, user.getRole().getId())) {
                task.setUser(user);
                taskDAO.update(task);
            } else {
                throw new WorkflowException("Given Task is not valid");
            }
            factory.commitTransaction();
		} catch (Exception exc) {
			throw new WorkflowException("Assign task exception", exc);
		} finally {
			factory.endConnection();
		}
    }

    /**
     * This method is used to create tasks and assign it to user groups.
     * Method is <code>protected</code> because it can only be invoked in
     * Workflow methods.
     * @param factory DAO implementations factory
     * @param userRole identifies user group to create task for
     */
    protected void createTask(OracleDAOFactory factory, ROLE role) throws Exception {
        ITaskDAO taskDAO = factory.getTaskDAO();
        ITaskStatusDAO statusDAO = factory.getTaskStatusDAO();
        IUserRoleDAO roleDAO = factory.getUserRoleDAO();
        
        UserRole userRole = roleDAO.findById(role.getId());
        
        Task task = new Task();
        task.setUser(null);
        task.setRole(userRole);
        task.setServiceOrder(order);
        TaskStatus status = statusDAO.findById(TASK_STATUS.PROCESSING.getId());
        task.setTaskStatus(status);
        taskDAO.insert(task);
    }

    /**
     * This method sets task status to "Completed".
     * Method is <code>protected</code> because it can only be invoked in
     * Workflow methods.
     * @param factory DAO implementations factory
     * @param taskID ID of task
     */
    protected void completeTask(OracleDAOFactory factory, int taskID) throws Exception {
        ITaskDAO taskDAO = factory.getTaskDAO();
        ITaskStatusDAO taskStatusDAO = factory.getTaskStatusDAO();

        Task task = taskDAO.findById(taskID);
        TaskStatus status = taskStatusDAO.findById(TASK_STATUS.COMPLETE.getId());
        task.setTaskStatus(status);
        taskDAO.update(task);
    }
    
    protected void changeServiceInstanceStatus(OracleDAOFactory factory, 
    		SERVICEINSTANCE_STATUS status) throws Exception {
    	
    	IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
    	IServiceInstanceStatusDAO sisDAO = factory.getServiceInstanceStatusDAO();
    	ServiceInstanceStatus instanceStatus  = sisDAO.findById(status.getId());
    	
    	ServiceInstance si = order.getServiceInstance();
    	si.setServInstanceStatus(instanceStatus);
        siDAO.update(si);
    }

    protected void changeOrderStatus(OracleDAOFactory factory, ORDER_STATUS status) 
    		throws Exception {
    	
        IServiceOrderDAO orderDAO = factory.getServiceOrderDAO();
        IOrderStatusDAO orderStatusDAO = factory.getOrderStatusDAO();

        OrderStatus orderStatus = orderStatusDAO.findById(status.getId());
        order.setOrderStatus(orderStatus);
        orderDAO.update(order);
    }

    /**
     * This method checks whether given Task is active, connected with
     * current Order and was created for given User Role
     * @param factory DAO implementations factory
     * @param taskID ID of Task to validate
     * @param userRoleID ID of User Role the Task was created for
     * @return <code>true</code> if Task is valid for execution and
     * <code>false</code> otherwise
     * @throws Exception 
     */
    protected boolean isTaskValid(OracleDAOFactory factory, int taskID, int userRoleID) 
    		throws Exception {
    	
        ITaskDAO taskDAO = factory.getTaskDAO();
        Task task = taskDAO.findById(taskID);
        
        if (task.getServiceOrder().getId() != order.getId()) {
            return false;
        } else if (task.getRole().getId() != userRoleID) {
            return false;
        } else if (task.getTaskStatus().getId() != TASK_STATUS.PROCESSING.getId()) {
            return false;
        } else {
            return true;
        }
    }
}
