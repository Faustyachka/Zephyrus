package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.REPORT_TYPE;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;
																						// REVIEW: documentation expected
public class ReportChoosingCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		// checking is user authorized
		if (request.getSession().getAttribute("user") == null) {
			request.setAttribute(
					"errorMessage",
					"You should login to view this page!<br>"
							+ " <a href='/Zephyrus/view/login.jsp'><input type='"		// REVIEW: HTML
							+ "button' class='button' value='Login'/></a>");		
			return PAGES.MESSAGE_PAGE.getValue();
		}
		// Get user from HTTP session
		User user = (User) request.getSession().getAttribute("user");					// REVIEW: why don't you make assignment of "user" attribute to some value and then check it?
		UserRole userRole = user.getRole();
		if (userRole.getId() == ROLE.ADMIN.getId()) {
			ArrayList<REPORT_TYPE> types = new ArrayList<>();
			for (REPORT_TYPE type: REPORT_TYPE.values()) {
				types.add(type);
			}			
			request.setAttribute("types", types);
			return "admin/reports.jsp";
		}
																					// REVIEW: else if should be used
		if (userRole.getId() == ROLE.INSTALLATION.getId()) {
			ArrayList<REPORT_TYPE> types = new ArrayList<>();
		    types.add(REPORT_TYPE.MOST_PROFITABLE_ROUTER);
		    types.add(REPORT_TYPE.ROUTER_UTILIZATION);
			request.setAttribute("types", types);
			return "installation/reports.jsp";										// REVIEW: hardcoded link
		}																			// REVIEW: else if should be used
		if (userRole.getId() == ROLE.PROVISION.getId()) {
			ArrayList<REPORT_TYPE> types = new ArrayList<>();
		    types.add(REPORT_TYPE.MOST_PROFITABLE_ROUTER);
		    types.add(REPORT_TYPE.ROUTER_UTILIZATION);
			request.setAttribute("types", types);
			return "provision/reports.jsp";
		}																			// REVIEW: else if should be used
		if (userRole.getId() == ROLE.SUPPORT.getId()) {
			ArrayList<REPORT_TYPE> types = new ArrayList<>();
		    types.add(REPORT_TYPE.DISCONNECT_ORDERS_PER_PERIOD);
		    types.add(REPORT_TYPE.NEW_ORDERS_PER_PERIOD);
		    types.add(REPORT_TYPE.PROFITABILITY_BY_MONTH);
			request.setAttribute("types", types);
			return "support/reports.jsp";
		} else {
			request.setAttribute("message", "There are no available reports for you!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
	}

}
