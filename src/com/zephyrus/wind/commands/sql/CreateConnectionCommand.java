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
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new connection to the service location in the system.
 * 
 * @see com.zephyrus.wind.model.Cable
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.model.PTask
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IPortDAO
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * @see com.zephyrus.wind.workflow.NewScenarioWorkflow
 * 
 * @return page with confirmation of successful creation of connection
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */

public class CreateConnectionCommand extends SQLCommand {

	/**
	 * This method creates the connection in the database. Method gets
	 * parameters of task's ID, number of port and cable's ID, which indicate
	 * what service order connection is being created for and what port and
	 * cable will be user for the connection. By means of workflow, new object
	 * Cable with mentioned parameters is created in the database.
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

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.INSTALLATION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Installation Engineer's account to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"
							+ "button' class='button' value='Tasks'/></a>");
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

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);

		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!"
							+ "<a href='/Zephyrus/installation'> Tasks </a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = new NewScenarioWorkflow(getOracleDaoFactory(),
				order);
		try {
			wf.plugCableToPort(taskID);

		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("message", "Failed to create connection");
			return "newConnectionProperties";
		} finally {
			wf.close();
		}

		request.setAttribute("message",
				"New connection successfully created! Task completed!<br>"
						+ "<a href='/Zephyrus/installation'><input type='"
						+ "button' class='button' value='Tasks'/></a>");
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
