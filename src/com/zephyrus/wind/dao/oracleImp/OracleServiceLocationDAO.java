package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.User;

/**
 * OracleServiceLocationDAO implementation of the IServiceLocationDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleServiceLocationDAO works with DB table SERVICE_LOCATIONS and ServiceLocation class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleServiceLocationDAO extends OracleDAO<ServiceLocation> implements IServiceLocationDAO {
	private static final String TABLE_NAME = "SERVICE_LOCATIONS";
	private static final String SQL_SELECT = 
			"SELECT ID, SERVICE_LOCATION_COORD, SERVICE_LOCATION_ADD, USER_ID, ROWNUM AS ROW_NUM " + 								
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET SERVICE_LOCATION_COORD = ?, SERVICE_LOCATION_ADD = ?, USER_ID = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(SERVICE_LOCATION_COORD, SERVICE_LOCATION_ADD, USER_ID) VALUES(?,?,?) " +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class ServiceLocation
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleServiceLocationDAO(Connection connection, OracleDAOFactory daoFactory) 
			throws Exception {   
		super(ServiceLocation.class, connection, daoFactory);
	}

	/**
	 * Method update ServiceLocation class instance using command update in the database.
	 * @param ServiceLocation class instance
	 */
	@Override
	public void update(ServiceLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getServiceLocationCoord());
		if (record.getAddress() == null){
			stmt.setNull(2, java.sql.Types.INTEGER); 
		} else {
			stmt.setString(2, record.getAddress());
		}
		stmt.setInt(3, record.getUser().getId());    	
		stmt.setLong(4, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant ServiceLocation class instance.
	 * @param inserting ServiceLocation class instance
	 * @return inserted ServiceLocation class instance
	 */
	@Override
	public ServiceLocation insert(ServiceLocation record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getServiceLocationCoord());
		if(record.getAddress() == null){
			cs.setNull(2, java.sql.Types.INTEGER);
		} else {																						
			cs.setString(2, record.getAddress());
		}
		cs.setInt(3, record.getUser().getId());    
		cs.registerOutParameter(4, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(4);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete ServiceLocation class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(ServiceLocation record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes ServiceLocation class instance and set values to its variables 
	 * @param class instance ServiceLocation
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(ServiceLocation item, ResultSet rs) 
			throws SQLException, Exception {
		item.setId(rs.getInt(1));
		item.setServiceLocationCoord(rs.getString(2));
		item.setAddress(rs.getString(3));
		IUserDAO userDAO = daoFactory.getUserDAO();
		User user = userDAO.findById(rs.getInt(4));
		item.setUser(user);

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

	/**
	 * Method finds Service Location objects by User ID
	 * @param User ID
	 * @return existing Service Location
	 */
	@Override
	public ArrayList<ServiceLocation> getServiceLocationsByUserId(int userID)
			throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, userID);
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

}
