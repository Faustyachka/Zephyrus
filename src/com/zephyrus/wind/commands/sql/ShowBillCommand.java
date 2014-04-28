package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Task;

/** 																										 	
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to show the details of bill to customer support engineer for the completing of 
 * approve bill command.
 * 
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * 
 * @return page with order details							
 * @author Alexandra Beskorovaynaya
 */
public class ShowBillCommand extends SQLCommand {
	
	/** 
	 * This method checks the presence of task ID to confirm that user is redirected
	 * to this page from tasks page and shows the page of task's order detail.																										 	
	 * 
	 * @see com.zephyrus.wind.model.Task
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
	 * 
	 * @return page with order details							
	 * @author Alexandra Beskorovaynaya
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		if (request.getAttribute("taskId")==null) {
			request.setAttribute("errorMessage", "Please, choose the task from Tasks page!");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		
		int taskID = (int)request.getAttribute("taskId");
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskID);
		request.setAttribute("task", task);
		return "support/approveBill.jsp";
	}

}
