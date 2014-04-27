package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
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
		
		//check the presence of task ID
		if (request.getParameter("taskId").equals("")){
			request.setAttribute("error", "Please, choose the task from Tasks page!");
			return "provision/createCircuit.jsp";
		}		
		int taskID = Integer.parseInt(request.getParameter("taskId"));
		
		String circuitConfig= request.getParameter("circuit");
		if (circuitConfig.equals("")) {
			request.setAttribute("error", "Circuit field can not be empty!");
			return "provision/createCircuit.jsp";
		}
		
		//getting Task and Port and Service Order by task ID
		ITaskDAO taskDao = getOracleDaoFactory().getTaskDAO();
		Task task = taskDao.findById(taskID);		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		Port port = portDAO.findPortFromTaskID(task);		
		ServiceOrder so =  task.getServiceOrder();
		
		//creating circuit due to "New" scenario
		NewScenarioWorkflow wf = new NewScenarioWorkflow(so);
		wf.createCircuit(taskID, circuitConfig, port);
		
		//sending redirect to page with confirmation
		request.setAttribute("message", "New circuit successfully added" +
						"<a href='/Zephyrus/provision'>home page");	
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
