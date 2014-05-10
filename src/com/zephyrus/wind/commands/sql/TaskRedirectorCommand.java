package com.zephyrus.wind.commands.sql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.Command. It is supposed to redirect
 * user from page with tasks to page of task processing.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class TaskRedirectorCommand extends SQLCommand {

	/**
	 * This method redirects user from tasks page to processing task page in
	 * dependence of scenario. Method gets parameter of task's ID from JSP and
	 * User from session.
	 * 
	 * @see com.zephyrus.wind.model.User
	 * @see com.zephyrus.wind.enums.ROLE
	 * 
	 * @return address of selected task processing page.
	 */
	@Override
	public String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null) {
			request.setAttribute("messageNumber",
					MessageNumber.LOGIN_INSTALL_PROVISION_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("id") == null) {
			request.setAttribute("messageNumber",
					MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		int taskID;
		try {
			taskID = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber",
					MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);

		if (task == null) {
			request.setAttribute("messageNumber",
					MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		OrderType orderType = task.getServiceOrder().getOrderType();
		request.setAttribute("taskId", taskID);
		if (user.getRole().getId() == ROLE.INSTALLATION.getId()) {
			if (orderType.getId() == ORDER_TYPE.NEW.getId()) {
				return "newConnectionProperties";
			}
			if (orderType.getId() == ORDER_TYPE.DISCONNECT.getId()) {
				return "disconnectConnectionProperties";
			}
		}
		if (user.getRole().getId() == ROLE.PROVISION.getId()) {
			if (orderType.getId() == ORDER_TYPE.NEW.getId()) {
				return "createCircuitView";
			}
			if (orderType.getId() == ORDER_TYPE.DISCONNECT.getId()) {
				return "deleteCircuitView";
			}
			if (orderType.getId() == ORDER_TYPE.MODIFY.getId()) {
				return "modifyCircuitView";
			}

		}

		return null;
	}

}
