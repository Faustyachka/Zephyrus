package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;


import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;


public class OracleServiceOrderDAO extends OracleDAO<ServiceOrder> implements IServiceOrderDAO {

	private static final String TABLE_NAME = "MISTERDAN.SERVICE_ORDERS";
    private static final String SQL_SELECT = "SELECT ID, ORDER_TYPE_ID, ORDER_STATUS_ID, " + 
    								  "ORDER_DATE, PRODUCT_CATALOG_ID, SERVICE_LOCATION_ID, SERVICE_INSTANCE_ID" +
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET ORDER_TYPE_ID = ?, ORDER_STATUS_ID = ?, " + 
                                      " ORDER_DATE = ?, PRODUCT_CATALOG_ID = ?, SERVICE_LOCATION_ID = ?, SERVICE_INSTANCE_ID = ?"
                                      + " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (ORDER_TYPE_ID, ORDER_STATUS_ID, " + 
    								  "ORDER_DATE, PRODUCT_CATALOG_ID, SERVICE_LOCATION_ID, SERVICE_INSTANCE_ID) " +                                      
                                      "VALUES (?,?,?,?,?,?)" + " RETURN ROWID INTO ?;END;";
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
    	stmt.setInt(1, record.getOrderType().getId());   
    	stmt.setInt(2, record.getOrderStatus().getId());  
    	stmt.setDate(3, (java.sql.Date)record.getOrderDate());  
    	stmt.setInt(4, record.getProductCatalog().getId()); 
    	stmt.setInt(5, record.getServiceLocation().getId());  
    	stmt.setInt(6, record.getServiceInstance().getId());  
    	stmt.setLong(7, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public ServiceOrder insert(ServiceOrder record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getOrderType().getId());   
    	cs.setInt(2, record.getOrderStatus().getId());  
    	cs.setDate(3, (java.sql.Date)record.getOrderDate());  
    	cs.setInt(4, record.getProductCatalog().getId()); 
    	cs.setInt(5, record.getServiceLocation().getId());  
    	cs.setInt(6, record.getServiceInstance().getId());  
    	cs.registerOutParameter(7, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(7);
		return findByRowId(rowId);
	}

	@Override
	public int remove(ServiceOrder record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceOrder item, ResultSet rs)
			throws Exception {
		item.setId(rs.getInt(COLUMN_ID));
		item.setOrderDate(rs.getDate(COLUMN_ORDER_DATE));
		OrderStatus os = daoFactory.getOrderStatusDAO().findById(rs.getInt(COLUMN_ORDER_STATUS_ID));
		item.setOrderStatus(os);
		OrderType ot = daoFactory.getOrderTypeDAO().findById(rs.getInt(COLUMN_ORDER_TYPE_ID));
		item.setOrderType(ot);
		ProductCatalog pc = daoFactory.getProductCatalogDAO().findById(rs.getInt(COLUMN_PRODUCT_CATALOG_ID));
		item.setProductCatalog(pc);
		ServiceInstance si = daoFactory.getServiceInstanceDAO().findById(rs.getInt(COLUMN_SERVICE_INSTANCE_ID));
		item.setServiceInstance(si);
		ServiceLocation sl = daoFactory.getServiceLocationDAO().findById(rs.getInt(COLUMN_SERVICE_LOCATION_ID));
		item.setServiceLocation(sl);
		
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
