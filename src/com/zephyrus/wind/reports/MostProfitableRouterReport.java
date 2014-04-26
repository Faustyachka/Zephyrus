package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.rowObjects.MostProfitableRouterRow;

// REVIEW: documentation expected
public class MostProfitableRouterReport implements IReport {
	static String path = "E:\\reports\\"; // REVIEW: hardcode path
	private ArrayList<MostProfitableRouterRow> report = new ArrayList<MostProfitableRouterRow>();

	public ArrayList<MostProfitableRouterRow> getReport() {
		return report;
	}
	public void setReport(ArrayList<MostProfitableRouterRow> report) {
		this.report = report;
	}
	// REVIEW: documentation of ALL public methods expected
	public MostProfitableRouterReport() throws Exception {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO(); 
			report = dao.getMostProfitableRouterReport();
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
					+ "_MostProfitableRouter.xls"));
		} catch (FileNotFoundException e) {

		}
		workbook = new HSSFWorkbook(template);
		Sheet sheet = workbook.getSheetAt(0);
		// Write data to workbook
		Iterator<MostProfitableRouterRow> iterator = report.iterator();
		int rowIndex = 1;
		while (iterator.hasNext()) {
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellValue(iterator.next().getRouterSN());
			cell = row.createCell(1);
			cell.setCellValue(iterator.next().getProfit());
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return workbook;
	}

}
