package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.RouterUtilizationReport;
import com.zephyrus.wind.reports.rows.RouterUtilRow;

public class UtilizationReportCommand extends SQLCommand {

	private final int NUMBER_RECORDS_PER_PAGE = 20;

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		int last;
		if (request.getParameter("last") == null) {
			request.setAttribute("message", "Failed to create report");
			return "reports/utilizationReport.jsp";
		}

		try {
			last = Integer.parseInt(request.getParameter("last"));

		} catch (NumberFormatException ex) {
			request.setAttribute("message", "Failed to create report");
			return "reports/utilizationReport.jsp";
		}
		RouterUtilizationReport report = null;

		ArrayList<RouterUtilRow> records = new ArrayList<>();
		ArrayList<RouterUtilRow> checkRecords = new ArrayList<>();
		try {
			report = new RouterUtilizationReport();
			records = report.getReportData(last, NUMBER_RECORDS_PER_PAGE);
			checkRecords = report.getReportData(last + NUMBER_RECORDS_PER_PAGE
					+ 1, 1);
			last = last + records.size();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/utilizationReport.jsp";
		}

		if (checkRecords.isEmpty()) {
			request.setAttribute("next", "0");
		} else {
			request.setAttribute("next", "1");
		}
		request.setAttribute("last", last);
		request.setAttribute("records", records);
		request.setAttribute("count", NUMBER_RECORDS_PER_PAGE);

		return "reports/utilizationReport.jsp";
	}

}
