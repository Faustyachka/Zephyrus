package com.zephyrus.wind.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExelExport {
	static String path="E:\\reports\\";
	public static Workbook writeListToFile(ArrayList<String> textList) throws Exception{
        Workbook workbook = new HSSFWorkbook();
         
         
        Sheet sheet = workbook.createSheet("FirstSheet");
        
        Iterator<String> iterator = textList.iterator();
         
        int rowIndex = 0;
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderLeft(CellStyle.BORDER_NONE);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_DASH_DOT_DOT);
        cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = (HSSFFont) workbook.createFont();
        font.setBoldweight(HSSFFont.U_DOUBLE);
        cellStyle.setFont(font);
        while(iterator.hasNext()){
            String text = iterator.next();
            Row row = sheet.createRow(rowIndex++);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(text);
            cell0.setCellStyle(cellStyle);
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
		return workbook;
    }
	public static Workbook getRouterUtilizationReport(ArrayList<String> list) throws IOException
	{
		Workbook workbook = null;
		Row row = null;
		Cell cell=null;
		try {
			//Read template file
			FileInputStream template = new FileInputStream(new
					File(path+"_RouterUtil.xls"));
			workbook = new HSSFWorkbook(template);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<String> iterator = list.iterator();
			int rowIndex = 1;
			while(iterator.hasNext()){
				row = sheet.createRow(rowIndex++);
				cell = row.createCell(0);
				cell.setCellValue(iterator.next());
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			return workbook;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return workbook;
	

	}

}
