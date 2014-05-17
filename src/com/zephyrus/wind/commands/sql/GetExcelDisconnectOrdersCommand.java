package com.zephyrus.wind.commands.sql;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.DisconnectOrdersPerPeriodReport;

/**
 * This class contains the method, that is declared in
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for downloading of
 * "Disconnect orders per period" report data in Excel format.
 * @author Alexandra Beskorovaynaya
 */
public class GetExcelDisconnectOrdersCommand extends SQLCommand {
	
	/**
	 * This method checks all necessary input data, get all data for the
	 * "Disconnect orders per period" report and transform it to Excel format for
	 * downloading by user. Returns the downloading stream of
	 * "Disconnect orders per period" report in Excel format.
	 * @return String url of page for redirecting. Always return null because
	 *         there is no necessity to redirect user on other page after report
	 *         downloading.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		if (request.getParameter("from") == null
				|| request.getParameter("to") == null) {
			request.setAttribute("message", "Date fields can not be empty!");
			return "reports/disconnectOrdersReport.jsp";
		}

		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");

		Date fromDate;
		Date toDate;

		// check the dates on format corresponding
		if (isDateValid(fromDateString) && isDateValid(toDateString)) {
			// transform dates strings into Date format
			fromDate = Date.valueOf(fromDateString);
			toDate = Date.valueOf(toDateString);
		} else {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/disconnectOrdersReport.jsp";
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
		
		if (toDate.compareTo(fromDate) < 0) {
			request.setAttribute("message",
					"Date interval is incorrect");
			return "reports/disconnectOrdersReport.jsp";
		}

		DisconnectOrdersPerPeriodReport report = new DisconnectOrdersPerPeriodReport(
				fromDate, toDate);
		downloadExcel(response, report);
		return null;
	}

	private void downloadExcel(HttpServletResponse response,
			DisconnectOrdersPerPeriodReport report) throws IOException {
		final int MAX_ROWS_IN_EXCEL = 65535;
		Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
		// write workbook to outputstream
		// offer the user the option of opening or downloading the resulting
		// Excel file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=DisconnectOrders.xls");
		ServletOutputStream out = response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
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
