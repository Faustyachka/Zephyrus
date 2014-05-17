package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.MostProfitableRouterReport;
import com.zephyrus.wind.reports.rows.MostProfitableRouterRow;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for displaying of
 * "Most profitable router" report data on jsp page.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class MostProfitableRouterReportCommand extends SQLCommand {

	private static Date fromDate;
	private static Date toDate;
	private final int NUMBER_RECORDS_PER_PAGE = 1;
	
	/**
	 * This method checks all necessary input data, get all data for the
	 * "Most profitable router" report by defined period of time and 
	 * transform it to ArrayList for sending on jsp page.
	 * 
	 * @return String url of page for redirecting. It is the address
	 * of page with "Most profitable router" report.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");
		if (fromDateString != null && toDateString != null) {
			if (isDateValid(fromDateString) && isDateValid(toDateString)) {
				fromDate = Date.valueOf(fromDateString);
				toDate = Date.valueOf(toDateString);
			} else {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/mostProfitableRouterReport.jsp";
			}
		}

		// get current sql date
		java.util.Date utilDate = new java.util.Date();
		Date today = new java.sql.Date(utilDate.getTime());

		// check is date in future
		if (today.compareTo(fromDate) < 0 || today.compareTo(toDate) < 0) {
			request.setAttribute("message",
					"Wrong format of date! Date must be in past or present.");
			return "reports/mostProfitableRouterReport.jsp";
		}
		
		if (toDate.compareTo(fromDate) < 0) {
			request.setAttribute("message",
					"Date interval is incorrect");
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
