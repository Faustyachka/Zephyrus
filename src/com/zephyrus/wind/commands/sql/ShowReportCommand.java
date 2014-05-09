package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.REPORT_TYPE;
																				// REVIEW: documentation expected
public class ShowReportCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int reportType;
		// check the presence of task ID
		if (request.getParameter("reportType") == null) {
			request.setAttribute("message",
					"You must choose report type from reports page!");
			return "reportChoosing";											// REVIEW: hardcoded link
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
			return "reports/disconnectOrdersReport.jsp";						// REVIEW: hardcoded links
		}																		// REVIEW: else if, not if
		if (reportType == REPORT_TYPE.NEW_ORDERS_PER_PERIOD.getId()) {
			return "reports/newOrdersReport.jsp";			
		}
		if (reportType == REPORT_TYPE.PROFITABILITY_BY_MONTH.getId()) {
			return "reports/profitabilityReport.jsp";
		}
		if (reportType == REPORT_TYPE.ROUTER_UTILIZATION.getId()) {
			return "utilizationReport?last=1";								// REVIEW: last=1? what so special about this report, that it has link, different from others
		}
		if (reportType == REPORT_TYPE.MOST_PROFITABLE_ROUTER.getId()) {
			return "reports/mostProfitableRouterReport.jsp";
		}
		return null;
	}

}
