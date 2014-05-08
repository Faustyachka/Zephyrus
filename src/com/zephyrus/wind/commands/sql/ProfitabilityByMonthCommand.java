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

public class ProfitabilityByMonthCommand extends SQLCommand {
	private static Date date;

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String dateString = request.getParameter("month");	
		String dateWithDay="";
		if (dateString != null) {
			final Pattern pattern = Pattern
					.compile("^([0-9]){4}-([0-9]){2}$");
			final Matcher matcherFromDate = pattern.matcher(dateString);
			if (!matcherFromDate.find()) {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/profitabilityReport.jsp";
			}
			dateWithDay = dateString+"-01";
			date = Date.valueOf(dateWithDay);
		}
		ProfitabilityByMonthReport report = null;

		ArrayList<ProfitabilityByMonthRow> records = new ArrayList<>();
		try {
			report = new ProfitabilityByMonthReport(date);
			final int MAX_PROVIDER_LOC_NUMBER = 10;
			records = report.getReportData(1, MAX_PROVIDER_LOC_NUMBER);  // REVIEW: this params should be changed - paging should be implemented in this command
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/profitabilityReport.jsp";
		}

		request.setAttribute("records", records);
		request.setAttribute("date", dateString);
		return "reports/profitabilityReport.jsp";
	}

}
