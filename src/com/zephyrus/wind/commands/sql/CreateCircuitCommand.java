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
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * Uses for creating of circuit by provisioning engineer.  
 * 
 * @see com.zephyrus.wind.model.Task
 * @see com.zephyrus.wind.model.Port
 * @see com.zephyrus.wind.workflow.Workflow
 * @see com.zephyrus.wind.dao.interfaces.ITaskDAO
 * @see com.zephyrus.wind.dao.interfaces.IPortDAO
 * 
 * @return page with confirmation of successful creation of circuit
 * 
 * @author Alexandra Beskorovaynaya
 */

public class CreateCircuitCommand extends SQLCommand {

	/**
  	 * This method creates the circuit in the database. 
  	 * Method gets parameter of task's ID and Circuit configuration from JSP.
  	 * 
     * @see com.zephyrus.wind.model.Port
     * @see com.zephyrus.wind.model.Task
     * @see com.zephyrus.wind.workflow.Workflow
     * @see com.zephyrus.wind.dao.interfaces.ITaskDAO 
     * @see com.zephyrus.wind.dao.interfaces.IPortDAO
  	 * 
  	 * @return page with confirmation of successful creation of circuit
  	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID;
		
		User user = (User) request.getSession().getAttribute("user");
		
		//checking is user authorized
		if (user==null||user.getRole().getId()!=ROLE.PROVISION.getId()) {
			request.setAttribute("errorMessage", "You should login under "
					+ "Provisioning Engineer's account to view this page!"
					+ " <a href='/Zephyrus/view/login.jsp'>login</a>");
			return PAGES.MESSAGE_PAGE.getValue();
		} 
		
		//check the presence of task ID
		if (request.getParameter("taskId")==null) {
			request.setAttribute("errorMessage", "You must choose task from task's page!"
					+ "<a href='/Zephyrus/provision'> Tasks </a>");
		}
		try {
			taskID = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Task ID is not valid");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		String circuitConfig= request.getParameter("circuit");
		if (circuitConfig.equals("")) {
			request.setAttribute("error", "Circuit field can not be empty!");
			return "provision/createCircuit.jsp";
		}
		
		//getting Task and Port and Service Order by task ID
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		Task task = taskDao.findById(taskID);			
		ServiceOrder so =  task.getServiceOrder();
		
		//creating circuit due to "New" scenario
		NewScenarioWorkflow wf = new NewScenarioWorkflow(getOracleDaoFactory(), so);
		wf.createCircuit(taskID, circuitConfig);
		wf.close();
		
		//sending redirect to page with confirmation
		request.setAttribute("message", "New circuit successfully added" +
						"<a href='/Zephyrus/provision'>home page");	
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
