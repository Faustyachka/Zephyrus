package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.REPORT_TYPE;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

public class ReportChoosingCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		// checking is user authorized
		if (request.getSession().getAttribute("user") == null) {
			request.setAttribute(
					"errorMessage",
					"You should login to view this page!<br>"
							+ " <a href='/Zephyrus/view/login.jsp'><input type='"
							+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		// Get user from HTTP session
		User user = (User) request.getSession().getAttribute("user");
		UserRole userRole = user.getRole();
		
		//TODO check role and show corresponding reports
		
		ArrayList<REPORT_TYPE> types = new ArrayList<>();
		for (REPORT_TYPE type: REPORT_TYPE.values()) {
			types.add(type);
		}
		
		request.setAttribute("types", types);
		return "reports/reports.jsp";
	}

}
