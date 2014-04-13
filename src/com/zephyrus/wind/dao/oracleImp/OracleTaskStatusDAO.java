package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.model.TaskStatus;

public class OracleTaskStatusDAO extends OracleDAO<TaskStatus> implements ITaskStatusDAO {
	private final String TABLE_NAME = "TASK_STATUS";
    private final String SQL_SELECT = "SELECT ID, TASK_STATUS_VALUE " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET TASK_STATUS_VALUE = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
                                      " (TASK_STATUS_VALUE) " +                                      
                                      "VALUES (?)";
    private final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_TASK_STATUS_VALUE = 2;

	public OracleTaskStatusDAO(Connection connection) throws Exception {
		super(TaskStatus.class, connection);
		select = SQL_SELECT;
        remove = SQL_REMOVE;
	}

	@Override
	public void update(TaskStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(COLUMN_TASK_STATUS_VALUE, record.getTaskStatusValue());  	
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();	
		
	}

	@Override
	public int insert(TaskStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_INSERT);
    	stmt.setString(COLUMN_TASK_STATUS_VALUE, record.getTaskStatusValue());    	
        stmt.executeUpdate();		
		return stmt.executeUpdate();
	}

	@Override
	public int remove(TaskStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(TaskStatus item, ResultSet rs) throws SQLException {
		item.setId(rs.getLong(COLUMN_ID));
    	item.setTaskStatusValue(rs.getString(COLUMN_TASK_STATUS_VALUE));
		
	}

}
