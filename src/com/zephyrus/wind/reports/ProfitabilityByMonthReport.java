package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.rowObjects.ProfitabilityByMonthRow;
/**
 * This class provides functionality for create and convert "profitability by month report"
 * @author Kostya Trukhan
 */
public class ProfitabilityByMonthReport implements IReport {
	static String path = "E:\\reports\\";
	private ArrayList<ProfitabilityByMonthRow> report = new ArrayList<ProfitabilityByMonthRow>();

	public ArrayList<ProfitabilityByMonthRow> getReport() {
		return report;
	}

	public void setReport(ArrayList<ProfitabilityByMonthRow> report) {
		this.report = report;
	}
    /**
     * This constructor generate report into private parameter report 
     * @param month - method use it to get all SI that started before this parameter
     * how choose this date - must be described in assumptions 
     * @throws If get some trouble with connection to DB
     */
	public ProfitabilityByMonthReport(Date month) throws Exception {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			report = dao.getProfitByMonthReport(month);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			factory.endConnection();
		}
	}

	public Workbook convertToExel() throws IOException {
		Workbook workbook = null;
		Row row = null;
		Cell cell = null; 
		// Read template file
		FileInputStream template = null;
		try {
			template = new FileInputStream(new File(path
					+ "_ProfitabilityByMonth.xls"));
		} catch (FileNotFoundException e) {

		}
		workbook = new HSSFWorkbook(template);
		Sheet sheet = workbook.getSheetAt(0);
		// Write data to workbook
		Iterator<ProfitabilityByMonthRow> iterator = report.iterator();
		ProfitabilityByMonthRow item = null;
		int rowIndex = 1;
		while (iterator.hasNext()) {
			item = iterator.next();
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellValue(item.getProviderLocation());
			cell = row.createCell(1);
			cell.setCellValue(item.getProfit());
		}
		CellStyle bold = workbook.createCellStyle();
		HSSFFont font = (HSSFFont) workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bold.setFont(font);
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("Summary:");
		cell.setCellStyle(bold);
		cell = row.createCell(1);
		cell.setCellValue("=SUMM(B2:B" + rowIndex);
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return workbook;
	}

}
