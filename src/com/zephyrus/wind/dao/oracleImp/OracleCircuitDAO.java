package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.model.Circuit;

public class OracleCircuitDAO extends OracleDAO<Circuit> implements ICircuitDAO{
	
	private static final String TABLE_NAME = "CIRCUITS";
    private static final String SQL_SELECT = "SELECT ID, PORT_ID, STATUS " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET PORT_ID = ?, STATUS = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (PORT_ID, STATUS) " + 
                                      
                                      "VALUES (?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_PORT_ID = 2;
    private static final int COLUMN_STATUS = 3;    

	public OracleCircuitDAO(Connection connection)
			throws Exception {
		super(Circuit.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(Circuit record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setBigDecimal(COLUMN_PORT_ID, record.getPortId());
    	stmt.setBigDecimal(COLUMN_STATUS, record.getStatus());    	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(Circuit record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setBigDecimal(COLUMN_PORT_ID, record.getPortId());
    	stmt.setBigDecimal(COLUMN_STATUS, record.getStatus());    	    	
        stmt.executeUpdate();		
		return stmt.executeUpdate();
	}

	@Override
	public int remove(Circuit record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Circuit item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setPortId(rs.getBigDecimal(COLUMN_PORT_ID));
    	item.setStatus(rs.getBigDecimal(COLUMN_STATUS));
		
	}

}
