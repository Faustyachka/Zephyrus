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
 *  The DisplayTasksCommand class provide the displaying of engineers current active tasks
 *  and available tasks for given engineer's group.  			
 *  @author Alexandra Beskorovaynaya
 *
 */
public class DisplayTasksCommand extends SQLCommand {
	/**
	 * @return String url of page for redirecting, which depends on authorized user's role. 
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		User user = (User)request.getSession().getAttribute("user"); 
		UserRole role = user.getRole();
		
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		ArrayList<Task> activeTasks = taskDao.findActualTasksByUser(user);
		ArrayList<Task> availableTasks = taskDao.findAvailableTasksByRole(role);
		request.setAttribute("activeTasks", activeTasks);
		request.setAttribute("availableTask", availableTasks);
		if (role.getId() == ROLE.SUPPORT.getId()) {
			return PAGES.SUPPORT_PAGE.getValue();
		} 
		if (role.getId() == ROLE.INSTALLATION.getId()) {
			return PAGES.INSTALLATION_PAGE.getValue();
		}
		if (role.getId() == ROLE.PROVISION.getId()) {
			return PAGES.PROVISION_PAGE.getValue();
		} else {
			return null;
		}
	}

}
