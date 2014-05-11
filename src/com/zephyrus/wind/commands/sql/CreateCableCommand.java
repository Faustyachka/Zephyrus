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
 * the cable in the system.
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */
public class CreateCableCommand extends SQLCommand {

	/**
	 * This method creates the cable in the database. Method gets parameters of
	 * device's ID and port's ID, which cable will be connected to. By means of
	 * workflow, new object Cable with mentioned parameters is created in the
	 * database.
	 * @return page with confirmation of successful creation of cable
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking if user authorized														
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

		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = null;
		try {
			wf = new NewScenarioWorkflow(getOracleDaoFactory(),	
					order);
			wf.createCable(taskID);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("error", "Failed to create cable!");		
			return "newConnectionProperties";
		} finally {
			wf.close();
		}

		request.setAttribute("taskId", taskID);
		request.setAttribute("message", "New cable successfully created!");
		return "newConnectionProperties";
	}
}
