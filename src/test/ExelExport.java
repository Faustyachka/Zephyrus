package test;

import java.io.File;
import java.io.FileOutputStream;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExelExport {
	public static void writeListToFile(String fileName, List<String> textList) throws Exception{
        Workbook workbook = null;
         
        if(fileName.endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        }else if(fileName.endsWith("xls")){
            workbook = new HSSFWorkbook();
        }else{
            throw new Exception("invalid file name, should be xls or xlsx");
        }
         
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
         
        //write to file
        File exelFile = new File(fileName);
        if(!exelFile.exists()) {
            exelFile.createNewFile();
        } 
        FileOutputStream fos = new FileOutputStream(exelFile);
        workbook.write(fos);
        fos.close();
    }

}
