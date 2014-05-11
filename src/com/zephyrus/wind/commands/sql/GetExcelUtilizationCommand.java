package com.zephyrus.wind.commands.sql;


import java.sql.SQLException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.reports.RouterUtilizationReport;
																			// REVIEW: documentation expected
public class GetExcelUtilizationCommand extends SQLCommand{
	
	
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		RouterUtilizationReport report = new RouterUtilizationReport();		
		final int MAX_ROWS_IN_EXCEL = 65535;
    	Workbook wb = report.convertToExel(MAX_ROWS_IN_EXCEL);
    	//write workbook to outputstream
        //offer the user the option of opening or downloading the resulting Excel file
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=UtilizationReport.xls");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
		return null;
	}

}
