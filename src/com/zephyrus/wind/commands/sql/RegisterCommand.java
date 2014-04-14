package com.zephyrus.wind.commands.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.managers.ConnectionManager;

public class RegisterCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String SHApassword = SHAHashing.getHash(password);
		Connection conn = ConnectionManager.INSTANCE.getConnection();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO MISTERDAN.USERS (EMAIL, PASSWORD, ROLE_ID) VALUES(?,?,?)");
		stmt.setString(1, username);
		stmt.setString(2, SHApassword);
		stmt.setInt(3, ROLE.CUSTOMER.getId());
	    stmt.executeUpdate();
	    request.setAttribute("message", "You have successfully registered!");
		return Pages.MESSAGE_PAGE.getValue();
	}

}
