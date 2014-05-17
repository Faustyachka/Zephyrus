package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in 			
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for creating of
 * circuit by provisioning engineer.						 
 * @author Alexandra Beskorovaynaya
 */
public class CreateCircuitCommand extends SQLCommand {

	/**
	 * This method creates the circuit in the database. Method gets parameter of
	 * task's ID and Circuit configuration from JSP.
	 * 
	 * @return page with confirmation of successful creation of circuit
	 */
	@Override
	protected String doExecute(HttpServletRequest request,									
			HttpServletResponse response) throws SQLException, Exception {

		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_PROVISION_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// getting Task and Port and Service Order by task ID
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		Task task = taskDao.findById(taskID);
		if (task == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ServiceOrder so = task.getServiceOrder();
		Port port = findPortFromTaskID(task);

		String circuitConfig = request.getParameter("circuit");
		
		if (circuitConfig == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		Pattern pattern = Pattern
				.compile("^[1-9][0-9]{1,2}\\.([0-9]{1,3}\\.){2}[0-9]{1,3}$");
		Matcher matcher = pattern.matcher(circuitConfig);
		if (!matcher.matches()) {
			request.setAttribute("port", port);
			request.setAttribute("task", task);
			request.setAttribute("error", "Wrong configuration format");
			return "provision/createCircuit.jsp";
		}
		
		if (circuitConfig.equals("")) {
			request.setAttribute("port", port);
			request.setAttribute("task", task);
			request.setAttribute("error", "Circuit field can not be empty!");
			return "provision/createCircuit.jsp";
		}
		
		// creating circuit due to "New" scenario
		NewScenarioWorkflow wf = null;
		try {
			wf = new NewScenarioWorkflow(getOracleDaoFactory(),
					so);
			wf.createCircuit(taskID, circuitConfig);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("port", port);
			request.setAttribute("task", task);
			request.setAttribute("error", "Failed to create circuit!");
			return "provision/createCircuit.jsp";
		} finally {
			wf.close();
		}

		// sending redirect to page with confirmation
		request.setAttribute("messageNumber", MessageNumber.TASK_COMPLETED_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}

	/**
	 * Method for searching port by order task
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @param given																			// REVIEW: wrong documentation format: param name isn't specified
	 *            task
	 * @return port object if exist, otherwise null.
	 * @author Miroshnychenko Nataliya
	 */

	private Port findPortFromTaskID(Task task) throws Exception {							// REVIEW: find from task ID, but Task param was given
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
