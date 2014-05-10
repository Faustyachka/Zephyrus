package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.NewOrdersPerPeriodReport;
import com.zephyrus.wind.reports.rows.NewOrdersPerPeriodRow;
																				// REVIEW: documentation expected
public class NewOrdersCommand extends SQLCommand {
	private static Date fromDate;										
	private static Date toDate;
	private final int NUMBER_RECORDS_PER_PAGE = 10;

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int last;
		
		//if parameter last is null it means that user called this command not from report page
		if (request.getParameter("last")==null) {											
			return "reports/newOrdersReport.jsp";		
		}
		
		try {
			last=Integer.parseInt(request.getParameter("last"));			
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return "reports/newOrdersReport.jsp";								
		}
		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");
		if (fromDateString != null && toDateString != null) {
			final Pattern pattern = Pattern
					.compile("^([0-9]){4}-([0-9]){2}-([0-9]){2}$");
			final Matcher matcherFromDate = pattern.matcher(fromDateString);
			final Matcher matcherToDate = pattern.matcher(toDateString);
			if (!matcherFromDate.matches()||!matcherToDate.matches()) {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/newOrdersReport.jsp";
			}
			fromDate = Date.valueOf(fromDateString);
			toDate = Date.valueOf(toDateString);
		} else {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/newOrdersReport.jsp";
		}
		NewOrdersPerPeriodReport report = null;

		ArrayList<NewOrdersPerPeriodRow> records = new ArrayList<>();
		ArrayList<NewOrdersPerPeriodRow> checkRecords = new ArrayList<>();
		try {
			report = new NewOrdersPerPeriodReport(fromDate, toDate);				
			records = report.getReportData(last, NUMBER_RECORDS_PER_PAGE);
			checkRecords = report.getReportData(last + NUMBER_RECORDS_PER_PAGE
					+ 1, 1);
			last = last + records.size();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/newOrdersReport.jsp";
		}
		if (checkRecords.isEmpty()) {															
			request.setAttribute("hasNext", "false");
		} else {
			request.setAttribute("hasNext", "exist");
		}
		request.setAttribute("records", records);
		request.setAttribute("fromDate", fromDate.toString());
		request.setAttribute("toDate", toDate.toString());
		request.setAttribute("last", last);
		request.setAttribute("count", NUMBER_RECORDS_PER_PAGE);
		return "reports/newOrdersReport.jsp";					
	}

}
