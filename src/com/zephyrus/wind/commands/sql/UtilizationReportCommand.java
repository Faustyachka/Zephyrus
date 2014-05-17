package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.RouterUtilizationReport;
import com.zephyrus.wind.reports.rows.RouterUtilRow;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for displaying of
 * "Router utilization and capacity" report data on jsp page.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class UtilizationReportCommand extends SQLCommand {
	
	/**The quantity of records on one page*/
	private final int NUMBER_RECORDS_PER_PAGE = 10;
	
	/**
	 * This method checks all necessary input data, get all data for the
	 * "Router utilization and capacity" report by defined period of time and 
	 * transform it to ArrayList for sending on jsp page.
	 * 
	 * @return String url of page for redirecting. It is the address
	 * of page with "Router utilization and capacity" report.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		// if parameter last is null it means that user called this command not
		// from report page
		if (request.getParameter("last") == null) {
			return "reports/utilizationReport.jsp";
		}

		int last;
		try {
			last = Integer.parseInt(request.getParameter("last"));
		} catch (NumberFormatException ex) {
			return "reports/utilizationReport.jsp";
		}
		RouterUtilizationReport report = null;

		ArrayList<RouterUtilRow> records = new ArrayList<>();
		ArrayList<RouterUtilRow> checkRecords = new ArrayList<>();
		try {
			report = new RouterUtilizationReport();
			records = report.getReportData(last, NUMBER_RECORDS_PER_PAGE);
			checkRecords = report.getReportData(last + NUMBER_RECORDS_PER_PAGE,
					1);
			last = last + NUMBER_RECORDS_PER_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/utilizationReport.jsp";
		}

		if (checkRecords.isEmpty()) {
			request.setAttribute("hasNext", "notExist");
		} else {
			request.setAttribute("hasNext", "exist");
		}
		request.setAttribute("last", last);
		request.setAttribute("records", records);
		request.setAttribute("count", NUMBER_RECORDS_PER_PAGE);

		return "reports/utilizationReport.jsp";
	}

}
