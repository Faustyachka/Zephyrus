package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.workflow.NewScenarioWorkflow;

public class CreateConnectionCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int taskID = Integer.parseInt(request.getParameter("taskID"));
		int portNum = Integer.parseInt(request.getParameter("portNum"));
		int cableID = Integer.parseInt(request.getParameter("cableID"));
		
		Task task = new Task();
		ServiceOrder order = task.getServiceOrder();
		
		Cable cable = new Cable();
		IPortDAO portDAO = getOracleDaoFactory().getPortDAO();
		Port port = portDAO.findById(portNum);
		cable.setId(cableID);
		
		NewScenarioWorkflow nw = new NewScenarioWorkflow(order);
		nw.plugCableToPort(taskID, cable, port);
		
		request.setAttribute("message", "New connection successfully created" +
				"<a href='/Zephyrus/installation'>home page");	
		return PAGES.MESSAGE_PAGE.getValue();
	}

}
