package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.	// REVIEW: link is not working
 * Method realized cancel saved Order in state "ENTERING"
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
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

		int orderId;

		User user = (User) request.getSession().getAttribute("user");

		if (user == null || user.getRole().getId() != ROLE.CUSTOMER.getId()) {
			request.setAttribute("messageNumber", MessageNumber.USER_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		if (request.getParameter("orderId") == null) {
			request.setAttribute("messageNumber", MessageNumber.ORDER_SL_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.ORDER_SL_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = orderDAO.findById(orderId);
		
		if (order == null) {
			request.setAttribute("messageNumber", MessageNumber.ORDER_SL_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		if(order.getOrderStatus().getId() == ORDER_STATUS.ENTERING.getId() 
				&& order.getServiceLocation().getUser().getId().equals(user.getId())){

			IServiceLocationDAO serviceLocationDAO = getOracleDaoFactory().getServiceLocationDAO();
			ServiceLocation serviceLocation = order.getServiceLocation();
			orderDAO.remove(order);
			if (serviceLocation == null){
				request.setAttribute("messageNumber", MessageNumber.ORDER_SL_EXIST_ERROR.getId());
				return PAGES.MESSAGE_PAGE.getValue();
			} else {
				serviceLocationDAO.remove(serviceLocation);
			}
		} else {
			request.setAttribute("messageNumber", MessageNumber.CANCEL_ORDER_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		request.setAttribute("messageNumber", MessageNumber.CANCEL_ORDER_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
