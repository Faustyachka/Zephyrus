package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.model.OrderStatus;

public class OracleOrderStatusDAO extends OracleDAO<OrderStatus> implements IOrderStatusDAO {
	
	private static final String TABLE_NAME = "ORDER_STATUS";
    private static final String SQL_SELECT = "SELECT ID, ORDER_STATUS_VALUE " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ORDER_STATUS_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
												"(ORDER_STATUS_VALUE) VALUES(?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_ORDER_STATUS_VALUE = 2;

	public OracleOrderStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(OrderStatus.class, connection, daoFactory);
	}

	@Override
	public void update(OrderStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getOrderStatusValue());  	
    	stmt.setLong(2, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public OrderStatus insert(OrderStatus record) throws Exception {
		
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setString(1, record.getOrderStatusValue());
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
        cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(OrderStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(OrderStatus item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setOrderStatusValue(rs.getString(COLUMN_ORDER_STATUS_VALUE));
		
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
