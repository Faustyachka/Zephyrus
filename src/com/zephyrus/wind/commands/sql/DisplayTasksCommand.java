package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

/**
 * The DisplayTasksCommand class provide the displaying of engineers current
 * active tasks and available tasks for given engineer's group. Class contains 
 * the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * 
 * @see com.zephyrus.wind.model.User
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * @see com.zephyrus.wind.enums.ROLE
 * 
 * @return engineer's index page with tasks if user authorized under
 * engineer's account, index page of customer user if user authorized under customer 
 * user's account, and error page if user isn't authorized.
 * @author Alexandra Beskorovaynaya
 * 
 */
public class DisplayTasksCommand extends SQLCommand {
	/**
	 * Method allows to display active and available tasks for the authorized
	 * user in dependence of his role.
	 * 
     * @see com.zephyrus.wind.model.User
     * @see com.zephyrus.wind.model.Task
     * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
     * @see com.zephyrus.wind.enums.ROLE
     * 
	 * @return String url of user's index page for redirecting, which depends on
	 *         authorized user's role. Index page is the home page for every
	 *         user, on which Current Tasks are displayed.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		//Get user from HTTP session
		User user = (User) request.getSession().getAttribute("user");
		UserRole userRole = user.getRole();
		
		//Find necessary lists of tasks for defined user
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		ArrayList<Task> activeTasks = taskDao.findActualTasksByUser(user);
		ArrayList<Task> availableTasks = taskDao
				.findAvailableTasksByRole(userRole);

		request.setAttribute("activeTasks", activeTasks);
		request.setAttribute("availableTasks", availableTasks);
		for (ROLE role : ROLE.values()) {
			if (user.getRole().getId() == role.getId()) {
				return role.getIndexPage();
			}
		}

		// If user is not authorized
		request.setAttribute("errorMessage", "Please, login! <br/> <a href='view/login.jsp'> login");
		return PAGES.MESSAGE_PAGE.getValue();

	}

}
