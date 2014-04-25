package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.enums.TASK_STATUS;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;
import com.zephyrus.wind.model.TaskStatus;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.UserRole;

public class OracleTaskDAO extends OracleDAO<Task> implements ITaskDAO {
	private static final String TABLE_NAME = "MISTERDAN.TASKS";
    private static final String SQL_SELECT = "SELECT ID, SERVICE_ORDER_ID, TASK_VALUE, " + 
    								  "USER_ID, TASK_STATUS_ID, ROLE_ID" +
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET SERVICE_ORDER_ID = ?, TASK_VALUE = ?, " + 
                                      " USER_ID = ?, TASK_STATUS_ID = ?, ROLE_ID = ? WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
                                      " (SERVICE_ORDER_ID, TASK_VALUE, " + 
    								  "USER_ID, TASK_STATUS_ID, ROLE_ID) " +                                      
                                      "VALUES (?,?,?,?,?) " + "RETURN ROWID INTO ?;END;";
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
    	stmt.setInt(COLUMN_SERVICE_ORDER_ID, record.getServiceOrder().getId());   
    	stmt.setString(COLUMN_TASK_VALUE, record.getTaskValue());  
    	stmt.setInt(COLUMN_USER_ID, record.getUser().getId());  
    	stmt.setInt(COLUMN_TASK_STATUS_ID, record.getTaskStatus().getId());  
    	stmt.setInt(COLUMN_ROLE_ID, record.getRole().getId());
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
		
	}

	@Override
	public Task insert(Task record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getServiceOrder().getId());   
    	cs.setString(2, record.getTaskValue());  
    	cs.setInt(3, record.getUser().getId());  
    	cs.setInt(4, record.getTaskStatus().getId());  
    	cs.setInt(5, record.getRole().getId());
    	cs.registerOutParameter(5, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(5);
		return findByRowId(rowId);
	}

	@Override
	public int remove(Task record) throws Exception {
		return removeById((int)record.getId());
	}

	@Override
	protected void fillItem(Task item, ResultSet rs) throws Exception {
		item.setId(rs.getInt(COLUMN_ID));
		UserRole role = daoFactory.getUserRoleDAO().findById(rs.getInt(COLUMN_ROLE_ID));
		item.setRole(role);
		ServiceOrder so = daoFactory.getServiceOrderDAO().findById(rs.getInt(COLUMN_SERVICE_ORDER_ID));
		item.setServiceOrder(so);
		TaskStatus ts = daoFactory.getTaskStatusDAO().findById(rs.getInt(COLUMN_TASK_STATUS_ID));
		item.setTaskStatus(ts);
		item.setTaskValue(rs.getString(COLUMN_TASK_VALUE));
		User user = daoFactory.getUserDAO().findById(rs.getInt(COLUMN_USER_ID));
		item.setUser(user);
		
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
	public ArrayList<Task> findActualTasksByUser(User user) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ? AND TASK_STATUS_ID = ?");
		stmt.setInt(1, user.getId());
		stmt.setInt(2, TASK_STATUS.PROCESSING.getId());
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}

	@Override
	public ArrayList<Task> findAvailableTasksByRole(UserRole role) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE TASK_STATUS_ID = ? AND ROLE_ID = ?");
		stmt.setInt(1, TASK_STATUS.SUSPEND.getId());
		stmt.setInt(2, role.getId());
		rs = stmt.executeQuery();		
		return fetchMultiResults(rs);
	}

}
