package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.model.ServiceInstance;

public class ModifyOrderCreateCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String si = request.getParameter("si");
		String product = request.getParameter("products");
		System.out.println(si);
		System.out.println(product);
		return null;
	}



}
