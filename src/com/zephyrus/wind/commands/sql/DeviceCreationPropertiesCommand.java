package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for displaying of
 * device creation details to Installation engineer.
 * 
 * @return page with information about creation of Device
 * 
 * @author Alexandra Beskorovaynaya
 */
public class DeviceCreationPropertiesCommand extends SQLCommand {
	
	/**
	 * This method checks all necessary input data and forms the 
	 * information for device creation.
	 * 
	 * @return the page of device creation. In error situation returns the page
	 *         with message about error details.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;
		
		User user = (User) request.getSession().getAttribute("user");
		
		//checking is user authorized
		if (user==null||user.getRole().getId()!=ROLE.INSTALLATION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Installation Engineer's account to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		
		//check the presence of task ID
		if (request.getParameter("taskId")==null) {
			request.setAttribute("errorMessage", "You must choose task from task's page!<br>"
					+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid. "
					+ "You must choose task from task's page!<br>"
					+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
			
		request.setAttribute("taskId", taskID);
		return PAGES.CREATEDEVICE_PAGE.getValue();
	}

}
