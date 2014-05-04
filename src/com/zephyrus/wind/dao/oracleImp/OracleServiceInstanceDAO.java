package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.enums.SERVICEINSTANCE_STATUS;
import com.zephyrus.wind.model.Circuit;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.User;

public class OracleServiceInstanceDAO extends OracleDAO<ServiceInstance> implements IServiceInstanceDAO {
	
	private static final String TABLE_NAME = "SERVICE_INSTANCES";
    private static final String SQL_SELECT = "SELECT ID, SERV_INSTANCE_STATUS_ID, USER_ID, " + 
    								  "PRODUCT_CATALOG_ID, CIRCUIT_ID, START_DATE, ROWNUM AS ROW_NUM " +
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
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";
    

	public OracleServiceInstanceDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceInstance.class, connection, daoFactory);
	}

	@Override
	public void update(ServiceInstance record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(1, record.getServInstanceStatus().getId());   
    	stmt.setInt(2, record.getUser().getId());  
    	stmt.setInt(3, record.getProductCatalog().getId());  
    	if(record.getCircuit() == null){
    		stmt.setNull(4, java.sql.Types.INTEGER);
    		} 
    	else {
    		stmt.setInt(4, record.getCircuit().getId()); 
    		}
    	stmt.setDate(5, (java.sql.Date)record.getStartDate());
    	stmt.setLong(6, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public ServiceInstance insert(ServiceInstance record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getServInstanceStatus().getId());   
    	cs.setInt(2, record.getUser().getId());  
    	cs.setInt(3, record.getProductCatalog().getId());  
    	if(record.getCircuit() == null)
    		cs.setNull(4, java.sql.Types.INTEGER);
    	else
    		cs.setInt(4, record.getCircuit().getId());  
    	cs.setDate(5, (java.sql.Date)record.getStartDate());
    	cs.registerOutParameter(6, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(6);
        cs.close();
		return findByRowId(rowId);
	}

	@Override
	public int remove(ServiceInstance record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceInstance item, ResultSet rs)
			throws Exception {
		item.setId(rs.getInt(1));
		ServiceInstanceStatus sis = daoFactory.getServiceInstanceStatusDAO().findById(rs.getInt(2));
		item.setServInstanceStatus(sis);
		User user = daoFactory.getUserDAO().findById(rs.getInt(3));
		item.setUser(user);
		ProductCatalog pc = daoFactory.getProductCatalogDAO().findById(rs.getInt(4));
		item.setProductCatalog(pc);
		Circuit circuit = daoFactory.getCircuitDAO().findById(rs.getInt(5));
		item.setCircuit(circuit);
		item.setStartDate(rs.getDate(6));
		
	}
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}
	
	/**
	 * Method finds Service Instances by User ID
	 * 
	 * @param User ID
	 * @return collection of existing Service Instances
	 * @author unknown
	 */
	
	@Override
	public ArrayList<ServiceInstance> getServiceInstancesByUserId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

	/**
	 * Method finds Service Instances by User in all status expect DISCONNECTED
	 * 
	 * @param User
	 * @return ArrayList of existing Service Instances in all status expect DISCONNECTED
	 * @author Miroshnychenko Nataliya
	 */
	@Override
	public ArrayList<ServiceInstance> getActiveServiceInstancesByUser(User user) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ? AND SERV_INSTANCE_STATUS_ID != ?");
		stmt.setInt(1, user.getId());
		stmt.setInt(2, SERVICEINSTANCE_STATUS.DISCONNECTED.getId());
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

}
