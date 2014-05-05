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

/**
 * This class provides functionality for create and convert "Disconnect SO per period"
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class DisconnectOrdersPerPeriodReport implements IReport {
	
	private Date startDate;
	private Date endDate;

    /**
     * 
     * @param startDate - start of period
     * @param endDate - end of period
     */
	public DisconnectOrdersPerPeriodReport(Date startDate, Date endDate) throws Exception {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public ArrayList<DisconnectOrdersPerPeriodRow> getReportData(int offset, int count) {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			return dao.getDisconnectSOPerPeriodReport(startDate, endDate, offset, count);
		} catch (Exception exc) {
			throw new RuntimeException("Report generation failed", exc);
		} finally {
			factory.endConnection();
		}
	}
	
	@Override
	public Workbook convertToExel(int maxRowsNumber) throws IOException {
		
		ArrayList<DisconnectOrdersPerPeriodRow> report = getReportData(1, maxRowsNumber);
		
		// Read template file
		InputStream templateInput = getClass().getClassLoader().
				getResourceAsStream("xls/_DisconnectOrdersPerPeriod.xls");
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
		for(DisconnectOrdersPerPeriodRow row : report) {
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
