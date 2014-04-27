package com.zephyrus.wind.reports;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
/**
 * This interface describe overall functionality of Reports classes 
 * @author Kostya Trukhan
 */
public interface IReport {
    /**
     * This method generate convert report to Workbook(like xls) 
     * @throws when template file damaged or lost on server
     */
	public  Workbook convertToExel() throws IOException ;
}
