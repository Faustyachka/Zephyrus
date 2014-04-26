package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.rowObjects.MostProfitableRouterRow;
import com.zephyrus.wind.reports.rowObjects.RouterUtilRow;

public interface IReportDAO extends IDAO<IReport> {

	ArrayList<MostProfitableRouterRow> getMostProfitableRouterReport() throws SQLException;

	ArrayList<RouterUtilRow> getRouterUtilReport() throws SQLException;


}
