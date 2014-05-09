package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link							// REVIEW: link isn't working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to gather
 * the information, needed on DisconnectWorkflowTasks.jsp page.
 * 
 * @return page of tasks for Disconnecting scenario										// REVIEW: return in class docs
 * @author Alexandra Beskorovaynaya
 */
public class DeleteConnectionPropertiesCommand extends SQLCommand {

	public int taskID;																	// REVIEW: public fields in class
	public Cable cable = null;															// REVIEW: and do you need them here?
	public Port port = null;
	public Device device = null;
	
	/**
	 * This method gathers the information, needed on DisconnectWorkflowTasks.jsp page. 
	 * Method gets ID of the present task.
	 * All the information is sent to the page.
	 * 
	 * @return page with all necessary information for Disconnect scenario	
	 * performing by Installation Engineer											// REVIEW: unclear sentence
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.INSTALLATION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Installation Engineer's account to view this page!<br>"		// REVIEW: HTML
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getAttribute("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"		// REVIEW: HTML
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		taskID = (int) request.getAttribute("taskId");

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"		// REVIEW: HTML
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceOrder order = task.getServiceOrder();
		ServiceLocation loc = order.getServiceLocation();
		
		//take the message from other commands if it exist
		String message = (String) request.getAttribute("message");
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		cable = cableDAO.findCableFromServLoc(loc.getId());

		if (cable != null) {
			port = cable.getPort();
		}

		if (port != null) {
			device = port.getDevice();
		}
		
		//send all necessary information to jsp
		request.getSession().setAttribute("task", task);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("order", order);
		request.setAttribute("cable", cable);
		request.setAttribute("message", message);								// REVIEW: "message" or "errorMessage"

		return PAGES.INSTALLATIONDISCONNECTWORKFLOW_PAGE.getValue();
	}

}
