package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.model.ServiceLocation;

public class OracleServiceLocationDAO extends OracleDAO<ServiceLocation> implements IServiceLocationDAO {
	private static final String TABLE_NAME = "SERVICE_LOCATIONS";
    private static final String SQL_SELECT = "SELECT ID, SERVICE_LOCATION_COORD, USER_ID " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_LOCATION_COORD = ?, USER_ID = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERVICE_LOCATION_COORD, USER_ID) " + 
                                      
                                      "VALUES (?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERVICE_LOCATION_COORD = 2;
    private static final int COLUMN_USER_ID = 3;  
	public OracleServiceLocationDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceLocation.class, connection, daoFactory);
	}

	@Override
	public void update(ServiceLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_SERVICE_LOCATION_COORD, record.getServiceLocationCoord());
    	stmt.setBigDecimal(COLUMN_USER_ID, record.getUserId());    	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
	}

	@Override
	public int insert(ServiceLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_SERVICE_LOCATION_COORD, record.getServiceLocationCoord());
    	stmt.setBigDecimal(COLUMN_USER_ID, record.getUserId());    
		return stmt.executeUpdate();
	}

	@Override
	public int remove(ServiceLocation record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceLocation item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
		item.setServiceLocationCoord(rs.getString(COLUMN_SERVICE_LOCATION_COORD));
		item.setUserId(rs.getBigDecimal(COLUMN_USER_ID));
		
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
