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

public class DeleteConnectionPropertiesCommand extends SQLCommand {

	public int taskID;
	public Cable cable = null;
	public Port port = null;
	public Device device = null;
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		User user = (User) request.getSession().getAttribute("user");
		
		//checking is user authorized
		if (user==null||user.getRole().getId()!=ROLE.INSTALLATION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Installation Engineer's account to view this page!"
					+ " <a href='/Zephyrus/view/login.jsp'>login</a>");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		
		//check the presence of task ID
		if (request.getAttribute("taskId")==null) {
			request.setAttribute("errorMessage", "You must choose task from task's page!"
					+ "<a href='/Zephyrus/installation'> Tasks </a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		taskID = (int)request.getAttribute("taskId");
						
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();
		ServiceLocation loc = order.getServiceLocation();
		
		String message = (String)request.getAttribute("message");
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		cable = cableDAO.findCableFromServLoc(loc.getId());
		
		if (cable!=null) {
		port = cable.getPort();
		
		}		
		
		if (port!=null) {
			device = port.getDevice();
		}
		
		request.getSession().setAttribute("task", task);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("order", order);
		request.setAttribute("cable", cable);
		request.setAttribute("message", message);
		
		return PAGES.INSTALLATIONDISCONNECTWORKFLOW_PAGE.getValue();
	}


}
