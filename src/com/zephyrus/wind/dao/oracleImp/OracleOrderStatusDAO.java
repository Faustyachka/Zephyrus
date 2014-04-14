package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.model.OrderStatus;

public class OracleOrderStatusDAO extends OracleDAO<OrderStatus> implements IOrderStatusDAO {
	
	private final String TABLE_NAME = "ORDER_STATUS";
    private final String SQL_SELECT = "SELECT ID, ORDER_STATUS_VALUE " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ORDER_STATUS_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (ORDER_STATUS_VALUE) " + 
                                      
                                      "VALUES (?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_ORDER_STATUS_VALUE = 2;

	public OracleOrderStatusDAO(
			Connection connection) throws Exception {
		super(OrderStatus.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(OrderStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_ORDER_STATUS_VALUE, record.getOrderStatusValue());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();		
	}

	@Override
	public int insert(OrderStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_ORDER_STATUS_VALUE, record.getOrderStatusValue());    	
        stmt.executeUpdate();		
		return stmt.executeUpdate();
	}

	@Override
	public int remove(OrderStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(OrderStatus item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setOrderStatusValue(rs.getString(COLUMN_ORDER_STATUS_VALUE));
		
	}

}