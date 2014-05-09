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

/**
 * This class contains the method, that is declared in @link									// REVIEW: link isn't working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for displaying of
 * circuit creation details to provisioning engineer.
 * 
 * @return page with information about creation of circuit										// REVIEW: return. again.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class CreateCircuitViewCommand extends SQLCommand {

	/**
	 * This method checks all necessary input data and forms the necessary						// REVIEW: how does it form "necessary information"?
	 * information for circuit creation.
	 * 
	 * @return the page of circuit creation. In error situation returns the page				// REVIEW: "returns the page with message". what does it mean?
	 *         with message about error details.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Provisioning Engineer's account to view this page! <br>"					// REVIEW: HTML
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getParameter("taskId") == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/provision'><input type='"						// REVIEW: HTML
					+ "button' class='button' value='Tasks'/></a>");
		}
		try {
			taskID = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskID);
		
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!"									// REVIEW: HTML
							+ "<a href='/Zephyrus/provision'> Tasks </a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		Port port = findPortFromTaskID(task);
		request.setAttribute("port", port);
		request.setAttribute("task", task);
		return "provision/createCircuit.jsp";
	}

	/**
	 * Method for searching port by order task
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @param given																				// REVIEW: wrong documentation format: param name isn't specified
	 *            task
	 * @return port object if exist, otherwise null.
	 * @author Miroshnychenko Nataliya
	 */

	private Port findPortFromTaskID(Task task) throws Exception {								// REVIEW: find from task ID, but Task param was given
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		if (serviceLocation == null) {
			return null;
		}
		Cable cable = getOracleDaoFactory().getCableDAO().findCableFromServLoc(
				serviceLocation.getId());
		return cable.getPort();
	}

}
