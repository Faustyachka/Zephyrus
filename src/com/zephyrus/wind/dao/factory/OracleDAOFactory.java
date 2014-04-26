package com.zephyrus.wind.dao.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.dao.interfaces.IReportDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.interfaces.IServiceLocationDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.dao.interfaces.IServiceTypeDAO;
import com.zephyrus.wind.dao.interfaces.ITaskDAO;
import com.zephyrus.wind.dao.interfaces.ITaskStatusDAO;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.dao.interfaces.IUserRoleDAO;
import com.zephyrus.wind.dao.interfaces.IVSupportInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IVSupportOrderDAO;
import com.zephyrus.wind.dao.oracleImp.OracleCableDAO;
import com.zephyrus.wind.dao.oracleImp.OracleCircuitDAO;
import com.zephyrus.wind.dao.oracleImp.OracleDeviceDAO;
import com.zephyrus.wind.dao.oracleImp.OracleOrderStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleOrderTypeDAO;
import com.zephyrus.wind.dao.oracleImp.OraclePortDAO;
import com.zephyrus.wind.dao.oracleImp.OracleProductCatalogDAO;
import com.zephyrus.wind.dao.oracleImp.OracleProviderLocationDAO;
import com.zephyrus.wind.dao.oracleImp.OracleReportDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceInstanceDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceInstanceStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceLocationDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceOrderDAO;
import com.zephyrus.wind.dao.oracleImp.OracleServiceTypeDAO;
import com.zephyrus.wind.dao.oracleImp.OracleTaskDAO;
import com.zephyrus.wind.dao.oracleImp.OracleTaskStatusDAO;
import com.zephyrus.wind.dao.oracleImp.OracleUserDAO;
import com.zephyrus.wind.dao.oracleImp.OracleUserRoleDAO;
import com.zephyrus.wind.dao.oracleImp.OracleVSupportInstanceDAO;
import com.zephyrus.wind.dao.oracleImp.OracleVSupportOrderDAO;
import com.zephyrus.wind.managers.ConnectionManager;

/**
 * This class generates DAO instances and encapsulates connection to DB
 * 
 * @author Bogdan Bodnar & Igor Litvinenko
 */
public class OracleDAOFactory implements IDAOFactory { // REVIEW: documentation
														// on EVERY public
														// method expected

	private Connection connection = null;

	public OracleDAOFactory() {
	}

	/**
	 * Obtains connection from pool for current factory. Sets auto commit to
	 * false
	 * 
	 * @throws SQLException
	 *             if failed to obtain connection
	 */
	public void beginConnection() throws SQLException {
		connection = ConnectionManager.INSTANCE.getConnection();
		connection.setAutoCommit(false);
	}

	/**
	 * Closes connection obtained by factory
	 * 
	 * @throws SQLException
	 *             if failed to close connection
	 */
	public void endConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	/**
	 * This function commits current transaction, presented by executions
	 * between <code>beginConnection()</code> and <code>endConnection()</code>
	 * 
	 * @throws SQLException
	 *             if failed to commit
	 */
	public void commitTransaction() throws SQLException {
		if (connection != null) {
			connection.commit();
		}
	}

	@Override
	public ICableDAO getCableDAO() throws Exception {
		return new OracleCableDAO(connection, this);
	}

	@Override
	public ICircuitDAO getCircuitDAO() throws Exception {
		return new OracleCircuitDAO(connection, this);
	}

	@Override
	public IDeviceDAO getDeviceDAO() throws Exception {
		return new OracleDeviceDAO(connection, this);
	}

	@Override
	public IOrderStatusDAO getOrderStatusDAO() throws Exception {
		return new OracleOrderStatusDAO(connection, this);
	}

	@Override
	public IOrderTypeDAO getOrderTypeDAO() throws Exception {
		return new OracleOrderTypeDAO(connection, this);
	}

	@Override
	public IPortDAO getPortDAO() throws Exception {
		return new OraclePortDAO(connection, this);
	}

	@Override
	public IProductCatalogDAO getProductCatalogDAO() throws Exception {
		return new OracleProductCatalogDAO(connection, this);
	}

	@Override
	public IProviderLocationDAO getProviderLocationDAO() throws Exception {
		return new OracleProviderLocationDAO(connection, this);
	}

	@Override
	public IServiceInstanceDAO getServiceInstanceDAO() throws Exception {
		return new OracleServiceInstanceDAO(connection, this);
	}

	@Override
	public IServiceInstanceStatusDAO getServiceInstanceStatusDAO()
			throws Exception {
		return new OracleServiceInstanceStatusDAO(connection, this);
	}

	@Override
	public IServiceLocationDAO getServiceLocationDAO() throws Exception {
		return new OracleServiceLocationDAO(connection, this);
	}

	@Override
	public IServiceOrderDAO getServiceOrderDAO() throws Exception {
		return new OracleServiceOrderDAO(connection, this);
	}

	@Override
	public IServiceTypeDAO getServiceTypeDAO() throws Exception {
		return new OracleServiceTypeDAO(connection, this);
	}

	@Override
	public ITaskDAO getTaskDAO() throws Exception {
		return new OracleTaskDAO(connection, this);
	}

	@Override
	public ITaskStatusDAO getTaskStatusDAO() throws Exception {
		return new OracleTaskStatusDAO(connection, this);
	}

	@Override
	public IUserDAO getUserDAO() throws Exception {
		return new OracleUserDAO(connection, this);
	}

	@Override
	public IUserRoleDAO getUserRoleDAO() throws Exception {
		return new OracleUserRoleDAO(connection, this);
	}

	@Override
	public IVSupportInstanceDAO getVSupportInstanceDAO() throws Exception {
		return new OracleVSupportInstanceDAO(connection, this);
	}

	@Override
	public IVSupportOrderDAO getVSupportOrderDAO() throws Exception {
		return new OracleVSupportOrderDAO(connection, this);
	}

	@Override
	public IReportDAO getReportDAO() throws Exception {
		return (IReportDAO) new OracleReportDAO(connection, this);
	}
}
