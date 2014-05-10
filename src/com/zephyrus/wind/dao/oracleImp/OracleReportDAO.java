package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.reports.IReport;
import com.zephyrus.wind.reports.rows.DisconnectOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rows.MostProfitableRouterRow;
import com.zephyrus.wind.reports.rows.NewOrdersPerPeriodRow;
import com.zephyrus.wind.reports.rows.ProfitabilityByMonthRow;
import com.zephyrus.wind.reports.rows.RouterUtilRow;

/**
 * This class provides functionality of report data fetching to 
 * report generation classes. It allows to perform queries to DB
 * in order to obtain specific report data.
 * @author Kostya Trukhan & Igor Litvinenko
 */
public class OracleReportDAO extends OracleDAO<IReport> implements IReportDAO {
	
	/**
	 * This query allows to fetch data for Most profitable router report.
	 * It's done by obtaining serial number and summary profit of all routers
	 * in descending order by their profit in specified period. The period
	 * starts from given start date and ends after given end date. Note that 
	 * SI should be active during that period to be placed into the report.
	 */
	private static final String SQL_MOST_PROFIT_ROUTER =   
			  "SELECT D.SERIAL_NUM, SUM (PRODUCT_CATALOG.PRICE) AS DEVICE_PROFIT "
			+ "FROM PRODUCT_CATALOG  "
			+ "INNER JOIN SERVICE_INSTANCES SI ON PRODUCT_CATALOG.ID=SI.PRODUCT_CATALOG_ID "
			+ "INNER JOIN CIRCUITS C ON SI.CIRCUIT_ID = C.ID "
			+ "INNER JOIN PORTS P ON C.PORT_ID = P.ID "
			+ "INNER JOIN DEVICES D ON P.DEVICE_ID = D.ID "
			+ "WHERE SI.START_DATE < ? "
			+ "    AND NOT EXISTS(SELECT NULL "
			+ "                   FROM SERVICE_ORDERS SO "
			+ "                   INNER JOIN ORDER_TYPE OT ON SO.ORDER_TYPE_ID = OT.ID "
			+ "                   WHERE SO.SERVICE_INSTANCE_ID = SI.ID "
			+ "                      AND SO.ORDER_DATE < ? "
			+ "                      AND OT.ORDER_TYPE_VALUE = 'DISCONNECT' "
			+ "                  ) "
			+ "GROUP BY D.SERIAL_NUM "
			+ "ORDER BY DEVICE_PROFIT DESC";
	
	/**
	 * This query obtains serial number, capacity and utilization
	 * every router in system. Capacity is calculated as the number of ports,
	 * linked with current device whereas utilization is number of ports that are
	 * currently used, divided by capacity of router. Paging is used to fetch only
	 * specified range of results.
	 */
	private static final String SQL_ROUTER_UTIL = 
			  	"SELECT * FROM (  "
			  + "  SELECT a.*, ROWNUM rnum FROM ( "
			  + "      SELECT serial_num,  "
			  + "        COUNT(ports.id) AS capacity, "
			  + "        ROUND( "
			  + "        (SELECT COUNT(p.id) "
			  + "          FROM ports p "
			  + "          INNER JOIN devices d ON p.device_id = d.id "
			  + "          INNER JOIN port_status ps ON p.port_status_id = ps.id "
			  + "          WHERE ps.port_status_value = 'BUSY' "
			  + "              AND d.serial_num = d2.serial_num "
			  + "        ) / COUNT(1) * 100, 0) AS utilization "
			  + "      FROM ports "
			  + "      RIGHT JOIN devices d2 ON ports.device_id = d2.id "
			  + "      GROUP BY d2.serial_num "
			  + "  ) a where ROWNUM <= ?  "
			  + ") WHERE rnum  >= ?";
	
	/**
	 * This query obtains summary profit of every provider location, based on the 
	 * prices of all service instances, connected to it. Note that only service instances
	 * that were active in specified period are considered. Given period starts from
	 * given start date and ends one month later. 
	 */
	private static final String SQL_PROFIT_BY_MONTH = 
			"SELECT PROVIDER_LOCATIONS.LOCATION_NAME, SUM (PRODUCT_CATALOG.PRICE) AS SUM "
		  + "FROM PRODUCT_CATALOG  "
		  + "INNER JOIN SERVICE_INSTANCES SI ON PRODUCT_CATALOG.ID=SI.PRODUCT_CATALOG_ID "
		  + "INNER JOIN PROVIDER_LOCATIONS ON PROVIDER_LOCATIONS.ID=PRODUCT_CATALOG.PROVIDER_LOC_ID "
		  + "WHERE SI.START_DATE < ? "
		  + "    AND NOT EXISTS(SELECT NULL "
		  + "                   FROM SERVICE_ORDERS SO "
		  + "                   INNER JOIN ORDER_TYPE OT ON SO.ORDER_TYPE_ID = OT.ID "
		  + "                   WHERE SO.SERVICE_INSTANCE_ID = SI.ID "
		  + "                      AND SO.ORDER_DATE < ADD_MONTHS(?, 1) "
		  + "                      AND OT.ORDER_TYPE_VALUE = 'DISCONNECT' "
		  + "                  ) "
		  + "GROUP BY PROVIDER_LOCATIONS.LOCATION_NAME";
	
	/**
	 * This query obtains all service orders of specified period,
	 * which type is "DISCONNECT". Additional information connected with
	 * current order also is retrieved in order to form report.
	 * Paging is used to fetch only specified range of results.
	 */
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
	
	/**
	 * This query obtains all service orders of specified period,
	 * which type is "NEW". Additional information connected with
	 * current order also is retrieved in order to form report.
	 * Paging is used to fetch only specified range of results.
	 */
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
	protected void fillItem(IReport item, ResultSet rs) { }

	@Override
	protected String getSelect() {
		return null;
	}

	@Override
	protected String getDelete() {
		return null;
	}

	@Override
	public void update(IReport record) { }

	@Override
	public IReport insert(IReport record) {
		return null;
	}

	@Override
	public int remove(IReport record) {
		return 0;
	}
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report about given period. 
	 * Note that this report obtains single row as the most profitable router, 
	 * but list of rows is returned to consider possible requirement change.
	 * @param startDate start of period
     * @param endDate end of period
     * @return List of fetched records that comprise report data set
	 */
	@Override
	public ArrayList<MostProfitableRouterRow> getMostProfitableRouterReport(Date startDate, Date endDate)
			throws SQLException {
		
		stmt = connection.prepareStatement(SQL_MOST_PROFIT_ROUTER);
		stmt.setDate(1, startDate);
		stmt.setDate(2, endDate);
		rs = stmt.executeQuery();
		ArrayList<MostProfitableRouterRow> report = new ArrayList<MostProfitableRouterRow>();
		MostProfitableRouterRow item = new MostProfitableRouterRow();
		if (rs.next()) {
			item.setRouterSN(rs.getString("serial_num"));
			item.setProfit(rs.getLong("device_profit"));
			report.add(item);
		}
		rs.close();
		return report;
	}
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report. Paging is used to fetch only
	 * specified range of records.
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
     * @return List of fetched records that comprise report data set
	 */
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
		rs.close();
		return report;
	}
	
	/**
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report. 
	 * @param startOfMonth Date representing start of month to obtain report for
     * @return List of fetched records that comprise report data set
	 */
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
		rs.close();
		return report;
	}
	
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
	 * This method is used to fetch results of corresponding query in order to form 
	 * list of records used to form the report about given period. Paging is used 
	 * to fetch only specified range of records.
	 * @param startDate start of period
     * @param endDate end of period
	 * @param offset index of the first record to be fetched, starting from 1
	 * @param count number of records to be fetched
     * @return List of fetched records that comprise report data set
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
