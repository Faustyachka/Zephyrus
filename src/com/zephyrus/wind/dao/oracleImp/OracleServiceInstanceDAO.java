package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.model.ServiceInstance;

public class OracleServiceInstanceDAO extends OracleDAO<ServiceInstance> implements IServiceInstanceDAO {
	
	private static final String TABLE_NAME = "SERVICE_INSTANCES";
    private static final String SQL_SELECT = "SELECT ID, SERV_INSTANCE_STATUS_ID, USER_ID, " + 
    								  "PRODUCT_CATALOG_ID, CIRCUIT_ID, START_DATE" +
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERV_INSTANCE_STATUS_ID = ?, USER_ID = ?, " + 
                                      " PRODUCT_CATALOG_ID = ?, CIRCUIT_ID = ?, START_DATE = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERV_INSTANCE_STATUS_ID, USER_ID, " + 
    								  "PRODUCT_CATALOG_ID, CIRCUIT_ID, START_DATE) " +                                      
                                      "VALUES (?,?,?,?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERV_INSTANCE_STATUS_ID = 2;
    private static final int COLUMN_USER_ID = 3;  
    private static final int COLUMN_PRODUCT_CATALOG_ID = 4;  
    private static final int COLUMN_CIRCUIT_ID = 5;  
    private static final int COLUMN_START_DATE = 6;  

	public OracleServiceInstanceDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceInstance.class, connection, daoFactory);
	}

	@Override
	public void update(ServiceInstance record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setBigDecimal(COLUMN_SERV_INSTANCE_STATUS_ID, record.getServInstanceStatusId());   
    	stmt.setBigDecimal(COLUMN_USER_ID, record.getUserId());  
    	stmt.setBigDecimal(COLUMN_PRODUCT_CATALOG_ID, record.getProductCatalogId());  
    	stmt.setBigDecimal(COLUMN_CIRCUIT_ID, record.getCircuitId());  
    	stmt.setDate(COLUMN_START_DATE, (java.sql.Date)record.getStartDate());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(ServiceInstance record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setBigDecimal(COLUMN_SERV_INSTANCE_STATUS_ID, record.getServInstanceStatusId());   
    	stmt.setBigDecimal(COLUMN_USER_ID, record.getUserId());  
    	stmt.setBigDecimal(COLUMN_PRODUCT_CATALOG_ID, record.getProductCatalogId());  
    	stmt.setBigDecimal(COLUMN_CIRCUIT_ID, record.getCircuitId());  
    	stmt.setDate(COLUMN_START_DATE, (java.sql.Date)record.getStartDate());
    	return stmt.executeUpdate();
	}

	@Override
	public int remove(ServiceInstance record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceInstance item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
		item.setCircuitId(rs.getBigDecimal(COLUMN_CIRCUIT_ID));
		item.setProductCatalogId(rs.getBigDecimal(COLUMN_PRODUCT_CATALOG_ID));
		item.setServInstanceStatusId(rs.getBigDecimal(COLUMN_SERV_INSTANCE_STATUS_ID));
		item.setUserId(rs.getBigDecimal(COLUMN_USER_ID));
		item.setStartDate(rs.getDate(COLUMN_START_DATE));
		
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
