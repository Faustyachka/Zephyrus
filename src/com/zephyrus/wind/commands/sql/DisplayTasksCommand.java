package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.TASK_STATUS;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * The DisplayTasksCommand class provide the displaying of engineers current
 * active tasks and available tasks for given engineer's group. Class contains
 * the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand.
 * 
 * @author Alexandra Beskorovaynaya
 * 
 */
public class DisplayTasksCommand extends SQLCommand {
	/**
	 * Method allows to display active and available tasks for the authorized
	 * user in dependence of his role.
	 * 
	 * @return String url of user's index page for redirecting, which depends on
	 *         authorized user's role. Index page is the home page for every
	 *         user, on which Current Tasks are displayed.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		// checking is user authorized
		if (request.getSession().getAttribute("user") == null) {
			request.setAttribute("messageNumber",
					MessageNumber.LOGIN_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		// Get user from HTTP session
		User user = (User) request.getSession().getAttribute("user");
		UserRole userRole = user.getRole();

		// checking is user logged in under engineer's account
		if (userRole.getId() != ROLE.INSTALLATION.getId()
				&& userRole.getId() != ROLE.PROVISION.getId()) {
			request.setAttribute("messageNumber",
					MessageNumber.LOGIN_INSTALL_PROVISION_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// Find necessary lists of tasks for defined user
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		ArrayList<Task> activeTasks = taskDao.findTasksByUser(user, TASK_STATUS.PROCESSING.getId());
		ArrayList<Task> availableTasks = taskDao
				.findAvailableTasksByRole(userRole);
		ArrayList<Task> suspendedTasks = taskDao.findTasksByUser(user, TASK_STATUS.SUSPEND.getId());
		request.setAttribute("activeTasks", activeTasks);
		request.setAttribute("availableTasks", availableTasks);
		request.setAttribute("suspendedTasks", suspendedTasks);

		if (userRole.getId() == ROLE.PROVISION.getId()) {
			return "provision/index.jsp";
		} else if (userRole.getId() == ROLE.INSTALLATION.getId()) {
			return "installation/index.jsp";
		}
		return null;
	}

}
