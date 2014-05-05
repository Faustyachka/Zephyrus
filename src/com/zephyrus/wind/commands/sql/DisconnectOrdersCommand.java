package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.DisconnectOrdersPerPeriodReport;
import com.zephyrus.wind.reports.rowObjects.DisconnectOrdersPerPeriodRow;

public class DisconnectOrdersCommand extends SQLCommand {
	
	private static Date fromDate;
	private static Date toDate;
	private final int NUMBER_RECORDS_PER_PAGE = 20;
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int last = Integer.parseInt(request.getParameter("last"));
		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");
		if (fromDateString != null && toDateString != null) {
			final Pattern pattern = Pattern
					.compile("^([0-9]){4}-([0-9]){2}-([0-9]){2}");
			final Matcher matcherFromDate = pattern.matcher(fromDateString);
			final Matcher matcherToDate = pattern.matcher(fromDateString);
			if (!matcherFromDate.find()||!matcherToDate.find()) {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/newOrdersReport.jsp";
			}
			fromDate = Date.valueOf(fromDateString);
			toDate = Date.valueOf(toDateString);
		}
		DisconnectOrdersPerPeriodReport report = null;

		ArrayList<DisconnectOrdersPerPeriodRow> records = new ArrayList<>();
		try {
			report = new DisconnectOrdersPerPeriodReport(fromDate, toDate);
			records = report.getReportData(last, NUMBER_RECORDS_PER_PAGE);
			last = last + records.size();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/disconnectOrdersReport.jsp";
		}

		request.setAttribute("records", records);
		request.setAttribute("fromDate", fromDate.toString());
		request.setAttribute("toDate", toDate.toString());
		request.setAttribute("last", last);
		return "reports/disconnectOrdersReport.jsp";
	}

}
