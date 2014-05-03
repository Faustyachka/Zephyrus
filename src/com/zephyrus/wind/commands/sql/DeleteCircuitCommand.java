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
					+ "Provisioning Engineer's account to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/provision'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
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
					"You must choose task from task's page<br>!"
							+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ServiceOrder so = task.getServiceOrder();


		// creating circuit due to "New" scenario
		DisconnectScenarioWorkflow wf = new DisconnectScenarioWorkflow(getOracleDaoFactory(),
				so);
		try {
			wf.deleteCircuit(taskID);;
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			throw new Exception(ex.getCause().getMessage());
		} finally {
			wf.close();
		}

		// sending redirect to page with confirmation
		request.setAttribute("message", "Circuit was successfully removed <br>"
				+ "<a href='/Zephyrus/provision'> <input type='button' value='Back to"
				+ " tasks' class='button'></a>");
		return PAGES.MESSAGE_PAGE.getValue();
	}
	
	
}
