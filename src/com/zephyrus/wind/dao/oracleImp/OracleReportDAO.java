package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.IReport;

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
			+ "COUNT(PORTS.PORT_NUMBER) AS COUNT" + "FROM DEVICES"
			+ "INNER JOIN PORTS" + "ON DEVICES.ID = PORTS.DEVICE_ID"
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
	public LinkedHashMap<String, Long> getMostProfitableRouterReport() throws SQLException{
		stmt = connection.prepareStatement(SQL_ROUTER_PROFIT);
		rs = stmt.executeQuery();
		LinkedHashMap<String, Long> report = new LinkedHashMap<String,Long>();
		while(rs.next()){
			report.put(rs.getString(1), rs.getLong(2));
		}
		return report;
		
	}
}
