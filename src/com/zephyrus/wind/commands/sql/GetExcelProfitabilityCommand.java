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
import com.zephyrus.wind.reports.ProfitabilityByMonthReport;

public class GetExcelProfitabilityCommand extends SQLCommand{
	
	private static Date date;
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String dateString = request.getParameter("month");	
		String dateWithDay="";
		if (dateString != null) {
			final Pattern pattern = Pattern
					.compile("^([0-9]){4}-([0-9]){2}");
			final Matcher matcherFromDate = pattern.matcher(dateString);
			if (!matcherFromDate.find()) {
				request.setAttribute("message", "Wrong format of date!");
				return "reports/profitabilityReport.jsp";
			}
			dateWithDay = dateString+"-01";
			date = Date.valueOf(dateWithDay);
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
		final int MAX_ROWS_IN_EXCEL = 65535;
    	Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
    	//write workbook to outputstream
        //offer the user the option of opening or downloading the resulting Excel file
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ProfitabilityReport.xls");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
		return null;
	}

}
