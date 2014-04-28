package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;

/** 																										 	
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * It is supposed to assign task to the engineer.
 * 
 * @see com.zephyrus.wind.model.User
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.enums.ROLE
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * 
 * @return home page of user							
 * @author Alexandra Beskorovaynaya
 */
public class AssignTaskCommand extends SQLCommand {

	
	/**
  	 * This method assigns the task to user in the database. 
  	 * Method gets parameter of task's ID from JSP and User from session.
  	 * 
     * @see com.zephyrus.wind.model.User
     * @see com.zephyrus.wind.model.Task
     * @see com.zephyrus.wind.enums.ROLE
     * @see com.zephyrus.wind.dao.interfaces.ITaskDAO 
  	 * 
  	 * @return home page of user
  	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskId = Integer.parseInt(request.getParameter("id"));
		User user = (User) request.getSession().getAttribute("user");
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		Task task = taskDAO.findById(taskId);
		ServiceOrder order = task.getServiceOrder();
		NewScenarioWorkflow wf = new NewScenarioWorkflow(order);
		wf.assignTask(taskId, user.getId());
		
		
		if (user.getRole().getId()==ROLE.SUPPORT.getId()) {
			return "support";
		} if (user.getRole().getId()==ROLE.PROVISION.getId()) {
			return "provision";
		} if (user.getRole().getId()==ROLE.INSTALLATION.getId()) {
			return "installation";
		} else {
			return null;
		}

	}
}
