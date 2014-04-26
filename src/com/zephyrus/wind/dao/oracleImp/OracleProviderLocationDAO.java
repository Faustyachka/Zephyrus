package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.reports.ProfitabilityByMonth;
import com.zephyrus.wind.reports.RouterUtil;

public class OracleProviderLocationDAO extends OracleDAO<ProviderLocation> implements IProviderLocationDAO{
	private static final String TABLE_NAME = "PROVIDER_LOCATIONS";
    private static final String SQL_SELECT = "SELECT ID, LOCATION_NAME, LOCATION_COORD " + 
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
    private static final String SQL_PROFIT = "SELECT PROVIDER_LOCATIONS.LOCATION_NAME,"+
    										 "SUM (PRODUCT_CATALOG.PRICE) AS SUM"+
    										 "FROM PRODUCT_CATALOG INNER JOIN SERVICE_INSTANCES "+
    										 "ON PRODUCT_CATALOG.ID=SERVICE_INSTANCES.PRODUCT_CATALOG_ID "+
    										 "INNER JOIN PROVIDER_LOCATIONS "+
    										 "ON PROVIDER_LOCATIONS.ID=PRODUCT_CATALOG.PROVIDER_LOC_ID"+
    										 "WHERE SERVICE_INSTANCES.START_DATE < ?"+
    										 "GROUP BY PROVIDER_LOCATIONS.LOCATION_NAME;";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_LOCATION_NAME = 2;
    private static final int COLUMN_LOCATION_COORD = 3;  

	public OracleProviderLocationDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ProviderLocation.class, connection, daoFactory);
	}

	@Override
	public void update(ProviderLocation record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_LOCATION_NAME, record.getLocationName());
    	stmt.setString(COLUMN_LOCATION_COORD, record.getLocationCoord());    	
    	stmt.setLong(COLUMN_ID, record.getId());
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
		item.setId(rs.getInt(COLUMN_ID));
		item.setLocationName(rs.getString(COLUMN_LOCATION_NAME));
		item.setLocationCoord(rs.getString(COLUMN_LOCATION_COORD));
		
	}
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}
	@Override
	//Return list of Provider location, and profit by month
	public ArrayList<ProfitabilityByMonth> getProfitByMonth (Date month) throws Exception {
		stmt = connection.prepareStatement(SQL_PROFIT);
		stmt.setDate(1, month);
		rs = stmt.executeQuery();		
		ArrayList<ProfitabilityByMonth> resultList = new ArrayList<ProfitabilityByMonth>();
		ProfitabilityByMonth item = new ProfitabilityByMonth();
		while (rs.next()){
			item.setProviderLocation(rs.getString(1));
			item.setProfit(rs.getLong(2));
        	resultList.add(item);
        }
		return resultList;
	}
}
