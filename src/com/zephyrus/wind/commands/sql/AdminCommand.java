package com.zephyrus.wind.commands.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.managers.ConnectionManager;

public class AdminCommand extends SQLCommand  {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		Connection conn = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MISTERDAN.USERS");
	    ResultSet rs = stmt.executeQuery();
	    ArrayList<String> logins = new ArrayList<String>();
	    while(rs.next()){
	    	logins.add(rs.getString(2));
	    }
	    request.setAttribute("logins", logins);
		return Pages.ADMIN_PAGE.getValue();
	}


}
