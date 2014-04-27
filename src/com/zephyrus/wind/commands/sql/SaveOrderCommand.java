package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProductCatalogService;											// REVIEW: unused import
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
																								// REVIEW: documentation expected
public class SaveOrderCommand extends SQLCommand {
	
	private ServiceOrder insertedOrder = null;

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		returnOrder(request, response, getOracleDaoFactory());
		request.setAttribute("message", "Order successfuly saved!");
		return PAGES.MESSAGE_PAGE.getValue();
	}
	
	public ServiceOrder returnOrder(HttpServletRequest request,									// REVIEW: public return-method in Command. Are you sure about it? 
			HttpServletResponse response, OracleDAOFactory factory) throws SQLException, Exception{
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		IServiceLocationDAO locationDAO = factory.getServiceLocationDAO();
		ServiceLocation location = (ServiceLocation) session.getAttribute("serviceLocation");
		location.setUser(user);
		location = locationDAO.insert(location);
		
		IServiceOrderDAO orderDAO = factory.getServiceOrderDAO();
		ProductCatalog service = (ProductCatalog) session.getAttribute("service");
		ServiceOrder order = new ServiceOrder();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(ORDER_STATUS.ENTERING.getId());										// REVIEW: setOrderStatusValue wasn't invoked. OrderStatus should be obtained by find method in generic DAO
		order.setOrderStatus(orderStatus);
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		OrderType orderType = new OrderType();
		orderType.setId(ORDER_TYPE.NEW.getId());												// REVIEW: setOrderType wasn't invoked. OrderType should be obtained by find
		order.setOrderType(orderType);
		order.setProductCatalog(service);
		order.setServiceLocation(location);
		order.setServiceInstance(new ServiceInstance());
		insertedOrder = orderDAO.insert(order);
		
		session.setAttribute("serviceLocation", null);
		session.setAttribute("service", null);
		session.setAttribute("products", null);
		return insertedOrder;
	}

}
