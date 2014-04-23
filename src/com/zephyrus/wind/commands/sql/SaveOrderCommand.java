package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.OrderStatus;
import com.zephyrus.wind.enums.OrderType;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.model.ProductCatalogService;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

public class SaveOrderCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		IServiceLocationDAO locationDAO = oracleDaoFactory.getServiceLocationDAO();
		ServiceLocation location = (ServiceLocation) session.getAttribute("serviceLocation");
		location.setUser(user);
		location = locationDAO.insert(location);
		
		IServiceOrderDAO orderDAO = oracleDaoFactory.getServiceOrderDAO();
		ProductCatalogService service = (ProductCatalogService) session.getAttribute("service");
		ServiceOrder order = new ServiceOrder();
		order.setOrderStatusId(OrderStatus.ENTERING.getId());
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		order.setOrderTypeId(OrderType.NEW.getId());
		order.setProductCatalogId(service.getId());
		order.setServiceLocationId(location.getId());
		order.setServiceInstanceId(0);
		orderDAO.insert(order);
		
		session.setAttribute("serviceLocation", null);
		session.setAttribute("service", null);
		session.setAttribute("products", null);
		
		request.setAttribute("message", "Order successfuly saved!");
		return Pages.MESSAGE_PAGE.getValue();
	}

}
