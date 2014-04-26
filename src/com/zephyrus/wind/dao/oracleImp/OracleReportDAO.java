package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.rowObjects.*;

public class OracleReportDAO extends OracleDAO<IReport> implements IReportDAO {
	private static final String SQL_ROUTER_PROFIT = "SELECT DEVICES.SERIAL_NUM,"
			+ "SUM(PRODUCT_CATALOG.PRICE) AS SUM"
			+ "FROM DEVICES INNER JOIN PORTS"
			+ "ON DEVICES.ID = PORTS.DEVICE_ID"
			+ "INNER JOIN CIRCUITS"
			+ "ON PORTS.ID = CIRCUITS.PORT_ID"
			+ "INNER JOIN SERVICE_INSTANCES"
			+ "ON CIRCUITS.ID = SERVICE_INSTANCES.CIRCUIT_ID"
			+ "INNER JOIN PRODUCT_CATALOG"
			+ "ON PRODUCT_CATALOG.ID = SERVICE_INSTANCES.PRODUCT_CATALOG_ID"
			+ "GROUP BY DEVICES.SERIAL_NUM";
	private static final String SQL_ROUTER_UTIL = "SELECT DEVICES.SERIAL_NUM,"
			+ "COUNT(PORTS.PORT_NUMBER) AS COUNT FROM DEVICES"
			+ "INNER JOIN PORTT ON DEVICES.ID = PORTS.DEVICE_ID"
			+ "INNER JOIN CABLES ON CABLES.PORT_ID=PORTS.ID"
			+ "GROUP BY DEVICES.SERIAL_NUM ;";
	public OracleReportDAO(Connection connection,
			OracleDAOFactory daoFactory) throws Exception {
		super(IReport.class, connection, daoFactory);

	}

	@Override
	protected void fillItem(IReport item, ResultSet rs) throws SQLException,
			Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getSelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(IReport record) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IReport insert(IReport record) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remove(IReport record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<MostProfitableRouterRow> getMostProfitableRouterReport() throws SQLException{
		stmt = connection.prepareStatement(SQL_ROUTER_PROFIT);
		rs = stmt.executeQuery();
		ArrayList<MostProfitableRouterRow> report = new ArrayList<MostProfitableRouterRow>();
		MostProfitableRouterRow item = new MostProfitableRouterRow();
		while(rs.next()){
			item.setRouterSN(rs.getString(1));
			item.setProfit(rs.getLong(2));
			report.add(item);
		}
		return report;
	}
	@Override
	public ArrayList<RouterUtilRow> getRouterUtilReport() throws SQLException{
		stmt = connection.prepareStatement(SQL_ROUTER_UTIL);
		rs = stmt.executeQuery();
		ArrayList<RouterUtilRow> report = new ArrayList<RouterUtilRow>();
		RouterUtilRow item = new RouterUtilRow();
		while(rs.next()){
			item.setRouterSN(rs.getString(1));
			item.setRouterUtil(60/rs.getLong(2));
			item.setCapacity(0.6);
			report.add(item);
		}
		return report;
	}
}
