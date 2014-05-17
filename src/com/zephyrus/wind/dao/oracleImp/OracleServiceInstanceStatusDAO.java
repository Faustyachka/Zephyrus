package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.model.ServiceInstanceStatus;

/**
 * OracleServiceInstanceStatusDAO implementation of the IServiceInstanceStatusDAO interface 
 * and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleServiceInstanceStatusDAO works with DB table SERVICE_INSTANCE_STATUS 
 * and ServiceInstanceStatus class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleServiceInstanceStatusDAO extends OracleDAO<ServiceInstanceStatus> 
implements IServiceInstanceStatusDAO { 

	private static final String TABLE_NAME = "SERVICE_INSTANCE_STATUS";
	private static final String SQL_SELECT = 
			"SELECT ID, SERV_INSTANCE_STATUS_VALUE, ROWNUM AS ROW_NUM " + 
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET SERV_INSTANCE_STATUS_VALUE = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			" (SERV_INSTANCE_STATUS_VALUE) " +                                  
			"VALUES (?)" + " RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class ServiceInstanceStatus
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleServiceInstanceStatusDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(ServiceInstanceStatus.class, connection, daoFactory);
	}

	/**
	 * Method update ServiceInstanceStatus class instance using command update in the database.
	 * @param ServiceInstanceStatus class instance
	 */
	@Override
	public void update(ServiceInstanceStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getServInstanceStatusValue());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant ServiceInstanceStatus class instance.
	 * @param inserting ServiceInstanceStatus class instance
	 * @return inserted ServiceInstanceStatus class instance
	 */
	@Override
	public ServiceInstanceStatus insert(ServiceInstanceStatus record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getServInstanceStatusValue());    	
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(2);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete ServiceInstanceStatus class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(ServiceInstanceStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes ServiceInstanceStatus class instance and set values to its variables 
	 * @param class instance ServiceInstanceStatus
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(ServiceInstanceStatus item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getInt(1));
		item.setServInstanceStatusValue(rs.getString(2));

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
