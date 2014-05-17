package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.model.Device;

/**
 * OracleDeviceDAO implementation of the IDeviceDAO interface and extend OracleDAO<Device>. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleDeviceDAO works with DB table DEVICES and Device class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 *
 */
public class OracleDeviceDAO extends OracleDAO<Device> implements IDeviceDAO{

	private static final String TABLE_NAME = "DEVICES";
	private static final String SQL_SELECT = 
			"SELECT ID, SERIAL_NUM, ROWNUM AS ROW_NUM " + 
			"FROM " +  TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET SERIAL_NUM = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT =  
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(SERIAL_NUM) VALUES(?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class Device
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleDeviceDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Device.class, connection, daoFactory);
	}

	/**
	 * Method update Device class instance using command update in the database.
	 * @param Device class instance
	 */
	@Override
	public void update(Device record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getSerialNum());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant Device class instance.
	 * @param Device class instance
	 * @return inserted Device class instance
	 */
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

	/**
	 * Method delete Device class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(Device record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes Device class instance and set values to its variables 
	 * @param class instance Device
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(Device item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
		item.setSerialNum(rs.getString(2));  		
	}

	/**
   	 * Method gives text of SQL select query
   	 *@return text of select query
   	 */
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	/**
   	 * Method gives text of SQL delete query
   	 *@return text of delete query
   	 */
	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}


}
