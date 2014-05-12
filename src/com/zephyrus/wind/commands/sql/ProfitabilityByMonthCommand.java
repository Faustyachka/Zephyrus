package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

		// if parameter last is null it means that user called this command not
		// from report page
		if (request.getParameter("last") == null) {
			return "reports/newOrdersReport.jsp";
		}

		try {
			last = Integer.parseInt(request.getParameter("last"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return "reports/newOrdersReport.jsp";
		}
		String dateString = request.getParameter("month");
		String dateWithDay = "";
		if (dateString == null) {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/profitabilityReport.jsp";
		}
		dateWithDay = dateString + "-01";
		if (isDateValid(dateWithDay)) {
			date = Date.valueOf(dateWithDay);
		} else {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/profitabilityReport.jsp";
		}

		// get current sql date
		java.util.Date utilDate = new java.util.Date();
		Date today = new Date(utilDate.getTime());

		// check is date in future
		if (today.compareTo(date) < 0) {
			request.setAttribute("message",
					"Wrong format of date! Date must be in past or present.");
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
			request.setAttribute("hasNext", "1");
		}

		request.setAttribute("last", last);
		request.setAttribute("count", MAX_PROVIDER_LOC_NUMBER);
		request.setAttribute("records", records);
		request.setAttribute("date", dateString);
		return "reports/profitabilityReport.jsp";
	}

	private boolean isDateValid(String value) {

		if (value == null) {
			return false;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);

		try {
			formatter.parse(value);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}
