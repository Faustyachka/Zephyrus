package com.zephyrus.wind.dao.interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.rows.DisconnectOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rows.MostProfitableRouterRow;
import com.zephyrus.wind.reports.rows.NewOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rows.ProfitabilityByMonthRow;
import com.zephyrus.wind.reports.rows.RouterUtilRow;

/**
 * This interface declares methods that used to obtain data for reports,
 * generated in system.
 * @author Kostya Trukhan & Igor Litvinenko
 */
public interface IReportDAO extends IDAO<IReport> {
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report about given period. 
	 * Note that this report obtains single row as the most profitable router, 
	 * but list of rows is returned to consider possible requirement change.
	 * @param startDate start of period
     * @param endDate end of period
     * @return List of fetched records that comprise report data set
	 */
	ArrayList<MostProfitableRouterRow> getMostProfitableRouterReport(
			Date startDate, Date endDate) throws SQLException;
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report. Paging is used to fetch only
	 * specified range of records.
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
     * @return List of fetched records that comprise report data set
	 */
	ArrayList<RouterUtilRow> getRouterUtilReport(int offset, int count) 
			throws SQLException;
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report. 
	 * @param startOfMonth Date representing start of month to obtain report for
     * @return List of fetched records that comprise report data set
	 */
	ArrayList<ProfitabilityByMonthRow> getProfitByMonthReport(Date month)
			throws SQLException;
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report about given period. Paging is used 
	 * to fetch only specified range of records.
	 * @param startDate start of period
     * @param endDate end of period
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
     * @return List of fetched records that comprise report data set
	 */
	ArrayList<DisconnectOrdersPerPeriodRow> getDisconnectSOPerPeriodReport(
			Date startDate, Date endDate, int offset, int count) throws SQLException;
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report about given period. Paging is used 
	 * to fetch only specified range of records.
	 * @param startDate start of period
     * @param endDate end of period
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
     * @return List of fetched records that comprise report data set
	 */
	ArrayList<NewOrdersPerPeriodRow> getNewSOPerPeriodReport(
			Date startDate, Date endDate, int offset, int count) throws SQLException;


}
