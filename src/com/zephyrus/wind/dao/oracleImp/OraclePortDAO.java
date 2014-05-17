package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;                     	
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.enums.PORT_STATUS;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.PortStatus;

/**
 * OraclePortDAO implementation of the IPortDAO interface and extend OracleDAO. 
 * This class can contain all SQL statements and methods using SQL-code. 
 * OraclePortDAO works with DB table PORTS and Port class instance.
 * @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
 *
 */
public class OraclePortDAO extends OracleDAO<Port> implements IPortDAO {

	private static final String TABLE_NAME = "PORTS";
	private static final String SQL_SELECT = 
			"SELECT ID, DEVICE_ID, PORT_NUMBER, PORT_STATUS_ID, ROWNUM AS ROW_NUM " + 
					" FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = 
			"UPDATE " + TABLE_NAME + 
			" SET DEVICE_ID = ?, PORT_NUMBER = ?, PORT_STATUS_ID = ? " + 
			" WHERE ID = ?";
	private static final String SQL_INSERT = 
			"BEGIN INSERT INTO " + TABLE_NAME + 
			"(DEVICE_ID, PORT_NUMBER, PORT_STATUS_ID) VALUES(?,?,?)" +
			"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = 
			"DELETE FROM " + TABLE_NAME + " WHERE ";

	/**
     * Constructor return OracleDAO instance for class Port
     * @see com.zephyrus.wind.dao.oracleImp.OracleDAO
     * @param connection connect to DB      
     * @param OracleDAOFactory instance                                 
     */
	public OraclePortDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Port.class, connection, daoFactory);
	}

	/**
	 * Method update Port class instance using command update in the database.
	 * @param Port class instance
	 */
	@Override
	public void update(Port record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
		stmt.setInt(1, record.getDevice().getId());
		stmt.setInt(2, record.getPortNumber()); 
		stmt.setInt(3, record.getPortStatus().getId());
		stmt.setLong(4, record.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Method create new record and compliant Port class instance.
	 * @param inserting Port class instance 
	 * @return inserted Port class instance
	 */
	@Override
	public Port insert(Port record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
		cs.setInt(1, record.getDevice().getId());
		cs.setInt(2, record.getPortNumber());    
		cs.setInt(3, record.getPortStatus().getId());
		cs.registerOutParameter(4, OracleTypes.VARCHAR);
		cs.execute();
		String rowId = cs.getString(4);
		cs.close();
		return findByRowId(rowId);
	}

	/**
	 * Method delete Port class instance record from the database.
	 * @param compliant class instance
	 * @return count of removed rows
	 */
	@Override
	public int remove(Port record) throws Exception {
		return removeById((int)record.getId());
	}

	/**
	 * Method takes Port class instance and set values to its variables 
	 * @param class instance Port
	 * @param ResultSet a database result set represented by DB
	 */
	@Override
	protected void fillItem(Port item, ResultSet rs) throws SQLException, Exception {
		item.setId(rs.getInt(1));
		IDeviceDAO deviceDAO = daoFactory.getDeviceDAO();
		Device device = deviceDAO.findById(rs.getInt(2));
		item.setDevice(device);
		item.setPortNumber(rs.getInt(3));
		IPortStatusDAO portStatusDAO = daoFactory.getPortStatusDAO();
		PortStatus portStatus = portStatusDAO.findById(rs.getInt(4));
		item.setPortStatus(portStatus);
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
	 * Method for searching ID of fist free port (without cable)
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO.getCableDAO
	 * @return ID of first free port or 0 if it doesn't exist
	 */
	@Override
	public int findFreePortID() throws Exception {
		int freePortId = 0;
		stmt = connection.prepareStatement("SELECT ID FROM " + 
				TABLE_NAME 
				+ " WHERE ROWNUM = 1 AND  "
				+ " PORT_STATUS_ID = ? "
				+ " ORDER BY DEVICE_ID, PORT_NUMBER");
		stmt.setInt(1, PORT_STATUS.FREE.getId());
		rs = stmt.executeQuery();	
		if (rs.next()) {
			freePortId = rs.getInt(1);
		}
		rs.close();
		return freePortId;
	}
}
