package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for displaying of
 * circuit updating details to provisioning engineer.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class ModifyCircuitViewCommand extends SQLCommand {

	/**
	 * This method checks all necessary input data and forms the necessary
	 * information for circuit updating.
	 * 
	 * @return the page of circuit updating. In error situation returns the page
	 *         with message about error details.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.PROVISION.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_PROVISION_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getAttribute("taskId") == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		int taskID = (int)request.getAttribute("taskId");


		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskID);
		
		if (task == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		Port port = findPortFromTaskID(task);
		request.setAttribute("circuit", task.getServiceOrder().getServiceInstance().getCircuit());
		request.setAttribute("port", port);
		request.setAttribute("task", task);
		return "provision/modifyCircuit.jsp";	
	}

	/**
	 * Method for searching port by order task											// REVIEW: see remarks in other Commands
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @param given
	 *            task
	 * @return port object if exist, otherwise null.
	 * @author Miroshnychenko Nataliya
	 */

	private Port findPortFromTaskID(Task task) throws Exception {
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
