package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
																									 // REVIEW: documentation expected
public class CustomerOrdersCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		IServiceLocationDAO locationDAO = getOracleDaoFactory().getServiceLocationDAO();
		User user = (User) request.getSession().getAttribute("user");
		ArrayList<ServiceLocation> locations = locationDAO.getServiceLocationsByUserId(user.getId());
		ArrayList<ServiceOrder> orders = new ArrayList<ServiceOrder>();								
		for(ServiceLocation location : locations){																
			orders.addAll(orderDAO.getServiceOrdersByServiceLocationId(location.getId()));			// REVIEW: do you really need all orders for all locations? if so, why don't you write corresponding DAO method
		}
		ArrayList<ServiceOrder> actualOrders = new ArrayList<ServiceOrder>();
		ArrayList<ServiceOrder> workedOutOrders = new ArrayList<ServiceOrder>();
		for(ServiceOrder order : orders){
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
