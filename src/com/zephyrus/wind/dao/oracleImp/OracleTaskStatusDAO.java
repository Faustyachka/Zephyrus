package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

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
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (TASK_STATUS_VALUE) " +                                      
                                      "VALUES (?)" + " RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_TASK_STATUS_VALUE = 2;

	public OracleTaskStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(TaskStatus.class, connection, daoFactory);
	}

	@Override
	public void update(TaskStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setString(1, record.getTaskStatusValue());  	
    	stmt.setLong(2, record.getId());
        stmt.executeUpdate();	
        stmt.close();
	}

	@Override
	public TaskStatus insert(TaskStatus record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setString(1, record.getTaskStatusValue());    	
    	cs.registerOutParameter(2, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(2);
        cs.close();
		return findByRowId(rowId);
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
