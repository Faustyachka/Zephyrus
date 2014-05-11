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
import com.zephyrus.wind.reports.ProfitabilityByMonthReport;
																			// REVIEW: documentation expected 
public class GetExcelProfitabilityCommand extends SQLCommand{
	
	private static Date date;
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		String dateString = request.getParameter("month");	
		String dateWithDay="";
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
