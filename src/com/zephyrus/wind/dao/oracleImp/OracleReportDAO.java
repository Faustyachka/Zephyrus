package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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

	private static final String SQL_ROUTER_UTIL = 
			"SELECT * FROM (  "
		  + "  SELECT a.*, ROWNUM rnum FROM ( "
		  + "      SELECT serial_num,  "
		  + "        (SELECT COUNT(*) "
		  + "          FROM ports "
		  + "          INNER JOIN devices d ON ports.device_id = d.id "
		  + "          WHERE d.serial_num = serial_num "
		  + "        ) capacity, "
		  + "        ROUND(COUNT(p.port_number) / (SELECT COUNT(*) "
		  + "          FROM ports "
		  + "          INNER JOIN devices d ON ports.device_id = d.id "
		  + "          WHERE d.serial_num = serial_num "
		  + "        ) * 100, 0) AS utilization "
		  + "      FROM ports p  "
		  + "      INNER JOIN devices d ON p.device_id = d.id  "
		  + "      INNER JOIN port_status ps ON p.port_status_id = ps.id "
		  + "      WHERE ps.port_status_value = 'BUSY' "
		  + "      GROUP BY d.serial_num "
		  + "  ) a where ROWNUM <= ?  "
		  + ") WHERE rnum  >= ? ";

	private static final String SQL_PROFIT_BY_MONTH = 
			"SELECT PROVIDER_LOCATIONS.LOCATION_NAME, SUM (PRODUCT_CATALOG.PRICE) AS SUM "
		  + "FROM PRODUCT_CATALOG  "
		  + "INNER JOIN SERVICE_INSTANCES SI ON PRODUCT_CATALOG.ID=SI.PRODUCT_CATALOG_ID "
		  + "INNER JOIN PROVIDER_LOCATIONS ON PROVIDER_LOCATIONS.ID=PRODUCT_CATALOG.PROVIDER_LOC_ID "
		  + "WHERE SI.START_DATE < ? "
		  + "    AND NOT EXISTS(SELECT * "
		  + "                   FROM SERVICE_ORDERS SO "
		  + "                   INNER JOIN ORDER_TYPE OT ON SO.ORDER_TYPE_ID = OT.ID "
		  + "                   WHERE SO.SERVICE_INSTANCE_ID = SI.ID "
		  + "                      AND SO.ORDER_DATE < ADD_MONTHS(?, 1) "
		  + "                      AND OT.ORDER_TYPE_VALUE = 'DISCONNECT' "
		  + "                  ) "
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
	public ArrayList<RouterUtilRow> getRouterUtilReport(int offset, int count) 
			throws SQLException {

		int lastRow = offset + count - 1;
		
		stmt = connection.prepareStatement(SQL_ROUTER_UTIL);
		stmt.setInt(1, lastRow);
		stmt.setInt(2, offset);
		rs = stmt.executeQuery();
		
		ArrayList<RouterUtilRow> report = new ArrayList<RouterUtilRow>();
		RouterUtilRow item = new RouterUtilRow();
		while (rs.next()) {
			item.setRouterSN(rs.getString("serial_num"));
			item.setRouterUtil(rs.getDouble("utilization"));
			item.setCapacity(rs.getInt("capacity"));
			report.add(item);
		}
		return report;
	}

	@Override
	public ArrayList<ProfitabilityByMonthRow> getProfitByMonthReport(Date startOfMonth)
			throws SQLException {
		stmt = connection.prepareStatement(SQL_PROFIT_BY_MONTH);
		stmt.setDate(1, startOfMonth);
	    stmt.setDate(2, startOfMonth);
		rs = stmt.executeQuery();
		
		ArrayList<ProfitabilityByMonthRow> report = new ArrayList<ProfitabilityByMonthRow>();
		ProfitabilityByMonthRow item = new ProfitabilityByMonthRow();
		while (rs.next()) {
			item.setProviderLocation(rs.getString("location_name"));
			item.setProfit(rs.getLong("sum"));
			report.add(item);
		}
		return report;
	}
	
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
