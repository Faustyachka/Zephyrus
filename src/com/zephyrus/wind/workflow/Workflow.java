package com.zephyrus.wind.workflow;

import java.io.Closeable;
import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.locks.Lock;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.email.Email;
import com.zephyrus.wind.email.EmailSender;
import com.zephyrus.wind.email.NewTaskEmail;
import com.zephyrus.wind.email.OrderCompletedEmail;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.enums.TASK_STATUS;
import com.zephyrus.wind.managers.LockManager;
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
public abstract class Workflow implements Closeable {
	
	/**
	 * Lock is used to achieve continuous
	 * code execution of some crucial code blocks that should not be
	 * run simultaneously due to validation issues
	 */
    protected Lock lock;
	
    /** Service Order for which workflow was created */
    protected ServiceOrder order;
    
    /** DAO implementations' factory */
    protected OracleDAOFactory factory;

    public Workflow(OracleDAOFactory factory, ServiceOrder order) {
    	this.lock = LockManager.getLock(order.getId());
        this.factory = factory;
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
    	lock.lock();
		try {
			ITaskDAO taskDAO = factory.getTaskDAO();
			
            Task task = taskDAO.findById(taskID);
            User user = factory.getUserDAO().findById(userID);
            
            if (isTaskValid(taskID, user.getRole().getId())) {
                task.setUser(user);
                taskDAO.update(task);
            } else {
                throw new WorkflowException("Given Task is not valid");
            }
		} catch (Exception exc) {
			throw new WorkflowException("Assign task exception", exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * This method allows to suspend given task so that it cannot be executed.
     * Note that you could suspend only tasks assigned to particular user.
     * @param taskID ID of active task to suspend
     */
    public void suspendTask(int taskID) {
    	lock.lock();
		try {
			ITaskDAO taskDAO = factory.getTaskDAO();
			ITaskStatusDAO taskStatusDAO = factory.getTaskStatusDAO();
			
            Task task = taskDAO.findById(taskID);
            
            if (task.getServiceOrder().getId() != order.getId()) {
                throw new WorkflowException("Given task is not connected "
                		+ "with current order");
            } else if (task.getTaskStatus().getId() != TASK_STATUS.PROCESSING.getId()) {
            	throw new WorkflowException("Given task is not active at the moment");
            } else if (task.getUser() == null) {
            	throw new WorkflowException("Given task was not assigned to anyone");
            }
            
            TaskStatus suspendedStatus = taskStatusDAO.findById(TASK_STATUS.SUSPEND.getId());
            task.setTaskStatus(suspendedStatus);
            taskDAO.update(task);
		} catch (Exception exc) {
			throw new WorkflowException("Suspend task failed", exc);
		} finally {
			lock.unlock();
		}
    }
    
    /**
     * This method allows to renew given task so that it can be executed.
     * @param taskID ID of suspended task to renew
     */
    public void renewTask(int taskID) {
    	lock.lock();
		try {
			ITaskDAO taskDAO = factory.getTaskDAO();
			ITaskStatusDAO taskStatusDAO = factory.getTaskStatusDAO();
			
            Task task = taskDAO.findById(taskID);
            
            if (task.getServiceOrder().getId() != order.getId()) {
                throw new WorkflowException("Given task is not connected "
                		+ "with current order");
            } else if (task.getTaskStatus().getId() != TASK_STATUS.SUSPEND.getId()) {
            	throw new WorkflowException("Given task is not suspended");
            } 
            
            TaskStatus suspendedStatus = taskStatusDAO.findById(TASK_STATUS.PROCESSING.getId());
            task.setTaskStatus(suspendedStatus);
            taskDAO.update(task);
		} catch (Exception exc) {
			throw new WorkflowException("Renew task failed", exc);
		} finally {
			lock.unlock();
		}
    }

    /**
     * This method is used to create tasks and assign it to user groups.
     * Method is <code>protected</code> because it can only be invoked in
     * Workflow methods.
     * @param userRole identifies user group to create task for
     */
    protected void createTask(ROLE role) throws Exception {
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
        task = taskDAO.insert(task);
        
        /* Send email to user group */
        User user = order.getServiceInstance().getUser();
        Email email = new NewTaskEmail(order.getOrderDate(), user.getFirstName(), 
        		user.getLastName(), task.getId());
        EmailSender sender = new EmailSender();
        sender.sendEmail(role, email);
    }

    /**
     * This method sets task status to "Completed".
     * Method is <code>protected</code> because it can only be invoked in
     * Workflow methods.
     * 
     * @param taskID ID of task
     */
    protected void completeTask(int taskID) throws Exception {
        ITaskDAO taskDAO = factory.getTaskDAO();
        ITaskStatusDAO taskStatusDAO = factory.getTaskStatusDAO();
        
        Task task = taskDAO.findById(taskID);
        if(task.getTaskStatus().getId() != TASK_STATUS.PROCESSING.getId()) {
        	throw new WorkflowException("Given task is not active");
        }
        
        TaskStatus status = taskStatusDAO.findById(TASK_STATUS.COMPLETE.getId());
        task.setTaskStatus(status);
        taskDAO.update(task);
    }
    
    protected void changeServiceInstanceStatus(SERVICEINSTANCE_STATUS status) 
    		throws Exception {
    	
    	IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
    	IServiceInstanceStatusDAO sisDAO = factory.getServiceInstanceStatusDAO();
    	ServiceInstanceStatus instanceStatus  = sisDAO.findById(status.getId());
    	
    	ServiceInstance si = order.getServiceInstance();
    	si.setServInstanceStatus(instanceStatus);
        siDAO.update(si);
    }

    protected void changeOrderStatus(ORDER_STATUS status) 
    		throws Exception {
    	
        IServiceOrderDAO orderDAO = factory.getServiceOrderDAO();
        IOrderStatusDAO orderStatusDAO = factory.getOrderStatusDAO();

        OrderStatus orderStatus = orderStatusDAO.findById(status.getId());
        order.setOrderStatus(orderStatus);
        orderDAO.update(order);
    }
    
    /**
     * This method completes current order by setting order status to "Completed"
     * and sends email notification to the user.
     */
    protected void completeOrder() throws Exception {
    	this.changeOrderStatus(ORDER_STATUS.COMPLETED);
    	
    	User user = order.getServiceInstance().getUser();
    	Email email = new OrderCompletedEmail(user.getFirstName(), order.getOrderDate(),
    			order.getOrderType().getOrderType(), order.getServiceLocation().getAddress());
    	EmailSender sender = new EmailSender();
    	sender.sendEmail(user.getEmail(), email);
    }

    /**
     * This method checks whether given Task is active, connected with
     * current Order and was created for given User Role
     * @param taskID ID of Task to validate
     * @param userRoleID ID of User Role the Task was created for
     * @return <code>true</code> if Task is valid for execution and
     * <code>false</code> otherwise
     * @throws Exception 
     */
    protected boolean isTaskValid(int taskID, int userRoleID) throws Exception {
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
    
    /**
     * Sets SI creation date to current date
     * @param si Service Instance to update date for
     */
    protected void updateServiceInstanceDate(ServiceInstance si) 
    		throws Exception {
    	
        IServiceInstanceDAO siDAO = factory.getServiceInstanceDAO();
        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        si.setStartDate(date);
        siDAO.update(si);
    }
    
    /**
     * Removes lock associated with current order
     */
    public void close() {
		LockManager.removeLock(order.getId());
    }
}
