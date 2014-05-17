package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.MessageNumber;
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
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to gather
 * the information, needed on DisconnectWorkflowTasks.jsp page.
 * @author Alexandra Beskorovaynaya
 */
public class DeleteConnectionPropertiesCommand extends SQLCommand {
	
	/**
	 * This method gathers the information, needed on DisconnectWorkflowTasks.jsp page. 
	 * Method gets ID of the present task.
	 * All the information is sent to the page.
	 * @return address of page with all necessary information for Disconnect scenario	
	 *         performing by Installation Engineer.								
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		User user = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (user == null || user.getRole().getId() != ROLE.INSTALLATION.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_INSTALL_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		// check the presence of task ID
		if (request.getAttribute("taskId") == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		int taskID = (int) request.getAttribute("taskId");

		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		if (task == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceOrder order = task.getServiceOrder();
		ServiceLocation loc = order.getServiceLocation();
		
		//take the message from other commands if it exist
		String message = (String) request.getAttribute("message");
		String error = (String) request.getAttribute("error");
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		
		Cable cable = null;															
		Port port = null;
		Device device = null;
		cable = cableDAO.findCableFromServLoc(loc.getId());

		if (cable != null) {
			port = cable.getPort();
		}

		if (port != null) {
			device = port.getDevice();
		}
		
		//send all necessary information to jsp
		request.setAttribute("task", task);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("order", order);
		request.setAttribute("cable", cable);
		request.setAttribute("message", message);
		request.setAttribute("error", error);

		return PAGES.INSTALLATIONDISCONNECTWORKFLOW_PAGE.getValue();
	}

}
