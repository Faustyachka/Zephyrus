package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.model.OrderType;

/**
 * OracleOrderTypeDAO implementation of the IOrderTypeDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleOrderTypeDAO works with DB table ORDER_TYPE and OrderType class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 *
 */
public class OracleOrderTypeDAO extends OracleDAO<OrderType> implements IOrderTypeDAO {

	private static final String TABLE_NAME = "ORDER_TYPE";
	private static final String SQL_SELECT = 
			"SELECT ID, ORDER_TYPE_VALUE, ROWNUM AS ROW_NUM " + 
			"FROM " +  TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET ORDER_TYPE_VALUE = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(ORDER_TYPE_VALUE) VALUES(?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class OrderType
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleOrderTypeDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(OrderType.class, connection, daoFactory);
	}

	/**
	 * Method update OrderType class instance using command update in the database.
	 * @param OrderType class instance
	 */	
	@Override
	public void update(OrderType record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getOrderType());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant OrderType class instance.
	 * @param OrderType class instance
	 * @return inserted OrderType class instance
	 */
	@Override
	public OrderType insert(OrderType record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getOrderType());
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(2);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete OrderType class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(OrderType record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes OrderType class instance and set values to its variables 
	 * @param class instance OrderType
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(OrderType item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
		item.setOrderType(rs.getString(2));

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
