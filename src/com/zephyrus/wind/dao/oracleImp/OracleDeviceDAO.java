package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.reports.MostProfitableRouter;
import com.zephyrus.wind.reports.RouterUtil;

public class OracleDeviceDAO extends OracleDAO<Device> implements IDeviceDAO{
	
	private static final String TABLE_NAME = "DEVICES";
    private static final String SQL_SELECT = "SELECT ID, SERIAL_NUM " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERIAL_NUM = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT =  "BEGIN INSERT INTO " + TABLE_NAME + 
												"(SERIAL_NUM) VALUES(?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    private static final String SQL_PROFIT = "SELECT DEVICES.SERIAL_NUM,"+
											 "SUM(PRODUCT_CATALOG.PRICE) AS SUM"+
											 "FROM DEVICES INNER JOIN PORTS"+
											 "ON DEVICES.ID = PORTS.DEVICE_ID"+
											 "INNER JOIN CIRCUITS"+
											 "ON PORTS.ID = CIRCUITS.PORT_ID"+
											 "INNER JOIN SERVICE_INSTANCES"+
											 "ON CIRCUITS.ID = SERVICE_INSTANCES.CIRCUIT_ID"+
											 "INNER JOIN PRODUCT_CATALOG"+
											 "ON PRODUCT_CATALOG.ID = SERVICE_INSTANCES.PRODUCT_CATALOG_ID"+
											 "GROUP BY DEVICES.SERIAL_NUM";
    private static final String SQL_UTIL =  "SELECT DEVICES.SERIAL_NUM,"+
    		  								"COUNT(PORTS.PORT_NUMBER) AS COUNT"+
    		  								"FROM DEVICES"+
    		  								"INNER JOIN PORTS"+
    		  								"ON DEVICES.ID = PORTS.DEVICE_ID"+
    		  								"GROUP BY DEVICES.SERIAL_NUM ;";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERIAL_NUM = 2;

	public OracleDeviceDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Device.class, connection, daoFactory);
	}

	@Override
	public void update(Device record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_SERIAL_NUM, record.getSerialNum());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public Device insert(Device record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setString(1, record.getSerialNum());    
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
        cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(Device record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Device item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setSerialNum(rs.getString(COLUMN_SERIAL_NUM));  		
	}
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

	
	@Override
	//Return list of router and their profit for reports
	public ArrayList<MostProfitableRouter> getProfitRouter () throws Exception {
		stmt = connection.prepareStatement(SQL_PROFIT);
		rs = stmt.executeQuery();		
		ArrayList<MostProfitableRouter> resultList = new ArrayList<MostProfitableRouter>();
		MostProfitableRouter item = new MostProfitableRouter();
		while (rs.next()){
			item.setProfit(rs.getLong(2));
			item.setRouterSN(rs.getString(1));
        	resultList.add(item);
        }
		return resultList;
	}
	@Override
	//Return list of router and their util
	public ArrayList<RouterUtil> getRouterUtil () throws Exception {
		stmt = connection.prepareStatement(SQL_UTIL);
		rs = stmt.executeQuery();		
		ArrayList<RouterUtil> resultList = new ArrayList<RouterUtil>();
		RouterUtil item = new RouterUtil();
		while (rs.next()){
			item.setRouterSN(rs.getString(1));
			item.setRouterUtil(rs.getInt(2)/60);
			item.setCapacity(0.6);
        	resultList.add(item);
        }
		return resultList;
	}

}
