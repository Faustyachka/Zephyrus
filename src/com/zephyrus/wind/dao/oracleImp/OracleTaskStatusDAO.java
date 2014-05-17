package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.model.TaskStatus;

/**
 * OracleTaskStatusDAO implementation of the ITaskStatusDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OracleTaskStatusDAO works with DB table TASK_STATUS and TaskStatus class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OracleTaskStatusDAO extends OracleDAO<TaskStatus> implements ITaskStatusDAO {
	private static final String TABLE_NAME = "TASK_STATUS";
	private static final String SQL_SELECT = 
			"SELECT ID, TASK_STATUS_VALUE, ROWNUM AS ROW_NUM " + 			
					"FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
			" SET TASK_STATUS_VALUE = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			" (TASK_STATUS_VALUE) " +                                      
			"VALUES (?)" + " RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";


	/**
     * Constructor return OracleDAO instance for class TaskStatus
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OracleTaskStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(TaskStatus.class, connection, daoFactory);
	}

	/**
	 * Method update TaskStatus class instance using command update in the database.
	 * @param TaskStatus class instance
	 */
	@Override
	public void update(TaskStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getTaskStatusValue());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();	
		stmt.close();
	}

	/**
	 * Method create new record and compliant TaskStatus class instance.
	 * @param inserting TaskStatus class instance
	 * @return inserted TaskStatus class instance
	 */
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

	/**
	 * Method delete TaskStatus class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(TaskStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes TaskStatus class instance and set values to its variables 
	 * @param class instance TaskStatus
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(TaskStatus item, ResultSet rs) throws SQLException {
		item.setId(rs.getInt(1));
		item.setTaskStatusValue(rs.getString(2));

	}

	/**
   	 * Method gives text of SQL select query from table TASK_STATUS
   	 *@return text of select query
   	 */
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	/**
   	 * Method gives text of SQL delete query for table TASK_STATUS
   	 *@return text of delete query
   	 */
	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}

}
