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
import com.zephyrus.wind.reports.MostProfitableRouterReport;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for downloading of
 * "Most profitable router" report data in CSV format.
 * 
 * @author Alexandra Beskorovaynaya
 */
public class GetCSVMostProfitableRouterCommand extends SQLCommand {
	
	/**
	 * This method checks all necessary input data, get all data for the "Most profitable router"
	 * report and transform it to CSV format for downloading by user.
	 * Returns the downloading stream of "Most profitable router" report in CSV format.
	 * 
	 * @return String url of page for redirecting. Always return null because there is no necessity 
	 * to redirect user on other page after report downloading.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		MostProfitableRouterReport report = null;										// REVIEW: implementation is too far from usage
		
		//check the presence of dates
		if (request.getParameter("from") == null
				|| request.getParameter("to") == null) {
			request.setAttribute("message", "Date fields can not be empty!");
			return "reports/mostProfitableRouterReport.jsp";
		}
		
		//get the start and end dates of fetching period in String format
		String fromDateString = request.getParameter("from");
		String toDateString = request.getParameter("to");
		
		//check the dates on format corresponding 
		final Pattern pattern = Pattern
				.compile("^([0-9]){4}-([0-9]){2}-([0-9]){2}$");
		final Matcher matcherFromDate = pattern.matcher(fromDateString);
		final Matcher matcherToDate = pattern.matcher(fromDateString);					// REVIEW: error: you mixed up "toDateString" with "fromDateString"
		if (!matcherFromDate.find() || !matcherToDate.find()) {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/mostProfitableRouterReport.jsp";
		}
		
		//transform dates strings into Date format
		Date fromDate = Date.valueOf(fromDateString);
		Date toDate = Date.valueOf(toDateString);
		
		try {
			report = new MostProfitableRouterReport(fromDate, toDate);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message",
					"Error occured during report downloading");
			return "reports/mostProfitableRouterReport.jsp";

		}
		final int MAX_ROWS_IN_EXCEL = 65535;
		Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
		// write workbook to outputstream
		// offer the user the option of opening or downloading the resulting
		// Excel file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=MostProfitableRouter.csv");
		ServletOutputStream out = response.getOutputStream();
		byte[] data = CSVConverter.convert(wb);
		out.write(data);
		out.flush();
		out.close();
		return null;
	}

}
