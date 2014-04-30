package com.zephyrus.wind.commands.nosql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.ROLE;
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
public class TaskRedirectorCommand implements Command {

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
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		int taskId = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("taskId", taskId);
		if (user.getRole().getId() == ROLE.INSTALLATION.getId()) {
			return "newConnectionProperties";
		}
		if (user.getRole().getId() == ROLE.PROVISION.getId()) {
			return "provision/createCircuit.jsp";
		} else {
			return null;
		}
	}

}
