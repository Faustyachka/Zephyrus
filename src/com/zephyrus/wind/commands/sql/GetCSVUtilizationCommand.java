package com.zephyrus.wind.commands.sql;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.helpers.CSVConverter;
import com.zephyrus.wind.reports.RouterUtilizationReport;

/**
 * This class contains the method, that is declared in 
 * com.zephyrus.wind.commands.interfaces.SQLCommand. Uses for downloading of
 * "Router utilization and capacity" report data in CSV format.
 * @author Alexandra Beskorovaynaya
 */
public class GetCSVUtilizationCommand extends SQLCommand {

	/**
	 * This method checks all necessary input data, get all data for the "Router utilization and capacity"
	 * report and transform it to CSV format for downloading by user.
	 * Returns the downloading stream of "Router utilization and capacity" report in CSV format.
	 * @return String url of page for redirecting. Always return null because there is no necessity 
	 *         to redirect user on other page after report downloading.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		RouterUtilizationReport report = new RouterUtilizationReport();
		
		downloadCSV(response, report);
		return null;
	}
	
	private void downloadCSV(HttpServletResponse response, RouterUtilizationReport report) throws IOException {
		final int MAX_ROWS_IN_EXCEL = 65535;
		Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
		// write workbook to outputstream
		// offer the user the option of opening or downloading the resulting
		// Excel file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=RouterUtilization.csv");
		ServletOutputStream out = response.getOutputStream();
		byte[] data = CSVConverter.convert(wb);
		out.write(data);
		out.flush();
		out.close();
	}

}
