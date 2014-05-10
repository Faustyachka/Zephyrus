package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.ProfitabilityByMonthReport;
import com.zephyrus.wind.reports.rows.ProfitabilityByMonthRow;
																					// REVIEW: documentation expected
public class ProfitabilityByMonthCommand extends SQLCommand {
	private static Date date;
	final int MAX_PROVIDER_LOC_NUMBER = 10;

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
		String dateString = request.getParameter("month");	
		String dateWithDay="";
		if (dateString != null) {
			final Pattern pattern = Pattern
					.compile("^([0-9]){4}-([0-9]){2}$");
			final Matcher matcherFromDate = pattern.matcher(dateString);
			if (!matcherFromDate.matches()) {									
				request.setAttribute("message", "Wrong format of date!");
				return "reports/profitabilityReport.jsp";
			}
			dateWithDay = dateString+"-01";
			date = Date.valueOf(dateWithDay);
		} else {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/profitabilityReport.jsp";
		}
		ProfitabilityByMonthReport report = null;

		ArrayList<ProfitabilityByMonthRow> records = new ArrayList<>();
		ArrayList<ProfitabilityByMonthRow> checkRecords = new ArrayList<>();
		try {
			report = new ProfitabilityByMonthReport(date);							
			records = report.getReportData(last, MAX_PROVIDER_LOC_NUMBER);  
			checkRecords = report.getReportData(last + MAX_PROVIDER_LOC_NUMBER
					+ 1, 1);
			last = last + records.size();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/profitabilityReport.jsp";			
		}
		
		if (checkRecords.isEmpty()) {															
			request.setAttribute("hasNext", "notExist");
		} else {
			request.setAttribute("hasNext", "exist");
		}
		
		request.setAttribute("last", last);
		request.setAttribute("count", MAX_PROVIDER_LOC_NUMBER);
		request.setAttribute("records", records);
		request.setAttribute("date", dateString);
		return "reports/profitabilityReport.jsp";							
	}

}
