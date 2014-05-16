package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.managers.LockManager;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.DisconnectScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in 
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. 
 * Method realized SI disconnect
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
 * @author Miroshnychenko Nataliya
 */

public class DisconnectServiceInstanceCommand extends SQLCommand {

	private ServiceOrder disconnectOrder = null;

	/**
	 * This method started disconnect scenario workflow
	 * Method gets parameter of service instance ID that will be disconnect
	 * 
	 * @see com.zephyrus.wind.model.ServiceInstance
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
	 * 
	 * @return page with confirmation of successful creation of disconnect order 			
	 * Also can return the error page if the received Service Instance ID is not valid
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		int serviceInstanceID;

		User user = (User) request.getSession().getAttribute("user");

		if (user == null || user.getRole().getId() != ROLE.CUSTOMER.getId()) {
			request.setAttribute("messageNumber", MessageNumber.USER_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		if(request.getParameter("id") == null){
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		IServiceInstanceDAO serviceInstanceDAO = getOracleDaoFactory().getServiceInstanceDAO();

		try {
			serviceInstanceID = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		Lock lock = LockManager.getLock(serviceInstanceID);
		lock.lock();
		DisconnectScenarioWorkflow workflow = null;
		try {
			ServiceInstance serviceInstance = serviceInstanceDAO.findById(serviceInstanceID);	

			if (serviceInstance == null || serviceInstance.getUser().getId() != user.getId()) {
				request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
				return PAGES.MESSAGE_PAGE.getValue();
			}
			
			if (serviceInstance.getServInstanceStatus().getId() != SERVICEINSTANCE_STATUS.ACTIVE.getId()){
				request.setAttribute("messageNumber", MessageNumber.SERVICE_STATUS_ERROR.getId());
				return PAGES.MESSAGE_PAGE.getValue();
			}
			disconnectOrder =  createDisconnectOrder(serviceInstance);
			workflow = new DisconnectScenarioWorkflow(getOracleDaoFactory(), disconnectOrder); 
			workflow.proceedOrder();
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("serviceInstanceID", serviceInstanceID);
			request.setAttribute("error", "Failed to disconnect Service Instance");
			return "customerServices";
		} finally {
			workflow.close();
			lock.unlock();
		}

		request.setAttribute("messageNumber", MessageNumber.SI_DISCONNECT_COMPLETED_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}


	/**
	 * This method create disconnect order for disconnect scenario workflow 
	 * Method gets parameter of service instance that will be disconnect
	 * In the processes of disconnection SI will be created Service Order with type DISCONNECT and status ENTERING
	 * 
	 * @see com.zephyrus.wind.model.ServiceOrder
	 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
	 * 
	 * @return disconnect service order
	 */

	private ServiceOrder createDisconnectOrder (ServiceInstance serviceInstance) throws Exception{
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = new ServiceOrder();
		IOrderStatusDAO orderStatusDAO = getOracleDaoFactory().getOrderStatusDAO();
		OrderStatus orderStatus = orderStatusDAO.findById(ORDER_STATUS.ENTERING.getId());
		order.setOrderStatus(orderStatus);
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		IOrderTypeDAO orderTypeDAO = getOracleDaoFactory().getOrderTypeDAO();
		OrderType orderType = orderTypeDAO.findById(ORDER_TYPE.DISCONNECT.getId());
		order.setOrderType(orderType);
		order.setProductCatalog(serviceInstance.getProductCatalog());
		ServiceOrder createServiceOrders = orderDAO.getSICreateOrder(serviceInstance);
		order.setServiceLocation(createServiceOrders.getServiceLocation());
		order.setServiceInstance(serviceInstance);
		disconnectOrder = orderDAO.insert(order);
		return disconnectOrder;
	}

}
