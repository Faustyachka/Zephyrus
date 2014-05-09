package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.DisconnectScenarioWorkflow;
import com.zephyrus.wind.workflow.ModifyScenarioWorkflow;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
import com.zephyrus.wind.workflow.Workflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to assign
 * task to the engineer.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class AssignTaskCommand extends SQLCommand {

	/**
	 * This method assigns the task to user in the database. Method gets
	 * parameter of task's ID from JSP and User from session.
	 * @return url of home page of user
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskId;
		User user = (User) request.getSession().getAttribute("user");
		// checking is user authorized
		if (user == null) {
			request.setAttribute(
					"messageNumber", MessageNumber.LOGIN_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// checking is user logged in under engineer's account
		if (user.getRole().getId() != ROLE.INSTALLATION.getId()
				&& user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute(
					"messageNumber", MessageNumber.LOGIN_INSTALL_PROVISION_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("id") == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			taskId = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskId);

		if (task == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceOrder order = task.getServiceOrder();

		Workflow wf = null;
		
		// check the scenario for given task
		try {
			if (order.getOrderType().getId() == ORDER_TYPE.NEW.getId()) {
				wf = new NewScenarioWorkflow(getOracleDaoFactory(), order);
			}
			if (order.getOrderType().getId() == ORDER_TYPE.DISCONNECT.getId()) {
				wf = new DisconnectScenarioWorkflow( 
						getOracleDaoFactory(), order);
			}
			if (order.getOrderType().getId() == ORDER_TYPE.MODIFY.getId()) {
				wf = new ModifyScenarioWorkflow(
						getOracleDaoFactory(), order);
			}
			wf.assignTask(taskId, user.getId());
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("errorMessage", "Failed to assign task");
			return PAGES.MESSAGE_PAGE.getValue();
		} finally {
			wf.close();
		}

		// return the page in dependence of user's role
		if (user.getRole().getId() == ROLE.PROVISION.getId()) {
			return "provision";
		}
		if (user.getRole().getId() == ROLE.INSTALLATION.getId()) {
			return "installation";
		} else {
			return null;
		}

	}
}
