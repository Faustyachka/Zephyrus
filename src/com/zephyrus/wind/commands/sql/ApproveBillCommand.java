package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;

/** 																										 	
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to approve bill by customer support engineer and by this action complete the task due to 
 * given scenario.
 * 
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.enums.ROLE
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * @see com.zephyrus.wind.workflow.NewScenarioWorkflow
 * 
 * @return information about bill approving							
 * @author Alexandra Beskorovaynaya
 */
public class ApproveBillCommand extends SQLCommand {
	
	/** 																										 	
	 * This method supposed to approve bill by customer support engineer and by this action complete the task due to 
	 * given scenario.
	 * 
	 * @see com.zephyrus.wind.model.Task
	 * @see com.zephyrus.wind.model.ServiceOrder
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.enums.ROLE
	 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
	 * @see com.zephyrus.wind.workflow.NewScenarioWorkflow
	 * 
	 * @return information about bill approving							
	 * @author Alexandra Beskorovaynaya
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID = Integer.parseInt(request.getParameter("taskId"));
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();
		NewScenarioWorkflow wf = new NewScenarioWorkflow(order);
		wf.approveBill(taskID);
		request.setAttribute("message", "Bill succesfully approved! <a href='"+ROLE.SUPPORT.getHome() +"'/> home page");
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
