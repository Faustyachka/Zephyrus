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
import com.zephyrus.wind.reports.NewOrdersPerPeriodReport;

// REVIEW: documentation expected 
public class GetExcelNewOrdersCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		if (request.getParameter("from") == null
				|| request.getParameter("to") == null) {
			request.setAttribute("message", "Date fields can not be empty!");
			return "reports/newOrdersReport.jsp";
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
			return "reports/newOrdersReport.jsp";
		}
		
		//get current sql date
		java.util.Date utilDate = new java.util.Date();
		Date today = new java.sql.Date(utilDate.getTime());
		
		//check is date in future
		if (today.compareTo(fromDate) < 0 ||today.compareTo(toDate) < 0){
			request.setAttribute("message", "Wrong format of date! Date must be in past or present.");
			return "reports/newOrdersReport.jsp";
		}
		
		if (toDate.compareTo(fromDate) < 0) {
			request.setAttribute("message",
					"Date interval is incorrect");
			return "reports/newOrdersReport.jsp";
		}
		
		NewOrdersPerPeriodReport report = new NewOrdersPerPeriodReport(
				fromDate, toDate);

		downloadExcel(response, report);
		return null;
	}

	private void downloadExcel(HttpServletResponse response,
			NewOrdersPerPeriodReport report) throws IOException {
		final int MAX_ROWS_IN_EXCEL = 65535;
		Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
		// write workbook to outputstream
		// offer the user the option of opening or downloading the resulting
		// Excel file
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=NewOrders.xls");
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
