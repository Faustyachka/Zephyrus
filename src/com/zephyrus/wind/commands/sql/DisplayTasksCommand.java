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
		
		//checking is user authorized
		if (request.getSession().getAttribute("user")==null) {
			request.setAttribute("errorMessage", "You should login to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		//Get user from HTTP session
		User user = (User) request.getSession().getAttribute("user");
		UserRole userRole = user.getRole();
		
		//checking is user logged in under engineer's account
		if (userRole.getId()!=ROLE.INSTALLATION.getId()&&userRole.getId()!=ROLE.PROVISION.getId()) {
			request.setAttribute("errorMessage", "You should login under Provisioning or"
					+ "Installation Engineer's account to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		//Find necessary lists of tasks for defined user
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		ArrayList<Task> activeTasks = taskDao.findActualTasksByUser(user);
		ArrayList<Task> availableTasks = taskDao
				.findAvailableTasksByRole(userRole);
		
		if (activeTasks.size()==0) {
			request.setAttribute("activeTasks", null);
		} else {
			request.setAttribute("activeTasks", activeTasks);
		}
		if (availableTasks.size()==0) {
			request.setAttribute("availableTasks", null);
		} else {
			request.setAttribute("availableTasks", availableTasks);
		}
		if (userRole.getId()==ROLE.PROVISION.getId()) {
			return "provision/index.jsp";
		}
		if (userRole.getId()==ROLE.INSTALLATION.getId()) {
			return "installation/index.jsp";
		}
		return null;
	}

}
