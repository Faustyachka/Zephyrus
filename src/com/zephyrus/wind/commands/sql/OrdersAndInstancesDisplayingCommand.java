package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in 
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to display
 * defined user's Service Instances and Service Orders to Customer Support
 * Engineer.
 * @author Alexandra Beskorovaynaya
 * 
 */
public class OrdersAndInstancesDisplayingCommand extends SQLCommand {

	/**
	 * This method gets the data about Service Instances and Service Orders for
	 * defines user from database and sends it to the page of viewing.
	 * 
	 * @return Customer Support Engineer's page with Service Instances and
	 *         Service Orders. Also can return the error page if the received
	 *         user ID is not valid or user logged in not under Customer Support
	 *         Engineer's account.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null || user.getRole().getId() != ROLE.SUPPORT.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_SUPPORT_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		if (request.getParameter("id") == null) {
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		int userID;
		try {
			userID = Integer.parseInt(request.getParameter("id"));				
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
		User customerUser = userDAO.findById(userID);	
		
		if(customerUser == null) {
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		IServiceInstanceDAO siDAO = getOracleDaoFactory()
				.getServiceInstanceDAO();
		ArrayList<ServiceInstance> serviceInstances = siDAO
				.getServiceInstancesByUserId(userID);
		ArrayList<ServiceOrder> orders = findServiceOrderByUser(customerUser);
		IServiceOrderDAO soDAO = getOracleDaoFactory().getServiceOrderDAO();
		Map<ServiceInstance, String> instances = new HashMap<ServiceInstance, String>();
		for (ServiceInstance instance: serviceInstances) {
			instances.put(instance, soDAO.getSICreateOrder(instance).getServiceLocation().getAddress());	
		}			
		request.setAttribute("instances", instances);
		request.setAttribute("orders", orders);
		return "support/ordersInstances.jsp";

	}

	/**
	 * Method finds Service Orders object of User
	 * 
	 * @param User															// REVIEW: no description
	 * @return collection of Service Orders
	 * @author Miroshnychenko Nataliya
	 */
	private ArrayList<ServiceOrder> findServiceOrderByUser(User user)
			throws Exception {
		ArrayList<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
		ArrayList<ServiceLocation> serviceLocations = getOracleDaoFactory()
				.getServiceLocationDAO().getServiceLocationsByUserId(
						user.getId());
		for (ServiceLocation serviceLocation : serviceLocations) {						// REVIEW: cycle here is a bad practice
			serviceOrders.addAll(getOracleDaoFactory().getServiceOrderDAO()
					.getServiceOrdersByServiceLocationId(
							serviceLocation.getId()));
		}
		return serviceOrders;

	}

}
