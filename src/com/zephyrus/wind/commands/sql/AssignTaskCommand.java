package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to assign
 * task to the engineer.
 * 
 * @see com.zephyrus.wind.model.User
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.enums.ROLE
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * 
 * @return home page of user
 * @author Alexandra Beskorovaynaya
 */
public class AssignTaskCommand extends SQLCommand {

	/**
	 * This method assigns the task to user in the database. Method gets
	 * parameter of task's ID from JSP and User from session.
	 * 
	 * @see com.zephyrus.wind.model.User
	 * @see com.zephyrus.wind.model.Task
	 * @see com.zephyrus.wind.enums.ROLE
	 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
	 * 
	 * @return home page of user
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskId;
		User user = (User) request.getSession().getAttribute("user");
		// checking is user authorized
		if (user == null) {
			request.setAttribute("errorMessage",
					"You should login to view this page!"
							+ " <a href='/Zephyrus/view/login.jsp'>login</a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// checking is user logged in under engineer's account
		if (user.getRole().getId() != ROLE.INSTALLATION.getId()
				&& user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute(
					"errorMessage",
					"You should login under Provisioning or"
							+ "Installation Engineer's account to view this page!"
							+ " <a href='/Zephyrus/view/login.jsp'>login</a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		//check the presence of task ID
		if (request.getParameter("taskId")==null) {
			request.setAttribute("errorMessage", "You must choose task from task's page!"
					+ "<a href='/Zephyrus/provision'> Tasks </a>");
		}
		try {
			taskId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskId);
		ServiceOrder order = task.getServiceOrder();
		NewScenarioWorkflow wf = new NewScenarioWorkflow(getOracleDaoFactory(),
				order);
		wf.assignTask(taskId, user.getId());

		if (user.getRole().getId() == ROLE.PROVISION.getId()) {
			return "provision";
		}
		if (user.getRole().getId() == ROLE.INSTALLATION.getId()) {
			return "installation";
		} else {
			return null;
		}

	}
}
