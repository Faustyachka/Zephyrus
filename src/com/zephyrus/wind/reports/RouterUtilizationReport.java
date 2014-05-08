package com.zephyrus.wind.reports;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.rows.RouterUtilRow;

/**
 * This class provides functionality of generating "Router utilization and capacity" report.
 * It allows to convert report to xls format as well as fetch report data using paging.
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class RouterUtilizationReport implements IReport {

    /**
     * No data should be provided to initialize this type of report 
     */
	public RouterUtilizationReport() throws Exception {
	}
	
	/**
	 * Method returns list of records that form current report
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
	 * @return list of records associated with report
	 * @throws RuntimeException if report generation failed
	 */
	@Override
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
	
	/**
	 * Converts current report to xls format using predefined template
	 * @param maxRowsNumber maximum number of rows that can be fetched to excel
	 * @return Workbook representing Excel document
	 * @throws IOException if failed to read template
	 */
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
