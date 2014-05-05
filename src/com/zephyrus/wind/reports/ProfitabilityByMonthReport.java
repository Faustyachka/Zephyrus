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
import com.zephyrus.wind.reports.rowObjects.ProfitabilityByMonthRow;

/**
 * This class provides functionality for create and convert "Disconnect SO per period"
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class ProfitabilityByMonthReport implements IReport {
	
	private Date month;
	
    /**
     * 
     * @param startDate - start of period
     * @param endDate - end of period
     */
	public ProfitabilityByMonthReport(Date month) throws Exception {
		this.month = month;
	}
	
	public ArrayList<ProfitabilityByMonthRow> getReportData() {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			return dao.getProfitByMonthReport(month);
		} catch (Exception exc) {
			throw new RuntimeException("Report generation failed", exc);
		} finally {
			factory.endConnection();
		}
	}
	
	@Override
	public Workbook convertToExel(int maxRowsNumber) throws IOException {
		
		ArrayList<ProfitabilityByMonthRow> report = getReportData();
		
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
			cell.setCellValue(row.getProfit());
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return workbook;
	}
}
