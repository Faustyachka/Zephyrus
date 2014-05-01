package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to display defined user's Service Instances and Service Orders to Customer Support Engineer.
 * 
 * @return page with all service Orders and Service Instances of defined user.
 * @author Alexandra Beskorovaynaya
 *
 */
public class OrdersAndInstancesDisplayingCommand extends SQLCommand {
	
	/**
	 * This method gets the data about Service Instances and Service Orders for defines user
	 * from database and sends it to the page of viewing. 
	 * 
	 * @return Customer Support Engineer's page with Service Instances and Service Orders.
	 * 		   Also can return the error page if the received user ID is not valid or user
	 *         logged in not under Customer Support Engineer's account. 
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		User user = (User)request.getSession().getAttribute("user");
		if (user==null||user.getRole().getId()!=ROLE.SUPPORT.getId()) {
			request.setAttribute("errorMessage", "You must be logged in under Support Engineer account"
					+ " to review this page!<a href='/Zephyrus/view/login.jsp'>login</a>");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		if (request.getParameter("id")==null) {
			request.setAttribute("errorMessage", "You must choose the user from list!"
					+ "<a href='/Zephyrus/customersupport'> choose user </a> ");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		try {
			int userID = Integer.parseInt(request.getParameter("id"));
			IServiceInstanceDAO siDAO = getOracleDaoFactory().getServiceInstanceDAO();
			ArrayList<ServiceInstance> instances = siDAO.getServiceInstancesByUserId(userID);
			//TODO get orders			
			request.setAttribute("instances", instances);			
			return "support/ordersInstances.jsp";
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "User ID is not valid");
			return PAGES.MESSAGE_PAGE.getValue();
		}
				
	}

}
