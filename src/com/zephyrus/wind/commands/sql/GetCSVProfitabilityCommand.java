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
import com.zephyrus.wind.helpers.CSVConverter;
import com.zephyrus.wind.reports.ProfitabilityByMonthReport;

/**
 * This class contains the method, that is declared in 
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for downloading of
 * "Profitability by month" report data in CSV format.
 * @author Alexandra Beskorovaynaya
 */
public class GetCSVProfitabilityCommand extends SQLCommand {
	private static Date date;

	/**
	 * This method checks all necessary input data, get all data for the
	 * "Profitability by month" report and transform it to CSV format for
	 * downloading by user. Returns the downloading stream of
	 * "Profitability by month" report in CSV format.
	 * @return String url of page for redirecting. Always return null because
	 *         there is no necessity to redirect user on other page after report
	 *         downloading.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
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
		if (today.compareTo(date) < 0 ) {
			request.setAttribute("message",
					"Wrong format of date! Date must be in past or present.");
			return "reports/profitabilityReport.jsp";
		}

		ProfitabilityByMonthReport report = null;
		try {
			report = new ProfitabilityByMonthReport(date);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message",
					"Error occured during report downloading");
			return "reports/profitabilityReport.jsp";

		}
		downloadCSV(response, report);
		return null;
	}

	private void downloadCSV(HttpServletResponse response,
			ProfitabilityByMonthReport report) throws IOException {
		final int MAX_ROWS_IN_EXCEL = 65535;
		Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
		// write workbook to outputstream
		// offer the user the option of opening or downloading the resulting
		// Excel file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=ProfitabilityByMonth.csv");
		ServletOutputStream out = response.getOutputStream();
		byte[] data = CSVConverter.convert(wb);
		out.write(data);
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
