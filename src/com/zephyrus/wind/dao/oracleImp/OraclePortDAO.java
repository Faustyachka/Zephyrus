package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.model.Port;

public class OraclePortDAO extends OracleDAO<Port> implements IPortDAO {

	private static final String TABLE_NAME = "PORTS";
    private static final String SQL_SELECT = "SELECT ID, DEVICE_ID, PORT_NUMBER " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET DEVICE_ID = ?, PORT_NUMBER = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (DEVICE_ID, PORT_NUMBER) " + 
                                      
                                      "VALUES (?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_DEVICE_ID = 2;
    private static final int COLUMN_PORT_NUMBER = 3;   
    private static final int COLUMN_PORT_ON = 4;  

	public OraclePortDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Port.class, connection, daoFactory);
	}

	@Override
	public void update(Port record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(COLUMN_DEVICE_ID, record.getDeviceId());
    	stmt.setInt(COLUMN_PORT_NUMBER, record.getPortNumber()); 
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public Port insert(Port record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setInt(COLUMN_DEVICE_ID, record.getDeviceId());
    	stmt.setInt(COLUMN_PORT_NUMBER, record.getPortNumber());
    	stmt.executeUpdate();
		return null;
	}

	@Override
	public int remove(Port record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Port item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setDeviceId(rs.getInt(COLUMN_DEVICE_ID));
    	item.setPortNumber(rs.getInt(COLUMN_PORT_NUMBER));
		
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
