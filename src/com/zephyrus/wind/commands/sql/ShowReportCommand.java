package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.REPORT_TYPE;

public class ShowReportCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int reportType;
		// check the presence of task ID
		if (request.getParameter("reportType") == null) {
			request.setAttribute("message",
					"You must choose report type from reports page!");
			return "reportChoosing";
		}
		try {
			reportType = Integer.parseInt(request.getParameter("reportType"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("message",
					"You must choose report type from reports page!");
			return "reportChoosing";
		}
		
		if (reportType == REPORT_TYPE.DISCONNECT_ORDERS_PER_PERIOD.getId()) {
			return "reports/disconnectOrdersReport.jsp";
		}
		if (reportType == REPORT_TYPE.NEW_ORDERS_PER_PERIOD.getId()) {
			return "reports/newOrdersReport.jsp";			
		}
		if (reportType == REPORT_TYPE.PROFITABILITY_BY_MONTH.getId()) {
			return "reports/profitabilityReport.jsp";
		}
		return null;
	}

}
