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
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in @link										// REVIEW: link isn't working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * the cable in the system.
 * 
 * @see com.zephyrus.wind.model.Cable
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
 * 
 * @return page with confirmation of successful creation of cable									// REVIEW: how possibly can class return the value?
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */
public class CreateCableCommand extends SQLCommand {

	/**
	 * This method creates the cable in the database. Method gets parameters of
	 * device's ID and port's ID, which cable will be connected to. By means of
	 * workflow, new object Cable with mentioned parameters is created in the
	 * database.
	 * 
	 * @see com.zephyrus.wind.model.Cable
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.model.Port
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
	 * 
	 * @return page with confirmation of successful creation of cable
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized																// REVIEW: typo is->if
		if (user == null || user.getRole().getId() != ROLE.INSTALLATION.getId()) {	
			request.setAttribute("errorMessage", "You should login under "
					+ "Installation Engineer's account to view this page!<br>"						// REVIEW: HTML code on server side
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"									// REVIEW: HTML code on server side
							+ "<a href='/Zephyrus/installation'> <input type='"
							+ "button' class='button' value='Tasks'/> </a>");
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid. "
					+ "You must choose task from task's page!<br>"									// REVIEW: HTML code on server side
					+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = new NewScenarioWorkflow(getOracleDaoFactory(),						// REVIEW: Workflow constructor should also be in the try block
				order);
		try {
			wf.createCable(taskID);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("message", "Failed to create cable!");								// REVIEW: "message" attribute instead of "errorMessage"
			return "newConnectionProperties";
		} finally {
			wf.close();
		}

		request.setAttribute("taskId", taskID);
		request.setAttribute("message", "New cable successfully created!");							// REVIEW: "message" attribute instead of "errorMessage"
		return "newConnectionProperties";
	}
}
