package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;

public class DeviceCreationPropertiesCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		if (request.getParameter("task_id")!=null) {
			int id =  Integer.parseInt(request.getParameter("task_id"));
			System.out.println("device's task = " + id);
			request.setAttribute("taskId", id);
		}else{
			System.out.println("no attr " );
		}
		
		
		
		return PAGES.CREATEDEVICE_PAGE.getValue();
	}

}
