package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;

public class RouterUtil {
	static String path = "E:\\reports\\";
	private String routerSN;
	private double capacity;
	private double routerUtil;
	
	public String getRouterSN() {
		return routerSN;
	}
	public void setRouterSN(String routerSN) {
		this.routerSN = routerSN;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getRouterUtil() {
		return routerUtil;
	}
	public void setRouterUtil(double routerUtil) {
		this.routerUtil = routerUtil;
	}
	
	public static ArrayList<RouterUtil> getListReport () throws Exception{
		ArrayList<RouterUtil> list = new ArrayList<RouterUtil>();
		OracleDAOFactory factory= new OracleDAOFactory();
		try { 
	    	  factory.beginConnection();
	    	  IDeviceDAO dao = factory.getDeviceDAO();
	    	  list = dao.getRouterUtil();
	    	  
    } catch(SQLException ex){
        ex.printStackTrace();
    } finally{
  	  factory.endConnection();
    }
		return list;
		
	}
	public static String convertToExel (ArrayList<RouterUtil> list) throws IOException
	{
		Workbook workbook = null;
		Row row = null;
		Cell cell=null;
			//Read template file
			FileInputStream template = null;
			try {
			template = new FileInputStream(new
					File(path+"_RouterUtil.xls"));
			} catch (FileNotFoundException e) 
			{

			}
			workbook = new HSSFWorkbook(template);
			Sheet sheet = workbook.getSheetAt(0);
			//Write data to workbook
			Iterator<RouterUtil> iterator = list.iterator();
			int rowIndex = 1;
			while(iterator.hasNext()){
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
			SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_hh_mm_ss");
			String fileName = "RouterUtil" + sdf.format(new Date()) + ".xls";
			File exelFile = new File(path + fileName);
			FileOutputStream outFile = new FileOutputStream(exelFile);
			workbook.write(outFile);
			outFile.close();
			return path+fileName;
	}

	
}
