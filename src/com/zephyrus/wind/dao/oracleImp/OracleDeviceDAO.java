package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.model.Device;

public class OracleDeviceDAO extends OracleDAO<Device> implements IDeviceDAO{
	
	private static final String TABLE_NAME = "DEVICES";
    private static final String SQL_SELECT = "SELECT ID, SERIAL_NUM, ROWNUM AS ROW_NUM " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERIAL_NUM = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT =  "BEGIN INSERT INTO " + TABLE_NAME + 
												"(SERIAL_NUM) VALUES(?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";

    
	public OracleDeviceDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Device.class, connection, daoFactory);
	}

	@Override
	public void update(Device record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getSerialNum());  	
    	stmt.setLong(2, record.getId());
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
		item.setId(rs.getInt(1));
    	item.setSerialNum(rs.getString(2));  		
	}
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}


}
