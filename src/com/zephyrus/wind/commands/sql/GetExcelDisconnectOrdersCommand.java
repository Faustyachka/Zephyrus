package com.zephyrus.wind.commands.sql;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.DisconnectOrdersPerPeriodReport;
																					// REVIEW: documentation expected 
public class GetExcelDisconnectOrdersCommand extends SQLCommand{

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
		
		final Pattern pattern = Pattern
				.compile("^([0-9]){4}-([0-9]){2}-([0-9]){2}$");
		final Matcher matcherFromDate = pattern.matcher(fromDateString);
		final Matcher matcherToDate = pattern.matcher(toDateString);		
		if (!matcherFromDate.matches() || !matcherToDate.matches()) {		
			request.setAttribute("message", "Wrong format of date!");
			return "reports/disconnectOrdersReport.jsp";
		}
		
		Date fromDate = Date.valueOf(fromDateString);
		Date toDate = Date.valueOf(toDateString);
		
		DisconnectOrdersPerPeriodReport report = new DisconnectOrdersPerPeriodReport(fromDate, toDate);																		
		downloadExcel(response, report);
		return null;
	}
	
	private void downloadExcel(HttpServletResponse response, DisconnectOrdersPerPeriodReport report) throws IOException {
		final int MAX_ROWS_IN_EXCEL = 65535;
    	Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
    	//write workbook to outputstream
        //offer the user the option of opening or downloading the resulting Excel file
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=DisconnectOrders.xls");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
	}

}
