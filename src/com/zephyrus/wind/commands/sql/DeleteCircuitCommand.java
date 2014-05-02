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
import com.zephyrus.wind.workflow.DisconnectScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for deleting of
 * circuit by provisioning engineer.
 * 
 * @return page with confirmation of successful deleting of circuit
 * 
 * @author Alexandra Beskorovaynaya
 */
public class DeleteCircuitCommand extends SQLCommand {
	
	/**
	 * This method deletes the circuit from the database. Method gets parameter of
	 * task's ID from JSP.
	 * 
	 * @return page with confirmation of successful deleting of circuit or
	 * error message if it occurs.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Provisioning Engineer's account to view this page!"
					+ " <a href='/Zephyrus/view/login.jsp'>login</a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!"
							+ "<a href='/Zephyrus/provision'> Tasks </a>");
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// getting Task and Port and Service Order by task ID
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		Task task = taskDao.findById(taskID);
		
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!"
							+ "<a href='/Zephyrus/installation'> Tasks </a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ServiceOrder so = task.getServiceOrder();
		Port port = findPortFromTaskID(task);


		// creating circuit due to "New" scenario
		DisconnectScenarioWorkflow wf = new DisconnectScenarioWorkflow(getOracleDaoFactory(),
				so);
		try {
			wf.deleteCircuit(taskID);;
		} catch (WorkflowException ex) {
			request.setAttribute("port", port);
			request.setAttribute("task", task);
			request.setAttribute("message", ex.getMessage() + " "
					+ ex.getCause().getMessage());
			return "provision/deleteCircuit.jsp";
		} finally {
			wf.close();
		}

		// sending redirect to page with confirmation
		request.setAttribute("message", "Circuit was successfully removed <br>"
				+ "<a href='/Zephyrus/provision'> <input type='button' value='Back to"
				+ " tasks' class='button'></a>");
		return PAGES.MESSAGE_PAGE.getValue();
	}
	
	/**
	 * Method for searching port by order task
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @param given task
	 * @return port object if exist, otherwise null.
	 * @author Miroshnychenko Nataliya
	 */

	private Port findPortFromTaskID(Task task) throws Exception{
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		if (serviceLocation == null){
			return null;
		} 
		Cable cable = getOracleDaoFactory().getCableDAO().findCableFromServLoc(serviceLocation.getId());
		return cable.getPort();
	}
}
