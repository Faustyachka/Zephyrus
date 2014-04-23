package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.model.TaskStatus;

public class OracleTaskStatusDAO extends OracleDAO<TaskStatus> implements ITaskStatusDAO {
	private static final String TABLE_NAME = "TASK_STATUS";
    private static final String SQL_SELECT = "SELECT ID, TASK_STATUS_VALUE " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET TASK_STATUS_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (TASK_STATUS_VALUE) " +                                      
                                      "VALUES (?)";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_TASK_STATUS_VALUE = 2;

	public OracleTaskStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(TaskStatus.class, connection, daoFactory);
	}

	@Override
	public void update(TaskStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_TASK_STATUS_VALUE, record.getTaskStatusValue());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();	
		
	}

	@Override
	public TaskStatus insert(TaskStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_TASK_STATUS_VALUE, record.getTaskStatusValue());    	
        stmt.executeUpdate();		
		return null;
	}

	@Override
	public int remove(TaskStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(TaskStatus item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(COLUMN_ID));
    	item.setTaskStatusValue(rs.getString(COLUMN_TASK_STATUS_VALUE));
		
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
