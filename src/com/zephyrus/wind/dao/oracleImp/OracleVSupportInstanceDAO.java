package com.zephyrus.wind.dao.oracleImp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IVSupportInstanceDAO;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.model.VSupportInstance;

public class OracleVSupportInstanceDAO extends OracleDAO<VSupportInstance> implements IVSupportInstanceDAO{
	private static final String TABLE_NAME = "MISTERDAN.V_SUPPORT_INSTANCE";
    private static final String SQL_SELECT = "SELECT USER_ID, SI_ID, SI_STARD_DATE, " + 
                                      "SERVICE, PRICE, SI_STATUS FROM " + 
                                       TABLE_NAME + " ";

    private static final String SQL_REMOVE = "DELETE FROM " + TABLE_NAME + "WHERE ";

	public OracleVSupportInstanceDAO(
			Connection connection, OracleDAOFactory daoFactory)
			throws Exception {
		super(VSupportInstance.class, connection, daoFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(VSupportInstance record) throws Exception {
				
	}

	@Override
	public VSupportInstance insert(VSupportInstance record) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remove(VSupportInstance record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void fillItem(VSupportInstance item, ResultSet rs)
			throws Exception {
		User user = daoFactory.getUserDAO().findById(rs.getInt(1));
		item.setUser(user);
		ServiceInstance si = daoFactory.getServiceInstanceDAO().findById(rs.getInt(2));
		item.setSi(si);
		item.setSiStardDate(rs.getDate(3));
		item.setService(rs.getString(4));
		item.setPrice(rs.getInt(5));
		item.setSiStatus(rs.getString(6));

		
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
	public ArrayList<VSupportInstance> getInstancesByUserId(int id) throws Exception {
		stmt = connection.prepareStatement(SQL_SELECT + "WHERE USER_ID = ?");
		stmt.setInt(1, id);
		rs = stmt.executeQuery();	
		return fetchMultiResults(rs);
	}

}
