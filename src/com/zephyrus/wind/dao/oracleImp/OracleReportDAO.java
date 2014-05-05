package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.rowObjects.DisconnectOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rowObjects.MostProfitableRouterRow;
import com.zephyrus.wind.reports.rowObjects.NewOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rowObjects.ProfitabilityByMonthRow;
import com.zephyrus.wind.reports.rowObjects.RouterUtilRow;

/**
 * This class gives functionality to generate report from DAO
 * @author Kostya Trukhan
 */
public class OracleReportDAO extends OracleDAO<IReport> implements IReportDAO {
	private static final String SQL_ROUTER_PROFIT = "SELECT DEVICES.SERIAL_NUM, "
			+ "SUM(PRODUCT_CATALOG.PRICE) AS SUM "
			+ "FROM DEVICES INNER JOIN PORTS "
			+ "ON DEVICES.ID = PORTS.DEVICE_ID "
			+ "INNER JOIN CIRCUITS "
			+ "ON PORTS.ID = CIRCUITS.PORT_ID "
			+ "INNER JOIN SERVICE_INSTANCES "
			+ "ON CIRCUITS.ID = SERVICE_INSTANCES.CIRCUIT_ID "
			+ "INNER JOIN PRODUCT_CATALOG "
			+ "ON PRODUCT_CATALOG.ID = SERVICE_INSTANCES.PRODUCT_CATALOG_ID "
			+ "GROUP BY DEVICES.SERIAL_NUM";

	private static final String SQL_ROUTER_UTIL = "SELECT DEVICES.SERIAL_NUM, "
			+ "COUNT(PORTS.PORT_NUMBER) AS COUNT FROM DEVICES "
			+ "INNER JOIN PORTS ON DEVICES.ID = PORTS.DEVICE_ID "
			+ "INNER JOIN CABLES ON CABLES.PORT_ID=PORTS.ID "
			+ "GROUP BY DEVICES.SERIAL_NUM";

	private static final String SQL_MONTH_PROFIT = "SELECT PROVIDER_LOCATIONS.LOCATION_NAME, "
			+ "SUM (PRODUCT_CATALOG.PRICE) AS SUM "
			+ "FROM PRODUCT_CATALOG INNER JOIN SERVICE_INSTANCES "
			+ "ON PRODUCT_CATALOG.ID=SERVICE_INSTANCES.PRODUCT_CATALOG_ID "
			+ "INNER JOIN PROVIDER_LOCATIONS "
			+ "ON PROVIDER_LOCATIONS.ID=PRODUCT_CATALOG.PROVIDER_LOC_ID "
			+ "WHERE SERVICE_INSTANCES.START_DATE < ? "
			+ "GROUP BY PROVIDER_LOCATIONS.LOCATION_NAME";
	
	private static final String SQL_DISCONNECT_ORDERS =   
			  "SELECT * FROM (  "
			+ "  SELECT a.*, ROWNUM rnum FROM (  "
			+ "      SELECT u.first_name, u.last_name, so.id, os.order_status_value,  "
			+ "          st.service_type_value, pl.location_name "
			+ "      FROM service_orders so  "
			+ "      INNER JOIN order_type ot ON so.order_type_id = ot.id "
			+ "      INNER JOIN service_instances si ON so.service_instance_id = si.id "
			+ "      INNER JOIN users u ON si.user_id = u.id "
			+ "      INNER JOIN order_status os ON so.order_status_id = os.id "
			+ "      INNER JOIN product_catalog pc ON so.product_catalog_id = pc.id "
			+ "      INNER JOIN service_type st ON pc.service_type_id = st.id "
			+ "      INNER JOIN provider_locations pl ON pc.provider_loc_id = pl.id  "
			+ "      WHERE so.order_date BETWEEN ? AND ? "
			+ "          AND ot.order_type_value = 'DISCONNECT'  "
			+ "      ORDER BY so.order_date "
			+ "  ) a where ROWNUM <= ? "
			+ ") WHERE rnum  >= ?";
	
	private static final String SQL_NEW_ORDERS =   
			  "SELECT * FROM (  "
			+ "  SELECT a.*, ROWNUM rnum FROM (  "
			+ "      SELECT u.first_name, u.last_name, so.id, os.order_status_value,  "
			+ "          st.service_type_value, pl.location_name "
			+ "      FROM service_orders so  "
			+ "      INNER JOIN order_type ot ON so.order_type_id = ot.id "
			+ "      INNER JOIN service_instances si ON so.service_instance_id = si.id "
			+ "      INNER JOIN users u ON si.user_id = u.id "
			+ "      INNER JOIN order_status os ON so.order_status_id = os.id "
			+ "      INNER JOIN product_catalog pc ON so.product_catalog_id = pc.id "
			+ "      INNER JOIN service_type st ON pc.service_type_id = st.id "
			+ "      INNER JOIN provider_locations pl ON pc.provider_loc_id = pl.id  "
			+ "      WHERE so.order_date BETWEEN ? AND ? "
			+ "          AND ot.order_type_value = 'NEW'  "
			+ "      ORDER BY so.order_date "
			+ "  ) a where ROWNUM <= ? "
			+ ") WHERE rnum  >= ?";

	public OracleReportDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
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
	public ArrayList<MostProfitableRouterRow> getMostProfitableRouterReport()
			throws SQLException {
		stmt = connection.prepareStatement(SQL_ROUTER_PROFIT);
		rs = stmt.executeQuery();
		ArrayList<MostProfitableRouterRow> report = new ArrayList<MostProfitableRouterRow>();
		MostProfitableRouterRow item = new MostProfitableRouterRow();
		while (rs.next()) {
			item.setRouterSN(rs.getString(1));
			item.setProfit(rs.getLong(2));
			report.add(item);
		}
		return report;
	}

	@Override
	public ArrayList<RouterUtilRow> getRouterUtilReport() throws SQLException {
		stmt = connection.prepareStatement(SQL_ROUTER_UTIL);
		rs = stmt.executeQuery();
		ArrayList<RouterUtilRow> report = new ArrayList<RouterUtilRow>();
		RouterUtilRow item = new RouterUtilRow();
		while (rs.next()) {
			item.setRouterSN(rs.getString(1));
			item.setRouterUtil(rs.getLong(2));
			item.setCapacity(0.6);
			report.add(item);
		}
		return report;
	}

	@Override
	public ArrayList<ProfitabilityByMonthRow> getProfitByMonthReport(Date month)
			throws SQLException {
		stmt = connection.prepareStatement(SQL_MONTH_PROFIT);
		stmt.setDate(1, month);
		rs = stmt.executeQuery();
		ArrayList<ProfitabilityByMonthRow> report = new ArrayList<ProfitabilityByMonthRow>();
		ProfitabilityByMonthRow item = new ProfitabilityByMonthRow();
		while (rs.next()) {
			item.setProviderLocation(rs.getString(1));
			item.setProfit(rs.getLong(2));
			report.add(item);
		}
		return report;
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param offset
	 * @param count
	 * @return
	 * @throws Exception
	 */
	@Override
	public ArrayList<DisconnectOrdersPerPeriodRow> getDisconnectSOPerPeriodReport(
			Date startDate, Date endDate, int offset, int count) throws SQLException {
		
		int lastRow = offset + count - 1;
		stmt = connection.prepareStatement(SQL_DISCONNECT_ORDERS);
		stmt.setDate(1, startDate);
		stmt.setDate(2, endDate);
		stmt.setInt(3, lastRow);
		stmt.setInt(4, offset);
		rs = stmt.executeQuery();
		
		ArrayList<DisconnectOrdersPerPeriodRow> list = new ArrayList<>();
		while(rs.next()) {
			DisconnectOrdersPerPeriodRow row = new DisconnectOrdersPerPeriodRow();
			row.setOrderID(rs.getInt("id"));
			row.setOrderStatus(rs.getString("order_status_value"));
			row.setProductName(rs.getString("service_type_value"));
			row.setProviderLocation(rs.getString("location_name"));
			row.setUsername(rs.getString("first_name") + " " + rs.getString("last_name"));
			list.add(row);
		}
		rs.close();
		return list;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param offset
	 * @param count
	 * @return
	 * @throws Exception
	 */
	@Override
	public ArrayList<NewOrdersPerPeriodRow> getNewSOPerPeriodReport(
			Date startDate, Date endDate, int offset, int count) throws SQLException {
		
		int lastRow = offset + count - 1;
		stmt = connection.prepareStatement(SQL_NEW_ORDERS);
		stmt.setDate(1, startDate);
		stmt.setDate(2, endDate);
		stmt.setInt(3, lastRow);
		stmt.setInt(4, offset);
		rs = stmt.executeQuery();
		
		ArrayList<NewOrdersPerPeriodRow> list = new ArrayList<>();
		while(rs.next()) {
			NewOrdersPerPeriodRow row = new NewOrdersPerPeriodRow();
			row.setOrderID(rs.getInt("id"));
			row.setOrderStatus(rs.getString("order_status_value"));
			row.setProductName(rs.getString("service_type_value"));
			row.setProviderLocation(rs.getString("location_name"));
			row.setUsername(rs.getString("first_name") + " " + rs.getString("last_name"));
			list.add(row);
		}
		rs.close();
		return list;
	}
}
