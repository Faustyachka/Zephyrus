package com.zephyrus.wind.reports;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;

import com.zephyrus.wind.reports.rowObjects.NewOrdersPerPeriodRow;

public interface IReport {
	
	Workbook convertToExel(int maxRowsNumber) throws IOException;
	
}
