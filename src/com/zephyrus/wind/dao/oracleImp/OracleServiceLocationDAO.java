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
 * 
 * 
 * @author Miroshnychenko Nataliya
 */

public class OracleServiceLocationDAO extends OracleDAO<ServiceLocation> implements IServiceLocationDAO {
	private static final String TABLE_NAME = "SERVICE_LOCATIONS";
	private static final String SQL_SELECT = "SELECT ID, SERVICE_LOCATION_COORD, SERVICE_LOCATION_ADD, " +
											" USER_ID, ROWNUM AS ROW_NUM " + 
											"FROM " + 
											TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_LOCATION_COORD = ?, " + 
                                      " SET SERVICE_LOCATION_ADD = ?, " + 
                                      " USER_ID = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
    								"(SERVICE_LOCATION_COORD, SERVICE_LOCATION_ADD, USER_ID) VALUES(?,?,?) " +
    								"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";
    
    
    
	public OracleServiceLocationDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceLocation.class, connection, daoFactory);
	}

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

	@Override
	public ServiceLocation insert(ServiceLocation record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setString(1, record.getServiceLocationCoord());
    	if(record.getAddress() == null){
			cs.setNull(2, java.sql.Types.INTEGER);
		}
		else {
			cs.setString(2, record.getAddress());
		}
    	cs.setInt(3, record.getUser().getId());    
    	cs.registerOutParameter(4, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(4);
        cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(ServiceLocation record) throws Exception {
		return removeById((int)record.getId());
	}

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
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

	/**
	 * Method finds Service Location objects by User ID
	 * 
	 * @param User ID
	 * @return existing Service Location
	 * @author Miroshnychenko Nataliya
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
