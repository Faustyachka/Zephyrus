package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;

public class TaskRedirectorCommand extends SQLCommand {
	
	//TODO write documentation, correct return parameters

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		User user = (User) request.getSession().getAttribute("user");
		int taskId = Integer.parseInt(request.getParameter("id"));
		
		request.setAttribute("taskId", taskId);
		if (user.getRole().getId()==ROLE.INSTALLATION.getId()) {
			return "installation/dashboard.jsp";
		} if (user.getRole().getId()==ROLE.PROVISION.getId()){
			return "provision/createCircuit.jsp";			
		} if (user.getRole().getId()==ROLE.SUPPORT.getId()) {
			return "provision/dashboard.jsp";
		}
		return null;
	}

}
