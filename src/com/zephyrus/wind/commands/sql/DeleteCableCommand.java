/**
 * 
 */
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
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to delete the cable from the system.
 * 
 * @see com.zephyrus.wind.model.Cable
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
 * @see com.zephyrus.wind.dao.interfaces.IDeviceDAO
 * 
 * @return page with confirmation of successful deletion of cable
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */
public class DeleteCableCommand extends SQLCommand {
	
	/**
	 * This method deletes chosen cable from the database. 
	 * Method gets parameters of device's ID and port's ID, which cable is connected to.
	 * By means of DAO, chosen object Cable with mentioned parameters is deleted from database.
	 * 
	 * @see com.zephyrus.wind.model.Cable
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.model.Port
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
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
			return PAGES.MESSAGE_PAGE.getValue();
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
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ServiceOrder order = task.getServiceOrder();
		
		DisconnectScenarioWorkflow wf = new DisconnectScenarioWorkflow(getOracleDaoFactory(), order);
		try {
			wf.deleteCable(taskID);
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("taskId", taskID);
			request.setAttribute("message", "Failed to delete cable");
			return "disconnectConnectionProperties";
		} finally {
			wf.close();
		}
		
		//return message page
		request.setAttribute("message", "Cable deleted. Task completed! <br> <a href='/Zephyrus/installation'><input type='"
					+ "button' class='button' value='Bak to tasks'/></a>");		
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
