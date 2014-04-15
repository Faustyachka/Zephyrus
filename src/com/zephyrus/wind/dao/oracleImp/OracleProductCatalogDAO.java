package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.model.ProductCatalog;

public class OracleProductCatalogDAO extends OracleDAO<ProductCatalog> implements IProductCatalogDAO {

	private static final String TABLE_NAME = "PRODUCT_CATALOG";
    private static final String SQL_SELECT = "SELECT ID, SERVICE_TYPE_ID, PROVIDER_LOC_ID, " + 
                                      "PRODUCT_NAME, PRICE FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_TYPE_ID = ?, PROVIDER_LOC_ID = ?, " + 
                                      "PRODUCT_NAME = ?, PRICE = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERVICE_TYPE_ID, PROVIDER_LOC_ID, " + 
                                      "PRODUCT_NAME, PRICE) " +
                                      "VALUES (?,?,?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERVICE_TYPE_ID = 2;
    private static final int COLUMN_PROVIDER_LOC_ID = 3;
    private static final int COLUMN_PRODUCT_NAME = 4;
    private static final int COLUMN_PRICE = 5;
    
	public OracleProductCatalogDAO(Connection connection) throws Exception {
		super(ProductCatalog.class, connection);
		  select = SQL_SELECT;
	        remove = SQL_REMOVE;
	}

	@Override
	public void update(ProductCatalog record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setBigDecimal(COLUMN_SERVICE_TYPE_ID, record.getServiceTypeId());
    	stmt.setBigDecimal(COLUMN_PROVIDER_LOC_ID, record.getProviderLocId());
    	stmt.setString(COLUMN_PRODUCT_NAME, record.getProductName());
    	stmt.setBigDecimal(COLUMN_PRICE, record.getPrice());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(ProductCatalog record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setBigDecimal(COLUMN_SERVICE_TYPE_ID, record.getServiceTypeId());
    	stmt.setBigDecimal(COLUMN_PROVIDER_LOC_ID, record.getProviderLocId());
    	stmt.setString(COLUMN_PRODUCT_NAME, record.getProductName());
    	stmt.setBigDecimal(COLUMN_PRICE, record.getPrice());
    	return stmt.executeUpdate();
	}

	@Override
	public int remove(ProductCatalog record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ProductCatalog item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setServiceTypeId(rs.getBigDecimal(COLUMN_SERVICE_TYPE_ID));
    	item.setProductName(rs.getString(COLUMN_PRODUCT_NAME));
    	item.setPrice(rs.getBigDecimal(COLUMN_PRICE));
    	item.setProviderLocId(rs.getBigDecimal(COLUMN_PROVIDER_LOC_ID));
    
		
	}

}
