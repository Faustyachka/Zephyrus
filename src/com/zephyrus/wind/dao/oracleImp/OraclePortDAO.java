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
import com.zephyrus.wind.model.Device;
import com.zephyrus.wind.model.Port;
import com.zephyrus.wind.model.ProductCatalog;

public class OraclePortDAO extends OracleDAO<Port> implements IPortDAO {

	private static final String TABLE_NAME = "PORTS";
    private static final String SQL_SELECT = "SELECT ID, DEVICE_ID, PORT_NUMBER " + 
                                      "FROM " + 
                                       TABLE_NAME + " ";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + 
                                      " SET DEVICE_ID = ?, PORT_NUMBER = ? " + 
                                      " WHERE " + 
                                      " ID = ?";
    private static final String SQL_INSERT = "BEGIN INSERT INTO " + TABLE_NAME + 
												"(DEVICE_ID, PORT_NUMBER) VALUES(?,?)" +
												"RETURN ROWID INTO ?;END;";
    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";
    
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_DEVICE_ID = 2;
    private static final int COLUMN_PORT_NUMBER = 3;   

	public OraclePortDAO( Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(Port.class, connection, daoFactory);
	}

	@Override
	public void update(Port record) throws Exception {
		stmt = connection.prepareStatement(SQL_UPDATE);
    	stmt.setInt(COLUMN_DEVICE_ID, record.getDevice().getId());
    	stmt.setInt(COLUMN_PORT_NUMBER, record.getPortNumber()); 
    	stmt.setLong(COLUMN_ID, record.getId());
        stmt.executeUpdate();
        stmt.close();
	}

	@Override
	public Port insert(Port record) throws Exception {
		cs = connection.prepareCall(SQL_INSERT);
    	cs.setInt(1, record.getDevice().getId());
    	cs.setInt(2, record.getPortNumber());    
    	cs.registerOutParameter(3, OracleTypes.VARCHAR);
        cs.execute();
        String rowId = cs.getString(3);
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
	public int findFreePort() throws Exception {
		ArrayList<Port> ports = daoFactory.getPortDAO().findAll();
		ArrayList<Device> devices = daoFactory.getDeviceDAO().findAll();
		ICableDAO cable = daoFactory.getCableDAO();
		for (Device d: devices){
			int i = 1;
			for (Port p: ports){
				if (d.getId() == p.getDevice().getId()){
					if (cable.findPortID(p.getId())){
						i++;
					} else {
						return findByDevPortID(d.getId(), i).getId();
					}
				}
			}
		}
		return 0;
	}

	@Override
	public Port findByDevPortID(int devId, int portId) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + " WHERE (DEVICE_ID = ? and PORT_NUMBER = ? )");
		stmt.setInt(1, devId);
		stmt.setInt(2, portId);
		rs = stmt.executeQuery();
		return fetchSingleResult(rs);
	}

}
