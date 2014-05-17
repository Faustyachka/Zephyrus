package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.DisconnectScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for deleting of
 * connection by installation engineer.
 * @author Alexandra Beskorovaynaya
 */
public class DeleteConnectionCommand extends SQLCommand {
	
	/**
	 * This method deletes the connection from the database due to the
	 * Disconnect Scenario Workflow.
	 * @return method returns address of confirmation page, which states that
	 *  connection has been deleted successfully. In error situation returns
	 *  the address of page with message about error details.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.INSTALLATION.getId()) {
			request.setAttribute("messageNumber",
					MessageNumber.LOGIN_INSTALL_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("messageNumber",
					MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber",
					MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		if (task == null) {
			request.setAttribute("messageNumber",
					MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceOrder order = task.getServiceOrder();

		DisconnectScenarioWorkflow wf = null;
		try {
			wf = new DisconnectScenarioWorkflow(getOracleDaoFactory(), order);
			wf.unplugCableFromPort(taskID);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("message", "Failed to delete connection");
			return "disconnectConnectionProperties"; 
		} finally {
			wf.close();
		}

		request.setAttribute("message", "The connection successfully removed");
		request.setAttribute("taskId", taskID);
		return "disconnectConnectionProperties";
	}

}
