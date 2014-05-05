package com.zephyrus.wind.helpers;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 * This class provides functionality for convert POI Workbook to CSV outfile
 * @author Kostya Trukhan
 */											
public class CSVConverter {
	
	public static byte[] convert(Workbook workbook) throws IOException {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(byteOutputStream));
		
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			int minCol = row.getFirstCellNum();
			int maxCol = row.getLastCellNum();
			for (int i = minCol; i < maxCol; i++) {
				Cell cell = row.getCell(i);
				String buf = "";
				if (i > 0) {
					buf = ",";
				}
				if (cell == null) {
					output.write(buf);
				} else {
					String v = null;

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						v = cell.getRichStringCellValue().getString();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							v = cell.getDateCellValue().toString();
						} else {
							v = String.valueOf(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						v = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						v = cell.getCellFormula();
						break;
					default:
					}
					if (v != null) {
						buf = buf + toCSV(v);
					}
					output.write(buf);
				}
			}
			output.write("\n");
		}
		output.flush();
		output.close();
		return byteOutputStream.toByteArray();
	}

	private static String toCSV(String value) {
		String v = null;
		boolean doWrap = false;
		if (value != null) {
			v = value;
			if (v.contains("\"")) {
				v = v.replace("\"", "\"\"");
				doWrap = true;
			}
			if (v.contains(",") || v.contains("\n")) {
				doWrap = true;
			}
			if (doWrap) {
				v = "\"" + v + "\""; 
			}
		}
		return v;
	}
}
