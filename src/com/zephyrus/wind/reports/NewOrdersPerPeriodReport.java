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
import com.zephyrus.wind.reports.rows.NewOrdersPerPeriodRow;

/**
 * This class provides functionality of generating "New orders per period" report.
 * It allows to convert report to xls format as well as fetch report data using paging.
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class NewOrdersPerPeriodReport implements IReport {
	
	/** Start date of fetching period */
	private Date startDate;
	
	/** End date of fetching period */
	private Date endDate;

	/**
     * Initializes Report with date range report will be provided for
     * @param startDate start of period
     * @param endDate end of period
     */
	public NewOrdersPerPeriodReport(Date startDate, Date endDate) throws Exception {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * Method returns list of records that form current report
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
	 * @return list of records associated with report
	 * @throws RuntimeException if report generation failed
	 */
	@Override
	public ArrayList<NewOrdersPerPeriodRow> getReportData(int offset, int count) {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			return dao.getNewSOPerPeriodReport(startDate, endDate, offset, count);
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
		
		ArrayList<NewOrdersPerPeriodRow> report = getReportData(1, maxRowsNumber);
		
		// Read template file
		InputStream templateInput = getClass().getClassLoader().
				getResourceAsStream("xls/_NewOrdersPerPeriod.xls");
		Workbook workbook = new HSSFWorkbook(templateInput);
		Sheet sheet = workbook.getSheetAt(0);
		
		// Write data to workbook
		Row sheetRow = sheet.getRow(0);
		Cell cell = sheetRow.createCell(1);
		cell.setCellValue(startDate.toString());
		cell = sheetRow.createCell(2);
		cell.setCellValue(endDate.toString());
		
		/* starting from the second row because of the template layout */
		int rowIndex = 2; 
		for(NewOrdersPerPeriodRow row : report) {
			sheetRow = sheet.createRow(rowIndex++);
			cell = sheetRow.createCell(0);
			cell.setCellValue(row.getUsername());
			cell = sheetRow.createCell(1);
			cell.setCellValue(row.getOrderID());
			cell = sheetRow.createCell(2);
			cell.setCellValue(row.getOrderStatus());
			cell = sheetRow.createCell(3);
			cell.setCellValue(row.getProductName());
			cell = sheetRow.createCell(4);
			cell.setCellValue(row.getProviderLocation());
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		return workbook;
	}
}
