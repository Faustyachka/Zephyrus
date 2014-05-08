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
import com.zephyrus.wind.reports.rows.MostProfitableRouterRow;

/**
 * This class provides functionality of generating "Most profitable router" report.
 * It allows to convert report to xls format as well as fetch report data using paging.
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class MostProfitableRouterReport implements IReport {
	
	/** Start date of fetching period */
	private Date startDate;
	
	/** End date of fetching period */
	private Date endDate;

	/**
     * Initializes Report with date range report will be provided for
     * @param startDate start of period
     * @param endDate end of period
     */
	public MostProfitableRouterReport(Date startDate, Date endDate) throws Exception {
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
	public ArrayList<MostProfitableRouterRow> getReportData(int offset, int count) {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			return dao.getMostProfitableRouterReport(startDate, endDate);
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
		
		ArrayList<MostProfitableRouterRow> report = getReportData(1, maxRowsNumber);
		
		// Read template file
		InputStream templateInput = getClass().getClassLoader().
				getResourceAsStream("xls/_MostProfitableRouter.xls");
		Workbook workbook = new HSSFWorkbook(templateInput);
		Sheet sheet = workbook.getSheetAt(0);
		
		// Write data to workbook
		int rowIndex = 1; 
		for(MostProfitableRouterRow row : report) {
			Row sheetRow = sheet.createRow(rowIndex++);
			Cell cell = sheetRow.createCell(0);
			cell.setCellValue(row.getRouterSN());
			cell = sheetRow.createCell(1);
			cell.setCellValue(row.getProfit() + "$");
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		return workbook;
	}
}
