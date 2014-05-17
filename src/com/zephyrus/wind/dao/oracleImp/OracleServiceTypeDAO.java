package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.model.ServiceType;

/**
 * OracleServiceTypeDAO implementation of the IServiceTypeDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleServiceTypeDAO works with DB table SERVICE_TYPE and ServiceType class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleServiceTypeDAO extends OracleDAO<ServiceType> implements IServiceTypeDAO {       
	private static final String TABLE_NAME = "SERVICE_TYPE";
	private static final String SQL_SELECT = 
			"SELECT ID, SERVICE_TYPE_VALUE, ROWNUM AS ROW_NUM " + 
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET SERVICE_TYPE_VALUE = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			" (SERVICE_TYPE_VALUE) " +                                     
			"VALUES (?)" + " RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class ServiceType
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleServiceTypeDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceType.class, connection, daoFactory);
	}

	/**
	 * Method update ServiceType class instance using command update in the database.
	 * @param ServiceType class instance
	 */
	@Override
	public void update(ServiceType record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getServiceType());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant ServiceType class instance.
	 * @param inserting ServiceType class instance
	 * @return inserted ServiceType class instance
	 */
	@Override
	public ServiceType insert(ServiceType record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getServiceType());    	
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(2);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete ServiceType class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(ServiceType record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes ServiceType class instance and set values to its variables 
	 * @param class instance ServiceType
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(ServiceType item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
		item.setServiceType(rs.getString(2));
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
