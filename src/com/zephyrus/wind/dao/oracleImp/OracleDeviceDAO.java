package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.model.Device;

public class OracleDeviceDAO extends OracleDAO<Device> implements IDeviceDAO{
	
	private final String TABLE_NAME = "DEVICES";
    private final String SQL_SELECT = "SELECT ID, SERIAL_NUM " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERIAL_NUM = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERIAL_NUM) " + 
                                      
                                      "VALUES (?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERIAL_NUM = 2;

	public OracleDeviceDAO(Connection connection)
			throws Exception {
		super(Device.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(Device record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_SERIAL_NUM, record.getSerialNum());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(Device record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_SERIAL_NUM, record.getSerialNum());	    	
        stmt.executeUpdate();		
		return stmt.executeUpdate();
	}

	@Override
	public int remove(Device record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Device item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setSerialNum(rs.getString(COLUMN_SERIAL_NUM));  		
	}

}
