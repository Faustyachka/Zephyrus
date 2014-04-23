package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.model.ServiceInstanceStatus;

public class OracleServiceInstanceStatusDAO extends OracleDAO<ServiceInstanceStatus> implements IServiceInstanceStatusDAO {
	
	private static final String TABLE_NAME = "SERVICE_INSTANCE_STATUS";
    private static final String SQL_SELECT = "SELECT ID, SERV_INSTANCE_STATUS_VALUE " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERV_INSTANCE_STATUS_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERV_INSTANCE_STATUS_VALUE) " + 
                                      
                                      "VALUES (?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERV_INSTANCE_STATUS_VALUE = 2;


	public OracleServiceInstanceStatusDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(ServiceInstanceStatus.class, connection, daoFactory);
	}

	@Override
	public void update(ServiceInstanceStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_SERV_INSTANCE_STATUS_VALUE, record.getServInstanceStatusValue());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public ServiceInstanceStatus insert(ServiceInstanceStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_SERV_INSTANCE_STATUS_VALUE, record.getServInstanceStatusValue());    	
        stmt.executeUpdate();		
		return null;
	}

	@Override
	public int remove(ServiceInstanceStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(ServiceInstanceStatus item, ResultSet rs)
			throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setServInstanceStatusValue(rs.getString(COLUMN_SERV_INSTANCE_STATUS_VALUE));
		
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
