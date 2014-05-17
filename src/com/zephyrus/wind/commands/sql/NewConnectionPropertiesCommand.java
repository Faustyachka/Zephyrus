package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
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
 * This class contains the method, that is declared in com.zephyrus.wind.commands.interfaces.SQLCommand. 
 * It is supposed to gather the information, needed on NewWorkflowTasks.jsp page.
 * 
 * @author Ielyzaveta Zubacheva & Alexandra Beskorovaynaya
 */

public class NewConnectionPropertiesCommand extends SQLCommand {
		
	private int taskID;
	private Cable cable = null;			
	private Port port = null;
	private Device device = null;
	
	/**
	 * This method gathers the information, needed on NewWorkflowTasks.jsp page. 
	 * Method gets ID of the present task.
	 * All the information is sent to the page.										
	 * 
	 * @return address of page for creation new connection due to New Scenario
	 * by Installation Engineer 
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
				
		User user = (User) request.getSession().getAttribute("user");
		
		//checking is user authorized
		if (user==null||user.getRole().getId()!=ROLE.INSTALLATION.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_INSTALL_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		
		//check the presence of task ID
		if (request.getAttribute("taskId")==null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		try {
		taskID = (int)request.getAttribute("taskId");					
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		String error = (String) request.getAttribute("error");
		String message = (String)request.getAttribute("message");	
		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		
		//find free port if it exist
		port = portDAO.findById(portDAO.findFreePortID());
		
		//get port's device
		if (port != null) {
			device = port.getDevice();							
		}
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		
		if (task == null) {
			request.setAttribute("messageNumber", MessageNumber.TASK_SELECTING_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		//find cable, connected by this task if it exist
		cable = findCableByTask(task);
		
		//set all necessary attributes
		request.getSession().setAttribute("task", task);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("cable", cable);
		request.setAttribute("message", message);
		request.setAttribute("error", error);
		
		return PAGES.INSTALLATIONNEWWORKFLOW_PAGE.getValue();
	}
	
	
	private Cable findCableByTask(Task task) throws Exception {
		Cable cable = new Cable();
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		cable =  cableDAO.findCableFromServLoc(serviceLocation.getId());
		return cable;
	}
	
	



}
