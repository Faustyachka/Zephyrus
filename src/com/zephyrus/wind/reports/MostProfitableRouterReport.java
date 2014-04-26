package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;

// REVIEW: documentation expected
public class MostProfitableRouterReport implements IReport{
	static String path = "E:\\reports\\"; // REVIEW: hardcode path
	private LinkedHashMap<String, Long> report = new LinkedHashMap<String, Long>();
	
	
	public LinkedHashMap<String, Long> getReport() {
		return report;
	}
	
	public void setReport(LinkedHashMap<String, Long> report) {
		this.report = report;
	}
	
	// REVIEW: documentation of ALL public methods expected
	public MostProfitableRouterReport() throws Exception {
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO(); // REVIEW: reportDAO should
														// be invoked
			setReport(dao.getMostProfitableRouterReport());
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			factory.endConnection();
		}
	}
	
	public  Workbook convertToExel()
			throws IOException { // REVIEW: watch formatting
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
		Iterator<Entry<String, Long>> iterator = report.entrySet().iterator();
		int rowIndex = 1;
		while (iterator.hasNext()) {
			Entry<String, Long> pairs = iterator.next();
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellValue(pairs.getKey());
			cell = row.createCell(1);
			cell.setCellValue(pairs.getValue());
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return workbook;
	}


}
