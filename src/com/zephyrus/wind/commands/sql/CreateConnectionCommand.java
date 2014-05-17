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
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in 	
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new connection to the service location in the system.
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */

public class CreateConnectionCommand extends SQLCommand {

	/**
	 * This method creates the connection in the database. Method gets
	 * parameters of task's ID, number of port and cable's ID, which indicate
	 * what service order connection is being created for and what port and
	 * cable will be user for the connection.  
	 * @return method returns address of confirmation page, which states that
	 *  connection has been created successfully
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.INSTALLATION.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_INSTALL_ERROR.getId());
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

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);

		if (task == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = null;
		try {
			wf = new NewScenarioWorkflow(getOracleDaoFactory(),
					order);
			wf.plugCableToPort(taskID);

		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("error", "Failed to create connection");
			return "newConnectionProperties";
		} finally {
			wf.close();
		}

		request.setAttribute("messageNumber", MessageNumber.TASK_COMPLETED_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
