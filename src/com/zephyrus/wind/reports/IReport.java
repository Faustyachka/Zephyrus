package com.zephyrus.wind.reports;

import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import com.zephyrus.wind.reports.rows.IRow;

/**
 * This interface declares methods that should be implemented by report classes
 * in order to provide report data translation functionality
 * @author Igor Litvinenko
 */
public interface IReport {
	
	/**
	 * Converts current report to xls format using predefined template
	 * @param maxRowsNumber maximum number of rows that can be fetched to excel
	 * @return Workbook representing Excel document
	 * @throws IOException if failed to read template
	 */
	Workbook convertToExel(int maxRowsNumber) throws IOException;
	
	/**
	 * Method returns list of records that form current report
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
	 * @return list of records associated with report
	 */
	List<? extends IRow> getReportData(int offset, int count);
	
}
