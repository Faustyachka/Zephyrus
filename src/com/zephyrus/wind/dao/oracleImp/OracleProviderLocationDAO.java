package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProviderLocation;

/**
 * OracleProviderLocationDAO implementation of the IProviderLocationDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleProviderLocationDAO works with DB table PROVIDER_LOCATIONS and ProviderLocation class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleProviderLocationDAO extends OracleDAO<ProviderLocation> implements IProviderLocationDAO {     	
	private static final String TABLE_NAME = "PROVIDER_LOCATIONS";
	private static final String SQL_SELECT = 
			"SELECT ID, LOCATION_NAME, LOCATION_COORD, ROWNUM AS ROW_NUM " + 
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET LOCATION_NAME = ?, LOCATION_COORD = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(LOCATION_NAME, LOCATION_COORD) VALUES(?,?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class ProviderLocation
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleProviderLocationDAO(Connection connection, OracleDAOFactory daoFactory) 
			throws Exception {  	
		super(ProviderLocation.class, connection, daoFactory);
	}

	/**
	 * Method update ProviderLocation class instance using command update in the database.
	 * @param ProviderLocation class instance
	 */
	@Override
	public void update(ProviderLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getLocationName());
		stmt.setString(2, record.getLocationCoord());    	
		stmt.setLong(3, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}
	
	/**
	 * Method create new record and compliant ProviderLocation class instance.
	 * @param inserting ProviderLocation class instance
	 * @return inserted ProviderLocation class instance
	 */
	@Override
	public ProviderLocation insert(ProviderLocation record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getLocationName());
		cs.setString(2, record.getLocationCoord());    
		cs.registerOutParameter(3, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(3);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete ProviderLocation class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(ProviderLocation record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes ProviderLocation class instance and set values to its variables 
	 * @param class instance ProviderLocation
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(ProviderLocation item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getInt(1));
		item.setLocationName(rs.getString(2));
		item.setLocationCoord(rs.getString(3));

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
