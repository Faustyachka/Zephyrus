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
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
// REVIEW: documentation expected
public class SendOrderCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = null;
		Integer orderId = null;
		
		if(request.getParameter("orderId") != null)
			orderId = Integer.parseInt(request.getParameter("orderId"));
		if (orderId != null){
			order = orderDAO.findById(orderId);
		} else {
			SaveOrderCommand saveOrder = new SaveOrderCommand();
			order = saveOrder.returnOrder(request, response, getOracleDaoFactory());
		}
		if (order == null){
			request.setAttribute("error", "No order to send!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		NewScenarioWorkflow workflow = new NewScenarioWorkflow(getOracleDaoFactory(), order);
		workflow.proceedOrder();
		workflow.close();

		request.setAttribute("message", "Order has been sent successfuly!");
		return PAGES.MESSAGE_PAGE.getValue();
	}
}
