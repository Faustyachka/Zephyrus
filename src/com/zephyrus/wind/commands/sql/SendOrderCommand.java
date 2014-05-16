package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in 
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. 
 * Method send order to processing
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

public class SendOrderCommand extends SQLCommand {
	
	/**
	 * The method take input parameters and send order in processing using NewScenarioWorkflow. 
	 * If order doesn't exist method call at first method SaveOrderCommand.returnOrder, 
	 * that create order.
	 * Method gets parameter of order ID or Service Location ID and product ID
	 * 
	 * @return page with confirmation of successful sending order	
	 */

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {


		ServiceOrder order;
		int orderId;

		User user = (User) request.getSession().getAttribute("user");

		if (user == null || user.getRole().getId() != ROLE.CUSTOMER.getId()) {
			request.setAttribute("messageNumber", MessageNumber.USER_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}	

		if (request.getParameter("orderId") == null){
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

			SaveOrderCommand saveOrder = new SaveOrderCommand();
			order = saveOrder.returnOrder(location, product, getOracleDaoFactory());	
		} else {

			IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();

			try {
				orderId = Integer.parseInt(request.getParameter("orderId"));
			} catch (NumberFormatException ex) {
				ex.printStackTrace();
				request.setAttribute("messageNumber", MessageNumber.ORDER_SL_EXIST_ERROR.getId());
				return PAGES.MESSAGE_PAGE.getValue();
			}

			order = orderDAO.findById(orderId);
		}

		if (order == null  || !order.getServiceLocation().getUser().getId().equals(user.getId())){
			request.setAttribute("messageNumber", MessageNumber.ORDER_SEND_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		NewScenarioWorkflow workflow = null;

		try {
			workflow = new NewScenarioWorkflow(getOracleDaoFactory(), order); 
			workflow.proceedOrder();
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("error", "Failed to send order");
			return "customer/index.jsp";
		} finally {
			workflow.close();
		}

		request.setAttribute("messageNumber", MessageNumber.ORDER_SEND_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}
}
