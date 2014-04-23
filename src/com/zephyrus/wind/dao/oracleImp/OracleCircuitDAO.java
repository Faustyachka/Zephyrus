package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.model.Circuit;

public class OracleCircuitDAO extends OracleDAO<Circuit> implements ICircuitDAO{
	
	private static final String TABLE_NAME = "CIRCUITS";
    private static final String SQL_SELECT = "SELECT ID, PORT_ID " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET PORT_ID = ?" + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (PORT_ID) " + 
                                      
                                      "VALUES (?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_PORT_ID = 2;
    private static final int COLUMN_STATUS = 3;    

	public OracleCircuitDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Circuit.class, connection, daoFactory);
	}

	@Override
	public void update(Circuit record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(COLUMN_PORT_ID, record.getPortId());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public Circuit insert(Circuit record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setInt(COLUMN_PORT_ID, record.getPortId());
        stmt.executeUpdate();		
		return null;
	}

	@Override
	public int remove(Circuit record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Circuit item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setPortId(rs.getInt(COLUMN_PORT_ID));
		
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
