package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.MostProfitableRouterReport;
import com.zephyrus.wind.reports.rows.MostProfitableRouterRow;
																				// REVIEW: documentation expected
public class MostProfitableRouterReportCommand extends SQLCommand {
	
	private static Date fromDate;									
	private static Date toDate;	
	private final int NUMBER_RECORDS_PER_PAGE = 1;
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");
		if (fromDateString != null && toDateString != null) {
			final Pattern pattern = Pattern
					.compile("^([0-9]){4}-([0-9]){2}-([0-9]){2}$");
			final Matcher matcherFromDate = pattern.matcher(fromDateString);
			final Matcher matcherToDate = pattern.matcher(toDateString);
			if (!matcherFromDate.matches()||!matcherToDate.matches()) {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/mostProfitableRouterReport.jsp";
			}
			fromDate = Date.valueOf(fromDateString);
			toDate = Date.valueOf(toDateString);
		} else {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/mostProfitableRouterReport.jsp";
		}
		MostProfitableRouterReport report = null;

		ArrayList<MostProfitableRouterRow> records = new ArrayList<>();
		try {
			report = new MostProfitableRouterReport(fromDate, toDate);		

			records = report.getReportData(1, NUMBER_RECORDS_PER_PAGE);		

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/mostProfitableRouterReport.jsp";
		}

		request.setAttribute("records", records);
		request.setAttribute("fromDate", fromDate.toString());
		request.setAttribute("toDate", toDate.toString());
		return "reports/mostProfitableRouterReport.jsp";		
	}

}
