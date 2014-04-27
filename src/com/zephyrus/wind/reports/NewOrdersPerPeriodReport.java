package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.rowObjects.NewOrdersPerPeriodRow;

/**
 * This class provides functionality for create and convert "New SO per period"
 * @author Kostya Trukhan
 */
public class NewOrdersPerPeriodReport {
	static String path = "E:\\reports\\"; // REVIEW: hardcode path

	private ArrayList<NewOrdersPerPeriodRow> report = new ArrayList<NewOrdersPerPeriodRow>();

	public ArrayList<NewOrdersPerPeriodRow> getReport() {
		return report;
	}

	public void setReport(ArrayList<NewOrdersPerPeriodRow> report) {
		this.report = report;
	}

	/**
	 * This constructor generate report into private parameter report
	 * 
	 * @param startDate- start of period
	 * @param endDate - end of period
	 * @throws If get some trouble with connection to DB
	 */
	public NewOrdersPerPeriodReport(Date startDate, Date endDate)
			throws Exception {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			setReport(dao.getNewSOPerPeriodReport(startDate, endDate));
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
					+ "_NewOrdersPerPeriod.xls")); // REVIEW: hardcode path
		} catch (FileNotFoundException e) {
			// REVIEW: no error correction
		}
		workbook = new HSSFWorkbook(template);
		Sheet sheet = workbook.getSheetAt(0);
		// Write data to workbook
		Iterator<NewOrdersPerPeriodRow> iterator = getReport().iterator();
		row = sheet.getRow(0);
		cell = row.createCell(1);
		cell.setCellValue(iterator.next().getStartPeriod());
		cell = row.createCell(2);
		cell.setCellValue(iterator.next().getEndPeriod());

		int rowIndex = 2;
		while (iterator.hasNext()) {
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellValue(iterator.next().getUsername());
			cell = row.createCell(1);
			cell.setCellValue(iterator.next().getOrderID());
			cell = row.createCell(2);
			cell.setCellValue(iterator.next().getOrderStatus());
			cell = row.createCell(3);
			cell.setCellValue(iterator.next().getProductName());
			cell = row.createCell(4);
			cell.setCellValue(iterator.next().getProviderLocation());
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		return workbook;

	}

}
