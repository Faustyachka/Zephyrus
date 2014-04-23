package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;




import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.VSupportInstance;

public class OracleServiceOrderDAO extends OracleDAO<ServiceOrder> implements IServiceOrderDAO {

	private static final String TABLE_NAME = "SERVICE_ORDERS";
    private static final String SQL_SELECT = "SELECT ID, ORDER_TYPE_ID, ORDER_STATUS_ID, " + 
    								  "ORDER_DATE, PRODUCT_CATALOG_ID, SERVICE_LOCATION_ID, SERVICE_INSTANCE_ID" +
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ORDER_TYPE_ID = ?, ORDER_STATUS_ID = ?, " + 
                                      " ORDER_DATE = ?, PRODUCT_CATALOG_ID = ?, SERVICE_LOCATION_ID = ?, SERVICE_INSTANCE_ID = ?"
                                      + " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (ORDER_TYPE_ID, ORDER_STATUS_ID, " + 
    								  "ORDER_DATE, PRODUCT_CATALOG_ID, SERVICE_LOCATION_ID, SERVICE_INSTANCE_ID) " +                                      
                                      "VALUES (?,?,?,?,?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_ORDER_TYPE_ID = 2;
    private static final int COLUMN_ORDER_STATUS_ID = 3;  
    private static final int COLUMN_ORDER_DATE = 4;  
    private static final int COLUMN_PRODUCT_CATALOG_ID = 5;  
    private static final int COLUMN_SERVICE_LOCATION_ID = 6;  
    private static final int COLUMN_SERVICE_INSTANCE_ID = 7;  
	public OracleServiceOrderDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceOrder.class, connection, daoFactory);
	}

	@Override
	public void update(ServiceOrder record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(1, record.getOrderTypeId());   
    	stmt.setInt(2, record.getOrderStatusId());  
    	stmt.setDate(3, (java.sql.Date)record.getOrderDate());  
    	stmt.setInt(4, record.getProductCatalogId()); 
    	stmt.setInt(5, record.getServiceLocationId());  
    	stmt.setInt(6, record.getServiceInstanceId());  
    	stmt.setLong(7, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public ServiceOrder insert(ServiceOrder record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setInt(1, record.getOrderTypeId());   
    	stmt.setInt(2, record.getOrderStatusId());  
    	stmt.setDate(3, (java.sql.Date)record.getOrderDate());  
    	stmt.setInt(4, record.getProductCatalogId()); 
    	stmt.setInt(5, record.getServiceLocationId());  
    	stmt.setInt(6, record.getServiceInstanceId());  
    	stmt.executeUpdate();
		return null;
	}

	@Override
	public int remove(ServiceOrder record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceOrder item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
		item.setOrderDate(rs.getDate(COLUMN_ORDER_DATE));
		item.setOrderStatusId(rs.getInt(COLUMN_ORDER_STATUS_ID));
		item.setOrderTypeId(rs.getInt(COLUMN_ORDER_TYPE_ID));
		item.setProductCatalogId(rs.getInt(COLUMN_PRODUCT_CATALOG_ID));
		item.setServiceInstanceId(rs.getInt(COLUMN_SERVICE_INSTANCE_ID));
		item.setServiceLocationId(rs.getInt(COLUMN_SERVICE_LOCATION_ID));
		
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
	public ArrayList<ServiceOrder> getServiceOrdersByUserId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}

}
