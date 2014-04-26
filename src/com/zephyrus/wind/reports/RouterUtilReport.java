package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.rowObjects.RouterUtilRow;

// REVIEW: documentation expected
public class RouterUtilReport implements IReport{
	
	static String path = "E:\\reports\\"; // REVIEW: hardcode path
	private ArrayList<RouterUtilRow> report = new ArrayList<RouterUtilRow>();
	
	public ArrayList<RouterUtilRow> getReport() {
		return report;
	}
	
	public void setReport(ArrayList<RouterUtilRow> report) {
		this.report = report;
	}

	public RouterUtilReport() throws Exception{
		OracleDAOFactory factory = new OracleDAOFactory();
		try {
			factory.beginConnection();
			IReportDAO dao = factory.getReportDAO();
			report = dao.getRouterUtilReport();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			factory.endConnection();
		}
	}
	
// REVIEW: documentation expected & refactoring should be performed -
		// Interface Report and ExcelReportGenerator class
	
	public Workbook convertToExel()
			throws IOException {
		Workbook workbook = null;
		Row row = null;
		Cell cell = null;
		// Read template file
		FileInputStream template = null;
		try {
			template = new FileInputStream(new File(path + "_RouterUtil.xls"));
		} catch (FileNotFoundException e) {

		}
		workbook = new HSSFWorkbook(template);
		Sheet sheet = workbook.getSheetAt(0);
		// Write data to workbook
		Iterator<RouterUtilRow> iterator = report.iterator();
		int rowIndex = 1;
		while (iterator.hasNext()) {
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellValue(iterator.next().getRouterSN());
			cell = row.createCell(1);
			cell.setCellValue(iterator.next().getRouterUtil());
			cell = row.createCell(2);
			cell.setCellValue(iterator.next().getCapacity());
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		return workbook;
	}



}
