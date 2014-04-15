package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DisconnectOrdersPerPeriod {
	static String path = "E:\\reports\\";
	private String username;
	private String orderID;
	private String orderStatus;
	private String productName;
	private String providerLocation;
	private Date startPeriod;
	private Date endPeriod;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProviderLocation() {
		return providerLocation;
	}
	public void setProviderLocation(String providerLocation) {
		this.providerLocation = providerLocation;
	}
	public Date getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(Date startPeriod) {
		this.startPeriod = startPeriod;
	}
	public Date getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}
	
	public static ArrayList<DisconnectOrdersPerPeriod> getListReport (Date start, Date end){
		ArrayList<DisconnectOrdersPerPeriod> list = new ArrayList<DisconnectOrdersPerPeriod>();
//		TODO add work with DAO
		return list;
	}
	public static Workbook convertToExel (ArrayList<DisconnectOrdersPerPeriod> list) throws IOException
	{
		Workbook workbook = null;
		Row row = null;
		Cell cell=null;
			//Read template file
			FileInputStream template = null;
			try {
			template = new FileInputStream(new
					File(path+"_NewOrdersPerPeriod.xls"));
			} catch (FileNotFoundException e) 
			{

			}
			workbook = new HSSFWorkbook(template);
			Sheet sheet = workbook.getSheetAt(0);
			//Write data to workbook
			Iterator<DisconnectOrdersPerPeriod> iterator = list.iterator();
			row = sheet.getRow(0);
			cell = row.createCell(1);
			cell.setCellValue(iterator.next().getStartPeriod());
			cell = row.createCell(2);
			cell.setCellValue(iterator.next().getEndPeriod());

			int rowIndex = 2;
			while(iterator.hasNext()){
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
