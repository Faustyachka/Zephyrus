package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.User;

public class OracleServiceInstanceDAO extends OracleDAO<ServiceInstance> implements IServiceInstanceDAO {
	
	private static final String TABLE_NAME = "MISTERDAN.SERVICE_INSTANCES";
    private static final String SQL_SELECT = "SELECT ID, SERV_INSTANCE_STATUS_ID, USER_ID, " + 
    								  "PRODUCT_CATALOG_ID, CIRCUIT_ID, START_DATE " +
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERV_INSTANCE_STATUS_ID = ?, USER_ID = ?, " + 
                                      " PRODUCT_CATALOG_ID = ?, CIRCUIT_ID = ?, START_DATE = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (SERV_INSTANCE_STATUS_ID, USER_ID, " + 
    								  "PRODUCT_CATALOG_ID, CIRCUIT_ID, START_DATE) " +                                      
                                      "VALUES (?,?,?,?,?)" + " RETURN ROWID INTO ?;END;";
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
    	stmt.setInt(1, record.getServInstanceStatus().getId());   
    	stmt.setInt(2, record.getUser().getId());  
    	stmt.setInt(3, record.getProductCatalog().getId());  
    	if(record.getCircuit().getId() != null)
    		stmt.setInt(4, record.getCircuit().getId());  
    	else
    		stmt.setNull(4, java.sql.Types.INTEGER); 
    	stmt.setDate(5, (java.sql.Date)record.getStartDate());
    	stmt.setLong(6, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public ServiceInstance insert(ServiceInstance record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getServInstanceStatus().getId());   
    	cs.setInt(2, record.getUser().getId());  
    	cs.setInt(3, record.getProductCatalog().getId());  
    	if(record.getCircuit().getId() != null)
    		cs.setInt(4, record.getCircuit().getId());  
    	else
    		cs.setNull(4, java.sql.Types.INTEGER); 
    	cs.setDate(5, (java.sql.Date)record.getStartDate());
    	cs.registerOutParameter(6, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(6);
		return findByRowId(rowId);
	}

	@Override
	public int remove(ServiceInstance record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceInstance item, ResultSet rs)
			throws Exception {
		item.setId(rs.getInt(COLUMN_ID));
		Circuit circuit = daoFactory.getCircuitDAO().findById(rs.getInt(COLUMN_CIRCUIT_ID));
		item.setCircuit(circuit);
		ProductCatalog pc = daoFactory.getProductCatalogDAO().findById(rs.getInt(COLUMN_PRODUCT_CATALOG_ID));
		item.setProductCatalog(pc);
		ServiceInstanceStatus sis = daoFactory.getServiceInstanceStatusDAO().findById(rs.getInt(COLUMN_SERV_INSTANCE_STATUS_ID));
		item.setServInstanceStatus(sis);
		User user = daoFactory.getUserDAO().findById(rs.getInt(COLUMN_USER_ID));
		item.setUser(user);
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
	@Override
	public ArrayList<ServiceInstance> getServiceInstancesByUserId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}

}
