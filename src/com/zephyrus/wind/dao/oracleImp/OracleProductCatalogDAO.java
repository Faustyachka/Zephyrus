package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.model.ProductCatalog;

public class OracleProductCatalogDAO extends OracleDAO<ProductCatalog> implements IProductCatalogDAO {

	private static final String TABLE_NAME = "PRODUCT_CATALOG";
    private static final String SQL_SELECT = "SELECT ID, SERVICE_TYPE_ID, PROVIDER_LOC_ID, " + 
                                      " PRICE FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_TYPE_ID = ?, PROVIDER_LOC_ID = ?, " + 
                                      " PRICE = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERVICE_TYPE_ID, PROVIDER_LOC_ID, " + 
                                      " PRICE) " +
                                      "VALUES (?,?,?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERVICE_TYPE_ID = 2;
    private static final int COLUMN_PROVIDER_LOC_ID = 3;
    private static final int COLUMN_PRICE = 4;
    
	public OracleProductCatalogDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ProductCatalog.class, connection, daoFactory);
	}

	@Override
	public void update(ProductCatalog record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(COLUMN_SERVICE_TYPE_ID, record.getServiceTypeId());
    	stmt.setInt(COLUMN_PROVIDER_LOC_ID, record.getProviderLocId());
    	stmt.setInt(COLUMN_PRICE, record.getPrice());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public ProductCatalog insert(ProductCatalog record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setInt(COLUMN_SERVICE_TYPE_ID, record.getServiceTypeId());
    	stmt.setInt(COLUMN_PROVIDER_LOC_ID, record.getProviderLocId());
    	stmt.setInt(COLUMN_PRICE, record.getPrice());
    	stmt.executeUpdate();
    	return null;
	}

	@Override
	public int remove(ProductCatalog record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ProductCatalog item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setServiceTypeId(rs.getInt(COLUMN_SERVICE_TYPE_ID));
    	item.setPrice(rs.getInt(COLUMN_PRICE));
    	item.setProviderLocId(rs.getInt(COLUMN_PROVIDER_LOC_ID));
    
		
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
