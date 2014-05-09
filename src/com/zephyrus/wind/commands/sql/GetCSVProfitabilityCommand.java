package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.helpers.CSVConverter;
import com.zephyrus.wind.reports.ProfitabilityByMonthReport;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for downloading of
 * "Profitability by month" report data in CSV format.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class GetCSVProfitabilityCommand extends SQLCommand {
	private static Date date;
	
	/**
	 * This method checks all necessary input data, get all data for the "Profitability by month"
	 * report and transform it to CSV format for downloading by user.
	 * Returns the downloading stream of "Profitability by month" report in CSV format.
	 * 
	 * @return String url of page for redirecting. Always return null because there is no necessity 
	 * to redirect user on other page after report downloading.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
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
		}
		ProfitabilityByMonthReport report = null;
		try {
			report = new ProfitabilityByMonthReport(date);								// REVIEW: date is null if dateString is null
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message",
					"Error occured during report downloading");
			return "reports/profitabilityReport.jsp";

		}
		final int MAX_ROWS_IN_EXCEL = 65535;											// REVIEW: form as separate method
		Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
		// write workbook to outputstream								
		// offer the user the option of opening or downloading the resulting
		// Excel file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=ProfitabilityReport.csv");
		ServletOutputStream out = response.getOutputStream();
		byte[] data = CSVConverter.convert(wb);
		out.write(data);
		out.flush();
		out.close();
		return null;
	}

}
