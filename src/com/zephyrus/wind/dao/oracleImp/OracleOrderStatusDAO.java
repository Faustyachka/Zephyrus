package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.model.OrderStatus;

/**
 * OracleOrderStatusDAO implementation of the IOrderStatusDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleOrderStatusDAO works with DB table ORDER_STATUS and OrderStatus class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 *
 */
public class OracleOrderStatusDAO extends OracleDAO<OrderStatus> implements IOrderStatusDAO {

	private static final String TABLE_NAME = "ORDER_STATUS";
	private static final String SQL_SELECT = 
			"SELECT ID, ORDER_STATUS_VALUE, ROWNUM AS ROW_NUM " + 
			"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET ORDER_STATUS_VALUE = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(ORDER_STATUS_VALUE) VALUES(?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class OrderStatus
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleOrderStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(OrderStatus.class, connection, daoFactory);
	}

	/**
	 * Method update OrderStatus class instance using command update in the database.
	 * @param OrderStatus class instance
	 */
	@Override
	public void update(OrderStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getOrderStatusValue());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant OrderStatus class instance.
	 * @param OrderStatus class instance
	 * @return inserted OrderStatus class instance
	 */
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

	/**
	 * Method delete OrderStatus class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(OrderStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes OrderStatus class instance and set values to its variables 
	 * @param class instance OrderStatus
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(OrderStatus item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
		item.setOrderStatusValue(rs.getString(2));

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
