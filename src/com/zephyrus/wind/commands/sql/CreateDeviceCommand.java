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
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * 
 * This class contains the method, that is declared in 	
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new device in the system.
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */

public class CreateDeviceCommand extends SQLCommand {

	/**
	 * This method creates the device in the database. Method gets parameter of
	 * device's serial number. By means of Workflow, new object Device with
	 * mentioned parameter is created in the database.
	 * @return address of New connection properties page with confirmation
	 *         that device was created successfully. In error situation returns
	 *         the address of message page, which displays the error message
	 */
	@Override
	protected String doExecute(HttpServletRequest request,									// REVIEW: method is too long and should be split
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
        
		if (request.getParameter("serialNum") == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		String serialNum = request.getParameter("serialNum");
		int portQuantity = 60;
		
		if (serialNum.isEmpty()) {											
			request.setAttribute("error", "Serial number can not be empty!");
			request.setAttribute("taskId", taskID);
			return "installation/createDevice.jsp";
		}

		final Pattern pattern = Pattern
				.compile("^[A-Za-z]{3}[0-9]{4}[A-Za-z0-9]{4}");
		final Matcher matcher = pattern.matcher(serialNum);

		if (!matcher.find()) {
			request.setAttribute("error", "Serial number is not valid!");				
			request.setAttribute("taskId", taskID);
			return "installation/createDevice.jsp";
		}

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = null;
		try {
			wf = new NewScenarioWorkflow(getOracleDaoFactory(),	
					order);
			wf.createRouter(taskID, serialNum, portQuantity);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("error", "Failed to create device");
			return "newConnectionProperties";
		} finally {
			wf.close();
		}
		request.setAttribute("message", "New device succesfully created!");
		request.setAttribute("taskId", taskID);
		return "newConnectionProperties";
	}

}
