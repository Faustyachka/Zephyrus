package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class MostProfitableRouter {
	static String path = "E:\\reports\\";
	private String routerSN;
	private Long profit;
	
	public String getRouterSN() {
		return routerSN;
	}
	public void setRouterSN(String routerSN) {
		this.routerSN = routerSN;
	}
	public Long getProfit() {
		return profit;
	}
	public void setProfit(Long profit) {
		this.profit = profit;
	}

	public static ArrayList<MostProfitableRouter> getListReport (){
		ArrayList<MostProfitableRouter> list = new ArrayList<MostProfitableRouter>();
//		TODO add work with DAO
		return list;	
	}
	public static String convertToExel (ArrayList<MostProfitableRouter> list) throws IOException
	{
		Workbook workbook = null;
		Row row = null;
		Cell cell=null;
			//Read template file
			FileInputStream template = null;
			try {
			template = new FileInputStream(new
					File(path+"_MostProfitableRouter.xls"));
			} catch (FileNotFoundException e) 
			{

			}
			workbook = new HSSFWorkbook(template);
			Sheet sheet = workbook.getSheetAt(0);
			//Write data to workbook
			Iterator<MostProfitableRouter> iterator = list.iterator();
			int rowIndex = 1;
			while(iterator.hasNext()){
				row = sheet.createRow(rowIndex++);
				cell = row.createCell(0);
				cell.setCellValue(iterator.next().getRouterSN());
				cell = row.createCell(1);
				cell.setCellValue(iterator.next().getProfit());
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_hh_mm_ss");
			String fileName = "MostProfitableRouter" + sdf.format(new Date()) + ".xls";
			File exelFile = new File(path + fileName);
			FileOutputStream outFile = new FileOutputStream(exelFile);
			workbook.write(outFile);
			outFile.close();
			return path+fileName;
	}
}
