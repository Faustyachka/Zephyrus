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
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;

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

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID = (Integer) request.getAttribute("taskId");
		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		
		Port port = portDAO.findById(portDAO.findFreePortID());
		Device device = port.getDevice();
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();
		
		request.setAttribute("id", taskID);
		request.setAttribute("device", device);
		request.setAttribute("port", port);
		request.setAttribute("order", order);
		
		return PAGES.INSTALLATIONNEWWORKFLOW_PAGE.getValue();
	}


}
