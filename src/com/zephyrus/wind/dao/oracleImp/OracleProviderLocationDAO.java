package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProviderLocation;

public class OracleProviderLocationDAO extends OracleDAO<ProviderLocation> implements IProviderLocationDAO{
	private static final String TABLE_NAME = "PROVIDER_LOCATIONS";
    private static final String SQL_SELECT = "SELECT ID, LOCATION_NAME, LOCATION_COORD, ROWNUM AS ROW_NUM " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET LOCATION_NAME = ?, LOCATION_COORD = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
												"(LOCATION_NAME, LOCATION_COORD) VALUES(?,?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";

    
	public OracleProviderLocationDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ProviderLocation.class, connection, daoFactory);
	}

	@Override
	public void update(ProviderLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getLocationName());
    	stmt.setString(2, record.getLocationCoord());    	
    	stmt.setLong(3, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

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

	@Override
	public int remove(ProviderLocation record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ProviderLocation item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getInt(1));
		item.setLocationName(rs.getString(2));
		item.setLocationCoord(rs.getString(3));
		
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
