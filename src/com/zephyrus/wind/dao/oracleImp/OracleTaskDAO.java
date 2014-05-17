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

/**
 * OracleTaskDAO implementation of the ITaskDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleTaskDAO works with DB table TASKS and Task class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleTaskDAO extends OracleDAO<Task> implements ITaskDAO {
	private static final String TABLE_NAME = "TASKS";
	private static final String SQL_SELECT = 
			"SELECT ID, SERVICE_ORDER_ID, USER_ID, TASK_STATUS_ID, ROLE_ID, ROWNUM AS ROW_NUM " +
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET SERVICE_ORDER_ID = ?, USER_ID = ?, TASK_STATUS_ID = ?, ROLE_ID = ? "
			+ "WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			" (SERVICE_ORDER_ID, " + 
			"USER_ID, TASK_STATUS_ID, ROLE_ID) " +                                      
			"VALUES (?,?,?,?) " + "RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
	 * Constructor return OracleDAO instance for class Task
	 * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
	 * @param connection connect to DB      
	 * @param OracleDAOFactory instance                                 
	 */
	public OracleTaskDAO(Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Task.class, connection, daoFactory);
	}

	/**
	 * Method update Task class instance using command update in the database.
	 * @param Task class instance
	 */
	@Override
	public void update(Task record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setInt(1, record.getServiceOrder().getId());
		if (record.getUser() == null){
			stmt.setNull(2, java.sql.Types.INTEGER); 
		} else {
			stmt.setInt(2, record.getUser().getId()); 
		}
		stmt.setInt(3, record.getTaskStatus().getId());  
		stmt.setInt(4, record.getRole().getId());
		stmt.setLong(5, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant Task class instance.
	 * @param inserting Task class instance
	 * @return inserted Task class instance
	 */
	@Override
	public Task insert(Task record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setInt(1, record.getServiceOrder().getId()); 
		if(record.getUser() == null){
			cs.setNull(2, java.sql.Types.INTEGER);
		} else {																			
			cs.setInt(2, record.getUser().getId());
		}
		cs.setInt(3, record.getTaskStatus().getId());  
		cs.setInt(4, record.getRole().getId());
		cs.registerOutParameter(5, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(5);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete Task class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(Task record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes Task class instance and set values to its variables 
	 * @param class instance Task
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(Task item, ResultSet rs) throws Exception {
		item.setId(rs.getInt(1));
		ServiceOrder so = daoFactory.getServiceOrderDAO().findById(rs.getInt(2));
		item.setServiceOrder(so);
		User user = daoFactory.getUserDAO().findById(rs.getInt(3));
		item.setUser(user);
		TaskStatus ts = daoFactory.getTaskStatusDAO().findById(rs.getInt(4));
		item.setTaskStatus(ts);
		UserRole role = daoFactory.getUserRoleDAO().findById(rs.getInt(5));
		item.setRole(role);
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
	 * Method allows to get tasks for the given user and task status.
	 * @param User user - user for which it is necessary to find out tasks in defined status 
	 * @param int taskStatusId - Task status ID defined status of task												
	 * @return list of tasks for the given user and task status
	 */
	@Override
	public ArrayList<Task> findTasksByUser(User user, int taskStatusId) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID=? AND TASK_STATUS_ID=?");
		stmt.setInt(1, user.getId());
		stmt.setInt(2, taskStatusId); 
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

	/**
	 * Method allows to get free tasks inside the definite UserRole.
	 * @param role user role for which it is necessary to find out free tasks
	 * @return list of available tasks 
	 */
	@Override
	public ArrayList<Task> findAvailableTasksByRole(UserRole role) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + 
				"WHERE TASK_STATUS_ID = ? AND ROLE_ID = ? AND USER_ID IS NULL");
		stmt.setInt(1, TASK_STATUS.PROCESSING.getId());         
		stmt.setInt(2, role.getId());					
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

}
