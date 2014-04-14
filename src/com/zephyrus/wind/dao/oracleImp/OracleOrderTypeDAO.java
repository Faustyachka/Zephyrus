package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.model.OrderType;

public class OracleOrderTypeDAO extends OracleDAO<OrderType> implements IOrderTypeDAO {
	
	private final String TABLE_NAME = "ORDER_TYPE";
    private final String SQL_SELECT = "SELECT ID, ORDER_TYPE_VALUE " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ORDER_TYPE_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (ORDER_TYPE_VALUE) " + 
                                      
                                      "VALUES (?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_ORDER_TYPE_VALUE = 2;

	public OracleOrderTypeDAO( Connection connection)
			throws Exception {
		super(OrderType.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(OrderType record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_ORDER_TYPE_VALUE, record.getOrderType());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(OrderType record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_ORDER_TYPE_VALUE, record.getOrderType());    	
        stmt.executeUpdate();		
		return stmt.executeUpdate();
	}

	@Override
	public int remove(OrderType record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(OrderType item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setOrderType(rs.getString(COLUMN_ORDER_TYPE_VALUE));
		
	}

}
