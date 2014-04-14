package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ProfitabilityByMonth {
	static String path = "E:\\reports\\";
	private String providerLocation;
	private Long profit;
	
	public String getProviderLocation() {
		return providerLocation;
	}
	public void setProviderLocation(String providerLocation) {
		this.providerLocation = providerLocation;
	}
	public Long getProfit() {
		return profit;
	}
	public void setProfit(Long profit) {
		this.profit = profit;
	}
	
	public static ArrayList<ProfitabilityByMonth> getProfitabilityByMonthReport (){
		ArrayList<ProfitabilityByMonth> list = new ArrayList<ProfitabilityByMonth>();
//		TODO add work with DAO
		return list;
	}
	public static Workbook convertToExel (ArrayList<ProfitabilityByMonth> list) throws IOException
	{
		Workbook workbook = null;
		Row row = null;
		Cell cell=null;
			//Read template file
			FileInputStream template = null;
			try {
			template = new FileInputStream(new
					File(path+"_ProfitabilityByMonth.xls"));
			} catch (FileNotFoundException e) 
			{

			}
			workbook = new HSSFWorkbook(template);
			Sheet sheet = workbook.getSheetAt(0);
			//Write data to workbook
			Iterator<ProfitabilityByMonth> iterator = list.iterator();
			int rowIndex = 1;
			while(iterator.hasNext()){
				row = sheet.createRow(rowIndex++);
				cell = row.createCell(0);
				cell.setCellValue(iterator.next().getProviderLocation());
				cell = row.createCell(1);
				cell.setCellValue(iterator.next().getProfit());
			}
			CellStyle bold = workbook.createCellStyle();
			HSSFFont font  =(HSSFFont) workbook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			bold.setFont(font);
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("Summary:");
			cell.setCellStyle(bold);
			cell = row.createCell(1);
			cell.setCellValue("=SUMM(B2:B"+rowIndex);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			return workbook;
	}
}
