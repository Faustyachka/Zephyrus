package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.REPORT_TYPE;

/**
 * This class contains the method, that is declared in 
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It redirect
 * User from page of report choosing to the page of concrete report.
 * @author Alexandra Beskorovaynaya
 */
public class ShowReportCommand extends SQLCommand {
	
	/**
	 * This method redirects user from page of reports choosing to page of
	 * selected report. 
	 * @return address of page, which displays the selected report. In error
	 *         situation returns address of reports selection page, which also 
	 *         contains message about error details.
	 */
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
		} else if (reportType == REPORT_TYPE.NEW_ORDERS_PER_PERIOD.getId()) {
			return "reports/newOrdersReport.jsp";			
		} else if (reportType == REPORT_TYPE.PROFITABILITY_BY_MONTH.getId()) {
			return "reports/profitabilityReport.jsp";
		} else if (reportType == REPORT_TYPE.ROUTER_UTILIZATION.getId()) {
			return "utilizationReport?last=1";				
		} else if (reportType == REPORT_TYPE.MOST_PROFITABLE_ROUTER.getId()) {
			return "reports/mostProfitableRouterReport.jsp";
		} else {
			request.setAttribute("message", "Select report type from reports page!");
			return "reportChoosing";
		}
	}

}
