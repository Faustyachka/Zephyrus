package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;																// REVIEW: unused import
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;								
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
															

/**
 * This class contains the method, that is declared in @link								// REVIEW: link isn't working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to display
 * defined user's Service Orders to Customer User.
 * 
 * @return page with all Service Orders of defined user.									// REVIEW: return statement in class documentation
 * @author Miroshnychenko Nataliya
 * 
 */

public class CustomerOrdersCommand extends SQLCommand {

	/**
	 * This method gets the data about Service Orders for
	 * defines user from database and sends it to the page of viewing.
	 * 
	 * @return Customer User page with Service Orders. Also can return the error page if the received
	 *         user ID is not valid
	 */
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		User user = (User) request.getSession().getAttribute("user");
		
		if (user == null) {
			request.setAttribute("errorMessage", "User doesn't exist!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ArrayList<ServiceOrder> orders = orderDAO.getOrdersByUser(user);					// REVIEW: I recommend to add param orderType to this DAO
		
		if (orders == null){
			request.setAttribute("messageNumber", MessageNumber.ORDER_EMPTY_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ArrayList<ServiceOrder> actualOrders = new ArrayList<ServiceOrder>();				// REVIEW: rename actual -> active 
		ArrayList<ServiceOrder> workedOutOrders = new ArrayList<ServiceOrder>();
		
		for(ServiceOrder order : orders){													// REVIEW: cycle is not affective
			if(order.getOrderStatus().getId() == ORDER_STATUS.ENTERING.getId() ||
			   order.getOrderStatus().getId() == ORDER_STATUS.PROCESSING.getId()){
				actualOrders.add(order);
			} else {
				workedOutOrders.add(order);
			}
		}
		
		request.setAttribute("actualOrders", actualOrders);
		request.setAttribute("workedOutOrders", workedOutOrders);
		
		return PAGES.CUSTOMERORDERS_PAGE.getValue();
	}

}
