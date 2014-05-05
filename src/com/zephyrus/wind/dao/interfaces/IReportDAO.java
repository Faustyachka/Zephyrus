package com.zephyrus.wind.dao.interfaces;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.rowObjects.DisconnectOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rowObjects.MostProfitableRouterRow;
import com.zephyrus.wind.reports.rowObjects.NewOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rowObjects.ProfitabilityByMonthRow;
import com.zephyrus.wind.reports.rowObjects.RouterUtilRow;
/**
 * This interface describe all method needed to generate report from DAO
 * @author Kostya Trukhan
 */		

public interface IReportDAO extends IDAO<IReport> {

	public ArrayList<MostProfitableRouterRow> getMostProfitableRouterReport(
			Date startDate, Date endDate) throws SQLException;

	ArrayList<RouterUtilRow> getRouterUtilReport(int offset, int count) 
			throws SQLException;

	ArrayList<ProfitabilityByMonthRow> getProfitByMonthReport(Date month)
			throws SQLException;

	ArrayList<DisconnectOrdersPerPeriodRow> getDisconnectSOPerPeriodReport(
			Date startDate, Date endDate, int offset, int count) throws SQLException;

	ArrayList<NewOrdersPerPeriodRow> getNewSOPerPeriodReport(
			Date startDate, Date endDate, int offset, int count) throws SQLException;


}
