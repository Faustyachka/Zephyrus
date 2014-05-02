package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * 
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to create
 * new device in the system.
 * 
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
 * 
 * @return page with confirmation of successful creation of device
 * @author Ielyzaveta Zubacheva
 */

public class CreateDeviceCommand extends SQLCommand {

	/**
	 * This method creates the device in the database. Method gets parameter of
	 * device's serial number. By means of workflow, new object Device with
	 * mentioned parameter is created in the database.
	 * 
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
	 * 
	 * @return page with confirmation of successful deletion of cable
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

		String serialNum = request.getParameter("serialNum");
		int portQuantity = 60;

		if (serialNum.isEmpty()) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Serial number cannot be empty.");
			return "installation/createDevice.jsp";
		}

		final Pattern pattern = Pattern
				.compile("^[A-Za-z]{3}[0-9]{4}[A-Za-z0-9]{4}");
		final Matcher matcher = pattern.matcher(serialNum);

		if (!matcher.find()) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter()
					.write("Bad serial number! Serial number format must be as 'LLLNNNNSSS' "
							+ ", where L-letter symbol, N-number symbol, S-letter or number symbol");
			return "installation/createDevice.jsp";
		}

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();

		NewScenarioWorkflow wf = new NewScenarioWorkflow(getOracleDaoFactory(),
				order);
		try {
			wf.createRouter(taskID, serialNum, portQuantity);
		} catch (WorkflowException ex) {
			String message = ex.getMessage() + " " + ex.getCause().getMessage();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(message);
			return "installation/createDevice.jsp";
		} finally {
			wf.close();
		}
		request.setAttribute("message", "New device succesfully created!");
		request.setAttribute("taskId", taskID);
		return "newConnectionProperties";
	}

}
