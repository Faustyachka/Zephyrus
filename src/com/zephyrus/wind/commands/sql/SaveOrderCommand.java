package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in 
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. 
 * Method save order
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.model.ServiceLocation
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceLocationDAO
 * 
 * @author Miroshnychenko Nataliya
 */

public class SaveOrderCommand extends SQLCommand {
	
	private ServiceOrder insertedOrder = null;

	/**
	 * The method take input parameters and call method returnOrder that save order
	 * Method gets parameter of user ID, product ID and Service Location ID, that user has been selected
	 * 
	 * @return page with confirmation of successful saving order	
	 */
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		User user = (User) request.getSession().getAttribute("user");

		if (user == null || user.getRole().getId() != ROLE.CUSTOMER.getId()) {
			request.setAttribute("messageNumber", MessageNumber.USER_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}	
		
		IServiceLocationDAO locationDAO = getOracleDaoFactory().getServiceLocationDAO();
		ServiceLocation location = (ServiceLocation) request.getSession().getAttribute("serviceLocation");
		
		if (location == null) {
			request.setAttribute("messageNumber", MessageNumber.SERVICE_LOCATION_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		location.setUser(user);
		location = locationDAO.insert(location);
		
		ProductCatalog product = (ProductCatalog) request.getSession().getAttribute("service");
		
		if (product == null) {
			request.setAttribute("messageNumber", MessageNumber.SERVICE_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		returnOrder(location, product, getOracleDaoFactory());	
		
		request.getSession().setAttribute("serviceLocation", null);
		request.getSession().setAttribute("service", null);
		request.getSession().setAttribute("products", null);
		
		request.setAttribute("messageNumber", MessageNumber.ORDER_SAVE_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}
	
	/**
	 * The method create order 
	 * Method gets parameter of user ID and Service Location ID, that user has been selected. 
	 * Also it gets OracleDAOFactory instance
	 * 
	 * @return created Service Order
	 */
	
	protected ServiceOrder returnOrder(ServiceLocation location, ProductCatalog product, OracleDAOFactory factory) 
			throws SQLException, Exception{
		
		IServiceOrderDAO orderDAO = factory.getServiceOrderDAO();
		ServiceOrder order = new ServiceOrder();
		IOrderStatusDAO orderStatusDAO = getOracleDaoFactory().getOrderStatusDAO();
		OrderStatus orderStatus = orderStatusDAO.findById(ORDER_STATUS.ENTERING.getId());
		order.setOrderStatus(orderStatus);
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		IOrderTypeDAO orderTypeDAO = getOracleDaoFactory().getOrderTypeDAO();
		OrderType orderType = orderTypeDAO.findById(ORDER_TYPE.NEW.getId());
		order.setOrderType(orderType);
		order.setProductCatalog(product);
		order.setServiceLocation(location);
		order.setServiceInstance(new ServiceInstance());
		insertedOrder = orderDAO.insert(order);
		return insertedOrder;
	}

}
