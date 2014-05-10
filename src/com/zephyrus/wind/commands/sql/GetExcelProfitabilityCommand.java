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
import com.zephyrus.wind.reports.ProfitabilityByMonthReport;
																			// REVIEW: documentation expected 
public class GetExcelProfitabilityCommand extends SQLCommand{
	
	private static Date date;
	
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
		} else {
			request.setAttribute("message", "Wrong format of date!");
			return "reports/profitabilityReport.jsp";
		}
		ProfitabilityByMonthReport report = new ProfitabilityByMonthReport(date);					

		downloadExcel(response, report);
		return null;
	}
	
	private void downloadExcel(HttpServletResponse response, ProfitabilityByMonthReport report) throws IOException {
		final int MAX_ROWS_IN_EXCEL = 65535;
    	Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
    	//write workbook to outputstream
        //offer the user the option of opening or downloading the resulting Excel file
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ProfitabilityByMonth.xls");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
	}

}
