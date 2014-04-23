package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.OrderStatus;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.model.ServiceOrder;

public class SendOrderCommand extends SQLCommand {
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		boolean isSaved = false;
		IServiceOrderDAO orderDAO = oracleDaoFactory.getServiceOrderDAO();
		Date s = new Date(new java.util.Date().getTime());
		ServiceOrder order = null;
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		if (orderId != null){
			order = orderDAO.findById(orderId);
			isSaved = true;
		} else{
			order = (ServiceOrder)request.getSession().getAttribute("order");
		}
		if(order == null){
			request.setAttribute("error", "No order to send!");
			return Pages.MESSAGE_PAGE.getValue();
		}
		order.setOrderStatusId(OrderStatus.PROCESSING.getId());
		order.setOrderDate(s);
		if(isSaved)
			orderDAO.update(order);
		else
			orderDAO.insert(order);
		
		request.setAttribute("message", "Order has been sent successfuly!");
		return Pages.MESSAGE_PAGE.getValue();
	}
}
