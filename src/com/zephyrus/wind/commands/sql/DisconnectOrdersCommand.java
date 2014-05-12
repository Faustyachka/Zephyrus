package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.DisconnectOrdersPerPeriodReport;
import com.zephyrus.wind.reports.rows.DisconnectOrdersPerPeriodRow;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for displaying of
 * "Disconnect orders per period" report data using paging.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class DisconnectOrdersCommand extends SQLCommand {

	private Date fromDate;
	private Date toDate;
	/** Number of records displayed on one page */
	private final int NUMBER_RECORDS_PER_PAGE = 10;

	/**
	 * This method checks all necessary input data, get all data for the
	 * "Disconnect orders per period" report and sends it to jsp page for report
	 * view forming.
	 * 
	 * @return the page of "Disconnect orders per period" report
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		// The last displayed row number
		int last;

		// if parameter last is null it means that user called this command not
		// from report page
		if (request.getParameter("last") == null) {
			return "reports/disconnectOrdersReport.jsp";
		}

		try {
			last = Integer.parseInt(request.getParameter("last"));
		} catch (NumberFormatException ex) {
			return "reports/disconnectOrdersReport.jsp";
		}

		// Get start and end dates of fetching period
		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");

		// check the input dates format
		if (fromDateString != null && toDateString != null) {
			if (isDateValid(fromDateString) && isDateValid(toDateString)) {
				fromDate = Date.valueOf(fromDateString);
				toDate = Date.valueOf(toDateString);
			} else {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/disconnectOrdersReport.jsp";
			}
		}

		// get current sql date
		java.util.Date utilDate = new java.util.Date();
		Date today = new java.sql.Date(utilDate.getTime());

		// check is date in future
		if (today.compareTo(fromDate) < 0 || today.compareTo(toDate) < 0) {
			request.setAttribute("message",
					"Wrong format of date! Date must be in past or present.");
			return "reports/disconnectOrdersReport.jsp";
		}

		DisconnectOrdersPerPeriodReport report;

		// create list for results
		ArrayList<DisconnectOrdersPerPeriodRow> records;

		// create list for existence of next page checking
		ArrayList<DisconnectOrdersPerPeriodRow> checkRecords;
		try {
			report = new DisconnectOrdersPerPeriodReport(fromDate, toDate);
			records = report.getReportData(last, NUMBER_RECORDS_PER_PAGE);

			// get the first element of next page
			checkRecords = report.getReportData(last + NUMBER_RECORDS_PER_PAGE, 1);

			// set last element number
			last = last + records.size();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to form report");
			return "reports/disconnectOrdersReport.jsp";
		}

		// set attribute which shows the existence of next page
		if (checkRecords.isEmpty()) {
			request.setAttribute("hasNext", "notExist");
		} else {
			request.setAttribute("hasNext", "exist");
		}
		request.setAttribute("records", records);
		request.setAttribute("fromDate", fromDate.toString());
		request.setAttribute("toDate", toDate.toString());
		request.setAttribute("last", last);
		request.setAttribute("count", NUMBER_RECORDS_PER_PAGE);
		return "reports/disconnectOrdersReport.jsp";
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
