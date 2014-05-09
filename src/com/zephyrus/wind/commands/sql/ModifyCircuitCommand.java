package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.ModifyScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in @link								// REVIEW: link isn't working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for modifying of
 * circuit by provisioning engineer.
 * 
 * @return page with confirmation of successful modifying of circuit						// REVIEW: return. again.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class ModifyCircuitCommand extends SQLCommand {

	/**
	 * This method modify the circuit in the database. Method gets parameter of
	 * task's ID and Circuit configuration from JSP.
	 * 
	 * @return page with confirmation of successful modifying of circuit					// REVIEW: always?
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		int taskID;																			// REVIEW: implementation is too far from usage

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Provisioning Engineer's account to view this page!<br>"				// REVIEW: HTML
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/provision'><input type='"					// REVIEW: HTML
							+ "button' class='button' value='Tasks'/></a>");
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid"						// REVIEW: HTML
					+ "<a href='/Zephyrus/provision'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// getting Task and Port and Service Order by task ID
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		Task task = taskDao.findById(taskID);											
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!"								// REVIEW: HTML
							+ "<a href='/Zephyrus/provision'> Tasks </a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ServiceOrder so = task.getServiceOrder();
		Port port = findPortFromTaskID(task);

		String circuitConfig = request.getParameter("circuit");
		if (circuitConfig.equals("")) {														// REVIEW: what if circuitConfig is null?
			request.setAttribute("port", port);
			request.setAttribute("task", task);
			request.setAttribute("message", "Circuit field can not be empty!");				// REVIEW: message or errorMessage
			return "provision/modifyCircuit.jsp";
		}
		
		// creating circuit due to "New" scenario
		ModifyScenarioWorkflow wf = new ModifyScenarioWorkflow(getOracleDaoFactory(),		// REVIEW: Workflow constructor also should be in try block
				so);
		try {		
			wf.updateCircuitConfig(taskID, circuitConfig);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("port", port);
			request.setAttribute("task", task);
			request.setAttribute("message", "Failed to modify circuit!");
			return "provision/modifyCircuit.jsp";											// REVIEW: hardcoded link
		} finally {
			wf.close();
		}

		// sending redirect to page with confirmation
		request.setAttribute(
				"message",
				"Circuit successfully updated. Task completed! <br>"
						+ "<a href='/Zephyrus/provision'> <input type='button' value='Back to"	// REVIEW: HTML
						+ " tasks' class='button'></a>");
		return PAGES.MESSAGE_PAGE.getValue();
	}

	/**
	 * Method for searching port by order task													// REVIEW: see remarks about it in other Commands
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @param given
	 *            task
	 * @return port object if exist, otherwise null.
	 * @author Miroshnychenko Nataliya
	 */

	private Port findPortFromTaskID(Task task) throws Exception {
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		if (serviceLocation == null) {
			return null;
		}
		Cable cable = getOracleDaoFactory().getCableDAO().findCableFromServLoc(
				serviceLocation.getId());
		return cable.getPort();
	}

}
