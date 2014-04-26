package com.zephyrus.wind.dao.interfaces;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.zephyrus.wind.reports.IReport;

public interface IReportDAO extends IDAO<IReport> {

	LinkedHashMap<String, Long> getMostProfitableRouterReport() throws SQLException;


}
