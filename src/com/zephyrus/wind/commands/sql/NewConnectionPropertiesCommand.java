package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
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
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to gather the information, needed on NewWorkflowTasks.jsp page.
 * 
 * @see com.zephyrus.wind.model.Cable
 * @see com.zephyrus.wind.model.Device
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * @see com.zephyrus.wind.dao.interfaces.IPortDAO
 * 
 * @return {@linkNewWorkflowTasks.jsp} page
 * @author Ielyzaveta Zubacheva
 */

public class NewConnectionPropertiesCommand extends SQLCommand {
	
	/**
	 * This method gathers the information, needed on NewWorkflowTasks.jsp page. 
	 * Method gets ID of the present task.
	 * All the information is sent to the page.
	 * 
	 * @see com.zephyrus.wind.model.Cable
	 * @see com.zephyrus.wind.model.Device
	 * @see com.zephyrus.wind.model.Port
	 * @see com.zephyrus.wind.model.Task
	 * @see com.zephyrus.wind.model.ServiceOrder
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
	 * @see com.zephyrus.wind.dao.interfaces.IPortDAO
	 * 
	 * @return {@linkNewWorkflowTasks.jsp} page
	 */
	
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
		
		String message = (String)request.getAttribute("message");
		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		

		port = portDAO.findById(portDAO.findFreePortID());
		
		if (port != null) {
			device = port.getDevice();
		}
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);

		cable = findCableByTask(task);
		
		
		request.getSession().setAttribute("task", task);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("cable", cable);
		request.setAttribute("message", message);
		
		return PAGES.INSTALLATIONNEWWORKFLOW_PAGE.getValue();
	}
	
	
	/**
	 * Method finds Cable object for Task
	 * 
	 * @param Task object
	 * @return existing Cable, otherwise null
	 * @author Miroshnychenko Nataliya
	 * @throws Exception 
	 */
	private Cable findCableByTask(Task task) throws Exception {
		Cable cable = new Cable();
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		cable =  cableDAO.findCableFromServLoc(serviceLocation.getId());
		return cable;
	}
	
	/**
	 * Method for searching ID of fist free port (without cable)
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO.getCableDAO
	 * @return ID of first free port or 0 if it doesn't exist
	 * @author Miroshnychenko Nataliya
	 */
	
	private int findFreePortID() throws Exception {
		ArrayList<Port> ports = getOracleDaoFactory().getPortDAO().findAll();
		ICableDAO cable = getOracleDaoFactory().getCableDAO();
			for (Port p: ports){
					if (!cable.existConnectToPort(p.getId())){
						return p.getId();
					} 
				}
	
		return 0;
	}


}
