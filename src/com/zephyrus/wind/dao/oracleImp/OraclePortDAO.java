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
    private static final String SQL_SELECT = "SELECT ID, DEVICE_ID, PORT_NUMBER, PORT_STATUS_ID " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET DEVICE_ID = ?, PORT_NUMBER = ?, PORT_STATUS_ID = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
												"(DEVICE_ID, PORT_NUMBER, PORT_STATUS_ID) VALUES(?,?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_DEVICE_ID = 2;
    private static final int COLUMN_PORT_NUMBER = 3; 
    private static final int PORT_STATUS_ID = 4;

	public OraclePortDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Port.class, connection, daoFactory);
	}

	@Override
	public void update(Port record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(1, record.getDevice().getId());
    	stmt.setInt(2, record.getPortNumber()); 
    	stmt.setLong(3, record.getId());
    	stmt.setInt(4, record.getPortStatusID().getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public Port insert(Port record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getDevice().getId());
    	cs.setInt(2, record.getPortNumber());    
    	cs.setInt(3, record.getPortStatusID().getId());
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
		item.setId(rs.getInt(COLUMN_ID));
		IDeviceDAO deviceDAO = daoFactory.getDeviceDAO();
		Device device = deviceDAO.findById(rs.getInt(COLUMN_DEVICE_ID));
    	item.setDevice(device);
    	IPortStatusDAO portStatusDAO = daoFactory.getPortStatusDAO();
		PortStatus portStatus = portStatusDAO.findById(rs.getInt(PORT_STATUS_ID));
		item.setPortStatusID(portStatus);
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
		ArrayList<Port> ports = daoFactory.getPortDAO().findAll();
		ICableDAO cable = daoFactory.getCableDAO();
			for (Port p: ports){
					if (!cable.existConnectToPort(p.getId())){
						return p.getId();
					} 
				}
	
		return 0;
	}
	
	/**
	 * Method for searching port by order task
	 * 
	 * @see com.zephyrus.wind.dao.interfaces.ICableDAO
	 * @param given task
	 * @return port object if exist, otherwise null.
	 * @author Miroshnychenko Nataliya
	 */

	
	@Override
	public Port findPortFromTaskID(Task task) throws Exception{
		ServiceOrder serviceOrder = task.getServiceOrder();
		ServiceLocation serviceLocation = serviceOrder.getServiceLocation();
		if (serviceLocation == null){
			return null;
		} 
		Cable cable = daoFactory.getCableDAO().findCableFromServLoc(serviceLocation.getId());
		return cable.getPort();
	}


}
