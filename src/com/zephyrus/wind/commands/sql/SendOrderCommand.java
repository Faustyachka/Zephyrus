package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

public class SendOrderCommand extends SQLCommand {
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		Date s = new Date(new java.util.Date().getTime());
		ServiceOrder order = null;
		Integer orderId = null;
		if(request.getParameter("orderId") != null)
			orderId = Integer.parseInt(request.getParameter("orderId"));
		if (orderId != null){
			order = orderDAO.findById(orderId);
		} else{
			order = new SaveOrderCommand().returnOrder(request, response);
		}
		if(order == null){
			request.setAttribute("error", "No order to send!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(ORDER_STATUS.PROCESSING.getId());
		order.setOrderStatus(orderStatus);
		order.setOrderDate(s);
		IServiceInstanceDAO instanceDAO = getOracleDaoFactory().getServiceInstanceDAO();
		ServiceInstance serviceInstance = new ServiceInstance();
		serviceInstance.setProductCatalog(order.getProductCatalog());
		ServiceInstanceStatus instanceStatus = new ServiceInstanceStatus();
		instanceStatus.setId(SERVICEINSTANCE_STATUS.PLANNED.getId());
		instanceStatus.setServInstanceStatusValue(SERVICEINSTANCE_STATUS.PLANNED.name());
		serviceInstance.setServInstanceStatus(instanceStatus);
		serviceInstance.setStartDate(s);
		serviceInstance.setCircuit(new Circuit());
		serviceInstance.setUser((User) request.getSession().getAttribute("user"));
		serviceInstance = instanceDAO.insert(serviceInstance);
		order.setServiceInstance(serviceInstance);
		orderDAO.update(order);
		
		request.setAttribute("message", "Order has been sent successfuly!");
		return PAGES.MESSAGE_PAGE.getValue();
	}
}
