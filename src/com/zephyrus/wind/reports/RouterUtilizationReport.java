package com.zephyrus.wind.reports;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.rowObjects.DisconnectOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rowObjects.RouterUtilRow;

/**
 * 
 */
public class RouterUtilizationReport implements IReport {

    /**
     * 
     */
	public RouterUtilizationReport() throws Exception {
	}
	
	public ArrayList<RouterUtilRow> getReportData(int offset, int count) {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			return dao.getRouterUtilReport(offset, count);
		} catch (Exception exc) {
			throw new RuntimeException("Report generation failed", exc);
		} finally {
			factory.endConnection();
		}
	}
	
	@Override
	public Workbook convertToExel(int maxRowsNumber) throws IOException {
		
		ArrayList<RouterUtilRow> report = getReportData(1, maxRowsNumber);
		
		// Read template file
		InputStream templateInput = getClass().getClassLoader().
				getResourceAsStream("xls/_RouterUtil.xls");
		Workbook workbook = new HSSFWorkbook(templateInput);
		Sheet sheet = workbook.getSheetAt(0);
		
		// Write data to workbook
		int rowIndex = 1; 
		for(RouterUtilRow row : report) {
			Row sheetRow = sheet.createRow(rowIndex++);
			Cell cell = sheetRow.createCell(0);
			cell.setCellValue(row.getRouterSN());
			cell = sheetRow.createCell(1);
			cell.setCellValue(row.getRouterUtil() + "%");
			cell = sheetRow.createCell(2);
			cell.setCellValue(row.getCapacity());
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		return workbook;
	}
}
