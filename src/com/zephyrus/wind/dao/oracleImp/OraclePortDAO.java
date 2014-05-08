package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
import com.zephyrus.wind.enums.PORT_STATUS;
import com.zephyrus.wind.model.Cable;
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.PortStatus;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceLocation;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.Task;

public class OraclePortDAO extends OracleDAO<Port> implements IPortDAO {

	private static final String TABLE_NAME = "PORTS";
	private static final String SQL_SELECT = "SELECT ID, DEVICE_ID, PORT_NUMBER, PORT_STATUS_ID, ROWNUM AS ROW_NUM " + 
													" FROM " + TABLE_NAME + " ";
	private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
													" SET DEVICE_ID = ?, PORT_NUMBER = ?, PORT_STATUS_ID = ? " + 
													" WHERE " + 
													" ID = ?";
	private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
											"(DEVICE_ID, PORT_NUMBER, PORT_STATUS_ID) VALUES(?,?,?)" +
											"RETURN ROWID INTO ?;END;";
	private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + " WHERE ";


	public OraclePortDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Port.class, connection, daoFactory);
	}

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

	@Override
	public int remove(Port record) throws Exception {
		return removeById((int)record.getId());
	}

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
	
	@Override
	protected String getSelect() {
		return SQL_SELECT;
	}

	@Override
	protected String getDelete() {
		return SQL_REMOVE;
	}
	

	/**
	 * Method for searching ID of fist free port (without cable)
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO.getCableDAO
	 * @return ID of first free port or 0 if it doesn't exist
	 * @author Miroshnychenko Nataliya
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
