package com.zephyrus.wind.commands.sql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.Command. It is supposed to redirect
 * user from page with tasks to page of task processing.
 * 
 * @see com.zephyrus.wind.model.User
 * @see com.zephyrus.wind.enums.ROLE
 * 
 * @return page of task processing
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
	 * @return page of task processing for defined task
	 */
	@Override
	public String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null) {
			request.setAttribute("errorMessage", "You should login "
					+ " to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("id") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"
							+ "button' class='button' value='Tasks'/></a>");
		}
		try {
			taskID = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid. "
					+ "You must choose task from task's page!<br>"
					+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		request.setAttribute("taskId", taskID);
		if (user.getRole().getId() == ROLE.INSTALLATION.getId()) {
			if (task.getServiceOrder().getOrderType().getId() == ORDER_TYPE.NEW
					.getId()) {
				return "newConnectionProperties";
			}
			if (task.getServiceOrder().getOrderType().getId() == ORDER_TYPE.DISCONNECT
					.getId()) {
				return "disconnectConnectionProperties";
			}
		}
		if (user.getRole().getId() == ROLE.PROVISION.getId()) {
			if (task.getServiceOrder().getOrderType().getId() == ORDER_TYPE.NEW
					.getId()) {
				return "createCircuitView";
			}
			if (task.getServiceOrder().getOrderType().getId() == ORDER_TYPE.DISCONNECT
					.getId()) {
				return "deleteCircuitView";
			}

		}

		return null;
	}

}
