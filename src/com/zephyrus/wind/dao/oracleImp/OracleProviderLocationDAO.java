package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProviderLocation;

public class OracleProviderLocationDAO extends OracleDAO<ProviderLocation> implements IProviderLocationDAO{
	private final String TABLE_NAME = "PROVIDER_LOCATIONS";
    private final String SQL_SELECT = "SELECT ID, LOCATION_NAME, LOCATION_COORD " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET LOCATION_NAME = ?, LOCATION_COORD = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (LOCATION_NAME, LOCATION_COORD) " + 
                                      
                                      "VALUES (?,?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_LOCATION_NAME = 2;
    private static final int COLUMN_LOCATION_COORD = 3;  

	public OracleProviderLocationDAO(Connection connection) throws Exception {
		super(ProviderLocation.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(ProviderLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_LOCATION_NAME, record.getLocationName());
    	stmt.setString(COLUMN_LOCATION_COORD, record.getLocationCoord());    	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(ProviderLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_LOCATION_NAME, record.getLocationName());
    	stmt.setString(COLUMN_LOCATION_COORD, record.getLocationCoord()); 
    	return stmt.executeUpdate();
	}

	@Override
	public int remove(ProviderLocation record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ProviderLocation item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
		item.setLocationName(rs.getString(COLUMN_LOCATION_NAME));
		item.setLocationCoord(rs.getString(COLUMN_LOCATION_COORD));
		
	}

}
