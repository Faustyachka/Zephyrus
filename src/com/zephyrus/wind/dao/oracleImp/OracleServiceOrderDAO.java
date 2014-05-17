package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;


import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;

																										//REVIEW: documentation expected
public class OracleServiceOrderDAO extends OracleDAO<ServiceOrder> implements IServiceOrderDAO {        //REVIEW: long line

	private static final String TABLE_NAME = "SERVICE_ORDERS";
	private static final String SQL_SELECT = "SELECT ID, ORDER_TYPE_ID, ORDER_STATUS_ID, " + 			//REVIEW: wrong formatting
			"ORDER_DATE, PRODUCT_CATALOG_ID, SERVICE_LOCATION_ID, " +
			"SERVICE_INSTANCE_ID, ROWNUM AS ROW_NUM " +
			"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
			" SET ORDER_TYPE_ID = ?, ORDER_STATUS_ID = ?, ORDER_DATE = ?, "
			+ " PRODUCT_CATALOG_ID = ?, SERVICE_LOCATION_ID = ?, SERVICE_INSTANCE_ID = ?"
			+ " WHERE  ID = ?";
	private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
			" (ORDER_TYPE_ID, ORDER_STATUS_ID, ORDER_DATE, PRODUCT_CATALOG_ID, SERVICE_LOCATION_ID, SERVICE_INSTANCE_ID) " +                                      
			"VALUES (?,?,?,?,?,?)" + " RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";
    
	public OracleServiceOrderDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(ServiceOrder.class, connection, daoFactory);
	}
																										//REVIEW: documentation expected
	@Override
	public void update(ServiceOrder record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(1, record.getOrderType().getId());   
    	stmt.setInt(2, record.getOrderStatus().getId());  
    	stmt.setDate(3, (java.sql.Date)record.getOrderDate());  
    	stmt.setInt(4, record.getProductCatalog().getId()); 
    	stmt.setInt(5, record.getServiceLocation().getId());  
    	if (record.getServiceInstance() == null){
    		stmt.setNull(6, java.sql.Types.INTEGER); 
    	} else {	
    		stmt.setInt(6, record.getServiceInstance().getId());
    	}
    	stmt.setLong(7, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}
																									//REVIEW: documentation expected
	@Override
	public ServiceOrder insert(ServiceOrder record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getOrderType().getId());   
    	cs.setInt(2, record.getOrderStatus().getId());  
    	cs.setDate(3, (java.sql.Date)record.getOrderDate());  
    	cs.setInt(4, record.getProductCatalog().getId()); 
    	cs.setInt(5, record.getServiceLocation().getId()); 
    	if(record.getServiceInstance().getId() != null)
    		cs.setInt(6, record.getServiceInstance().getId());  
    	else																						//REVIEW: braces expected
    		cs.setNull(6, java.sql.Types.INTEGER);  
    	cs.registerOutParameter(7, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(7);
        cs.close();
		return findByRowId(rowId);
	}
																									//REVIEW: documentation expected
	@Override
	public int remove(ServiceOrder record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceOrder item, ResultSet rs)
			throws Exception {
		item.setId(rs.getInt(1));
		OrderType ot = daoFactory.getOrderTypeDAO().findById(rs.getInt(2));
		item.setOrderType(ot);
		OrderStatus os = daoFactory.getOrderStatusDAO().findById(rs.getInt(3));
		item.setOrderStatus(os);
		item.setOrderDate(rs.getDate(4));
		ProductCatalog pc = daoFactory.getProductCatalogDAO().findById(rs.getInt(5));
		item.setProductCatalog(pc);
		ServiceLocation sl = daoFactory.getServiceLocationDAO().findById(rs.getInt(6));
		item.setServiceLocation(sl);
		ServiceInstance si = daoFactory.getServiceInstanceDAO().findById(rs.getInt(7));
		item.setServiceInstance(si);
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
	 * Method finds Service Orders object for Service Instance ID
	 * 
	 * @param Service Instance ID														//REVIEW: parameter name expected
	 * @return collection of Service Orders												//REVIEW: may be better write "List of orders for given Service Instance?
	 */
																						//REVIEW: extra line
	@Override
	public ArrayList<ServiceOrder> getServiceOrdersByServiceInstanceId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE SERVICE_INSTANCE_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		return fetchMultiResults(rs);
	}
	
	/**
	 * Method finds creation Service Orders object for Service Instance
	 * 
	 * @param Service Instance															//REVIEW: see previous comments
	 * @return Service Orders on which basis the SI create 
	 */
	
	@Override
	public ServiceOrder getSICreateOrder(ServiceInstance serviceInstance) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE SERVICE_INSTANCE_ID = ? AND ORDER_TYPE_ID = ? ");
		stmt.setInt(1, serviceInstance.getId());
		stmt.setInt(2, ORDER_TYPE.NEW.getId());
		rs = stmt.executeQuery();
		return fetchSingleResult(rs);
	}
	
	/**
	 * Method finds Service Orders object for Service Location ID
	 * 
	 * @param Service Location ID														//REVIEW: parameter name expected
	 * @return collection of Service Orders												//REVIEW: see previous comment
	 * @author unknown																	//REVIEW: author shouldn't be here
	 */
																						//REVIEW: extra line
	@Override
	public ArrayList<ServiceOrder> getServiceOrdersByServiceLocationId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE SERVICE_LOCATION_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}
	
	
	
	/**
	 * Method finds disconnected Service Orders object for period
	 * 
	 * @param Date startDate start of period
	 * @param Date endDate end of period
	 * @return collection of disconnected Service Orders
	 */
	@Override																					//REVIEW: is this method used anywhere?
	//Returns orders for reports by period
	public ArrayList<ServiceOrder> getDisconnectSOByPeriod(Date startDate, Date endDate) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE (ORDER_DATE BETWEEN ? AND ?) "
				+ "AND ORDER_STATUS_ID="+ORDER_TYPE.DISCONNECT.getId());
		stmt.setDate(1, startDate);
		stmt.setDate(2, endDate);
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}
	
	/**
	 * Method finds new Service Orders object for period
	 * 
	 * @param Date startDate start of period
	 * @param Date endDate end of period
	 * @return collection of new Service Orders
	 */
	@Override																							//REVIEW: is this method used anywhere?
	public ArrayList<ServiceOrder> getNewSOByPeriod(Date startDate, Date endDate) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE (ORDER_DATE BETWEEN ? AND ?) "
				+ "AND ORDER_STATUS_ID="+ORDER_TYPE.NEW.getId());
		stmt.setDate(1, startDate);
		stmt.setDate(2, endDate);
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}
	
	/**
	 * Method finds Service Orders for user
	 * 
	 * @param User													//REVIEW: description expected
	 * @return collection of Service Orders 
	 */
	public ArrayList<ServiceOrder> getOrdersByUser(User user) throws Exception{
		
		IServiceLocationDAO locationDAO = daoFactory.getServiceLocationDAO();
		ArrayList<ServiceLocation> locations = locationDAO.getServiceLocationsByUserId(user.getId());
		
		int size = locations.size();							//REVIEW: there is no need to create variable for arraylist size
		
		if (size == 0){											//REVIEW: isEmpty() method should be used
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		for( int i = 0 ; i < size; i++ ) {
		    builder.append("?,");
		}
																						//REVIEW: comments needed
		stmt = connection.prepareStatement(SQL_SELECT + 
				" WHERE SERVICE_LOCATION_ID IN (" + builder.deleteCharAt( builder.length() -1).toString() + ")");
		
		int index = 1;
		for(ServiceLocation serviceLocation : locations) {
		   stmt.setInt(index++, serviceLocation.getId()); 
		}
		
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	};																					//REVIEW: semicolon?
	
	

}
