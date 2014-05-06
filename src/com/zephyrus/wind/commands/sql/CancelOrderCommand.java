package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * Method realized cancel saved Order in state "ENTERING"
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
 * @return massage of successful cancel order
 * @author Miroshnychenko Nataliya
 */

public class CancelOrderCommand extends SQLCommand {
	
	/**
	 * This method realized cancel Order. Order must be saved and have status "ENTERING"
	 * Method gets parameter of service order ID that will be canceled
	 * 
	 * @see com.zephyrus.wind.model.ServiceOrder
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
	 * 
	 * @return page with confirmation of successful order cancel 
	 */

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		// TODO Auto-generated method stub

		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = null;
		Integer orderId = null;
		
		if(request.getParameter("orderId") != null){
			orderId = Integer.parseInt(request.getParameter("orderId"));
			if (orderId != null){
				order = orderDAO.findById(orderId);
			}
		} else {
			request.setAttribute("error", "No order to cancel!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		if(order.getOrderStatus().getId() == ORDER_STATUS.ENTERING.getId()){
			
			IServiceLocationDAO serviceLocationDAO = getOracleDaoFactory().getServiceLocationDAO();
			ServiceLocation serviceLocation = order.getServiceLocation();
			orderDAO.remove(order);
			if (serviceLocation == null){
				request.setAttribute("error", "Service Location doesn`t exist!");
				return PAGES.MESSAGE_PAGE.getValue();
			} else {
				serviceLocationDAO.remove(serviceLocation);
			}
		} else {
			request.setAttribute("error", "Order can`t be cancel!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		request.setAttribute("message", "Order has been canceled! <br>"
						+ "<br><a href='/Zephyrus/customerOrders'> <input type='button' value='Back to"
						+ " orders' class='button'></a>");
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
