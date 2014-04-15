package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.model.Task;

public class OracleTaskDAO extends OracleDAO<Task> implements ITaskDAO {
	private static final String TABLE_NAME = "TASKS";
    private static final String SQL_SELECT = "SELECT ID, SERVICE_ORDER_ID, TASK_VALUE, " + 
    								  "USER_ID, TASK_STATUS_ID, ROLE_ID" +
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_ORDER_ID = ?, TASK_VALUE = ?, " + 
                                      " USER_ID = ?, TASK_STATUS_ID = ?, ROLE_ID = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (SERVICE_ORDER_ID, TASK_VALUE, " + 
    								  "USER_ID, TASK_STATUS_ID, ROLE_ID) " +                                      
                                      "VALUES (?,?,?,?,?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_SERVICE_ORDER_ID = 2;
    private static final int COLUMN_TASK_VALUE = 3;  
    private static final int COLUMN_USER_ID = 4;  
    private static final int COLUMN_TASK_STATUS_ID = 5;  
    private static final int COLUMN_ROLE_ID = 6;  

	public OracleTaskDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Task.class, connection, daoFactory);
	}

	@Override
	public void update(Task record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setBigDecimal(COLUMN_SERVICE_ORDER_ID, record.getServiceOrderId());   
    	stmt.setString(COLUMN_TASK_VALUE, record.getTaskValue());  
    	stmt.setBigDecimal(COLUMN_USER_ID, record.getUserId());  
    	stmt.setBigDecimal(COLUMN_TASK_STATUS_ID, record.getTaskStatusId());  
    	stmt.setBigDecimal(COLUMN_ROLE_ID, record.getRoleId());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public int insert(Task record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setBigDecimal(COLUMN_SERVICE_ORDER_ID, record.getServiceOrderId());   
    	stmt.setString(COLUMN_TASK_VALUE, record.getTaskValue());  
    	stmt.setBigDecimal(COLUMN_USER_ID, record.getUserId());  
    	stmt.setBigDecimal(COLUMN_TASK_STATUS_ID, record.getTaskStatusId());  
    	stmt.setBigDecimal(COLUMN_ROLE_ID, record.getRoleId());
		return stmt.executeUpdate();
	}

	@Override
	public int remove(Task record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Task item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
		item.setRoleId(rs.getBigDecimal(COLUMN_ROLE_ID));
		item.setServiceOrderId(rs.getBigDecimal(COLUMN_SERVICE_ORDER_ID));
		item.setTaskStatusId(rs.getBigDecimal(COLUMN_TASK_STATUS_ID));
		item.setTaskValue(rs.getString(COLUMN_TASK_VALUE));
		item.setUserId(rs.getBigDecimal(COLUMN_USER_ID));
		
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
