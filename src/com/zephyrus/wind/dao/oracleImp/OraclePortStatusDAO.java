package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;                     										  	
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.OracleTypes;
import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.model.PortStatus;

/**
 * OraclePortStatusDAO implementation of the IPortStatusDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OraclePortStatusDAO works with DB table PORT_STATUS and PortStatus class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 */
public class OraclePortStatusDAO extends OracleDAO<PortStatus> implements IPortStatusDAO {

	private static final String TABLE_NAME = "PORT_STATUS";
	private static final String SQL_SELECT = 
			"SELECT ID, PORT_STATUS_VALUE, ROWNUM AS ROW_NUM " + 
					"FROM " + TABLE_NAME + " ";                       
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET PORT_STATUS_VALUE = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(PORT_STATUS_VALUE) VALUES(?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class PortStatus
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OraclePortStatusDAO(Connection connection, OracleDAOFactory daoFactory) throws Exception {
		super(PortStatus.class, connection, daoFactory);
	}

	/**
	 * Method update PortStatus class instance using command update in the database.
	 * @param PortStatus class instance
	 */
	@Override
	public void update(PortStatus record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setString(1, record.getPortStatusValue());  	
		stmt.setLong(2, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant PortStatus class instance.
	 * @param inserting PortStatus class instance
	 * @return inserted PortStatus class instance
	 */
	@Override
	public PortStatus insert(PortStatus record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setString(1, record.getPortStatusValue());
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(2);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete PortStatus class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(PortStatus record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes PortStatus class instance and set values to its variables 
	 * @param class instance PortStatus
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(PortStatus item, ResultSet rs) throws SQLException,
	Exception {
		item.setId(rs.getInt(1));
		item.setPortStatusValue(rs.getString(2));

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


}
