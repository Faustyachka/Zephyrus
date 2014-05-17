package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;																			
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleTypes;
import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceType;

/**
 * OracleProductCatalogDAO implementation of the IProductCatalogDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleProductCatalogDAO works with DB table PRODUCT_CATALOG and ProductCatalog class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleProductCatalogDAO extends OracleDAO<ProductCatalog> implements IProductCatalogDAO {

	private static final String TABLE_NAME = "PRODUCT_CATALOG";
	private static final String SQL_SELECT = 
			"SELECT ID, SERVICE_TYPE_ID, PROVIDER_LOC_ID, PRICE, ROWNUM AS ROW_NUM "
					+ " FROM " +  TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET SERVICE_TYPE_ID = ?, PROVIDER_LOC_ID = ?, PRICE = ? "
			+ " WHERE ID = ?";
	private static final String SQL_INSERT =  
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(SERVICE_TYPE_ID, PROVIDER_LOC_ID, PRICE) VALUES(?,?,?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class ProductCatalog
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleProductCatalogDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ProductCatalog.class, connection, daoFactory);
	}

	/**
	 * Method update ProductCatalog class instance using command update in the database.
	 * @param ProductCatalog class instance
	 */
	@Override
	public void update(ProductCatalog record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setInt(1, record.getServiceType().getId());
		stmt.setInt(2, record.getProviderLoc().getId());
		stmt.setInt(3, record.getPrice());
		stmt.setLong(4, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant ProductCatalog class instance.
	 * @param inserting ProductCatalog class instance
	 * @return inserted ProductCatalog class instance
	 */
	@Override
	public ProductCatalog insert(ProductCatalog record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setInt(1, record.getServiceType().getId());
		cs.setInt(2, record.getProviderLoc().getId());  
		cs.setInt(3, record.getPrice());  
		cs.registerOutParameter(4, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(4);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete ProductCatalog class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(ProductCatalog record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes ProductCatalog class instance and set values to its variables 
	 * @param class instance ProductCatalog
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(ProductCatalog item, ResultSet rs)
			throws SQLException, Exception {
		item.setId(rs.getInt(1));
		IServiceTypeDAO serviceTypeDAO = daoFactory.getServiceTypeDAO();
		ServiceType serviceType = serviceTypeDAO.findById(rs.getInt(2));
		item.setServiceType(serviceType);
		item.setPrice(rs.getInt(4));
		IProviderLocationDAO providerLocationDAO = daoFactory.getProviderLocationDAO();
		ProviderLocation providerLoc = providerLocationDAO.findById(rs.getInt(3));
		item.setProviderLoc(providerLoc);

	}

	/**
   	 * Method gives text of SQL select query
   	 *@return text of select query
   	 */
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	/**
   	 * Method gives text of SQL delete query
   	 *@return text of delete query
   	 */
	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

	/**
	 * Method allows to get products for given Provider Location.
	 * @param Provider Location for which it is necessary to find products
	 * @return list of products from Product Catalog
	 */
	@Override
	public ArrayList<ProductCatalog> getProductsByProviderLocation(ProviderLocation providerLocation) 
			throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + " WHERE PROVIDER_LOC_ID = ? ");
		stmt.setInt(1, providerLocation.getId()); 
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

}
