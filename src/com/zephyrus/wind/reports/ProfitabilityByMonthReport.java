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
import com.zephyrus.wind.reports.rows.ProfitabilityByMonthRow;

/**
 * This class provides functionality of generating "Profitability by month" report.
 * It allows to convert report to xls format as well as fetch report data using paging.
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class ProfitabilityByMonthReport implements IReport {
	
	/** Date representing start of month to generate report for */
	private Date month;
	
    /**
     * Initializes Report with given month report will be provided for
     * @param month Date representing start of month to generate report for
     */
	public ProfitabilityByMonthReport(Date month) throws Exception {
		this.month = month;
	}
	
	/**
	 * Method returns list of records that form current report
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
	 * @return list of records associated with report
	 * @throws RuntimeException if report generation failed
	 */
	@Override
	public ArrayList<ProfitabilityByMonthRow> getReportData(int offset, int count) {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			return dao.getProfitByMonthReport(month, offset, count);
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
		
		ArrayList<ProfitabilityByMonthRow> report = getReportData(1, maxRowsNumber);
		
		// Read template file
		InputStream templateInput = getClass().getClassLoader().
				getResourceAsStream("xls/_ProfitabilityByMonth.xls");
		Workbook workbook = new HSSFWorkbook(templateInput);
		Sheet sheet = workbook.getSheetAt(0);
		
		// Write data to workbook
		int rowIndex = 1; 
		for(ProfitabilityByMonthRow row : report) {
			Row sheetRow = sheet.createRow(rowIndex++);
			Cell cell = sheetRow.createCell(0);
			cell.setCellValue(row.getProviderLocation());
			cell = sheetRow.createCell(1);
			cell.setCellValue(row.getProfit() + "$");
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return workbook;
	}
}
