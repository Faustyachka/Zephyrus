package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

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
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand. // REVIEW: link is not working
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
 * @return {@linkNewWorkflowTasks.jsp} page													// REVIEW: return
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */

public class NewConnectionPropertiesCommand extends SQLCommand {
	
	/**
	 * This method gathers the information, needed on NewWorkflowTasks.jsp page. 
	 * Method gets ID of the present task.
	 * All the information is sent to the page.										// REVIEW: why documentation about the method is placed away from the method?
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
	 * @return {@linkNewWorkflowTasks.jsp} page										// REVIEW: link is not working
	 */
	
	public int taskID;
	public Cable cable = null;														// REVIEW: public class scope variables
	public Port port = null;
	public Device device = null;
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
				
		User user = (User) request.getSession().getAttribute("user");
		
		//checking is user authorized
		if (user==null||user.getRole().getId()!=ROLE.INSTALLATION.getId()) {
			request.setAttribute("errorMessage", "You should login under "		// REVIEW: HTML
					+ "Installation Engineer's account to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		
		//check the presence of task ID
		if (request.getAttribute("taskId")==null) {
			request.setAttribute("errorMessage", "You must choose task from task's page!<br>"
					+ "<a href='/Zephyrus/installation'><input type='"			// REVIEW: HTML
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		taskID = (int)request.getAttribute("taskId");							// REVIEW: watch null pointer exception
		
		String message = (String)request.getAttribute("message");				// REVIEW: watch null pointer exception
		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		
		//find free port if it exist
		port = portDAO.findById(portDAO.findFreePortID());
		
		//get port's device
		if (port != null) {
			device = port.getDevice();											// REVIEW: what if port is null. than device will be uninitialized
		}
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		
		if (task == null) {
			request.setAttribute("errorMessage",
					"You must choose task from task's page!<br>"
							+ "<a href='/Zephyrus/installation'><input type='"	// REVIEW: HTML
					+ "button' class='button' value='Tasks'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		//find cable, connected by this task if it exist
		cable = findCableByTask(task);											// REVIEW: what if it's null?
		
		//set all necessary attributes
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
	 * @author Miroshnychenko Nataliya													// REVIEW: author is not needed here
	 * @throws Exception 																// REVIEW: description expected
	 */
	private Cable findCableByTask(Task task) throws Exception {
		Cable cable = new Cable();
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		cable =  cableDAO.findCableFromServLoc(serviceLocation.getId());
		return cable;
	}
	
	



}
