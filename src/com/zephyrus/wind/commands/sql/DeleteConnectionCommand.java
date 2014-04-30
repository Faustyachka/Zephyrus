package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;

public class DeleteConnectionCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID = 0 ;
		
		if (request.getParameter("task_id")!=null) {
			taskID =  Integer.parseInt(request.getParameter("task_id"));
		}else{
			System.out.println("no task " );
		}
		
		int cableID = 0;
		
		if (request.getParameter("cable")!=null) {
			cableID =  Integer.parseInt(request.getParameter("cable"));
		}else{
			System.out.println("no cable " );
		}
		
		int portID = 0;
		
		if (request.getParameter("port")!=null) {
			portID =  Integer.parseInt(request.getParameter("port"));
		}else{
			System.out.println("no port" );
		}
		
		Task task = new Task();
		ITaskDAO taskDAO = getOracleDaoFactory().getTaskDAO();
		task = taskDAO.findById(taskID);
		ServiceOrder order = task.getServiceOrder();
		
		ICableDAO cableDAO = getOracleDaoFactory().getCableDAO();
		Cable cable = cableDAO.findById(cableID);
		
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		Port port = portDAO.findById(portID);
				
//		DisconnectScenarioWorkflow wf = new NewScenarioWorkflow(getOracleDaoFactory(), order);
//		wf.removeCableFromPort(taskID, cable, port);
		
		request.setAttribute("message", "The connection successfully removed" +
				"<a href='/Zephyrus/installation'>home page");	
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
