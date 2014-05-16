
package com.zephyrus.wind.dao.factory;

import com.zephyrus.wind.dao.interfaces.ICableDAO;
import com.zephyrus.wind.dao.interfaces.ICircuitDAO;
import com.zephyrus.wind.dao.interfaces.IDeviceDAO;
import com.zephyrus.wind.dao.interfaces.IOrderStatusDAO;
import com.zephyrus.wind.dao.interfaces.IOrderTypeDAO;
import com.zephyrus.wind.dao.interfaces.IPortDAO;
import com.zephyrus.wind.dao.interfaces.IPortStatusDAO;
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

/**
* An abstract factory interface representing DAO factories. Implementations of this
* interface will generates DAO instances. 
* @author Alexandra Beskorovaynaya & Miroshnychenko Nataliya
*/
public interface IDAOFactory {

	/**
	 * Method gets OracleCableDAO instance
	 * @return OracleCableDAO instance
	 */
	ICableDAO getCableDAO() throws Exception;	

	/**
	 * Method gets OracleCircuitDAO instance
	 * @return OracleCircuitDAO instance
	 */
	ICircuitDAO getCircuitDAO() throws Exception; 
	
	/**
     * Method gets OracleDeviceDAO instance
     * @return OracleDeviceDAO instance
     */
	IDeviceDAO getDeviceDAO()  throws Exception;
	
	/**
     * Method gets OracleOrderStatusDAO instance
     * @return OracleOrderStatusDAO instance
     */
	IOrderStatusDAO getOrderStatusDAO()  throws Exception;
	
	/**
     * Method gets OracleOrderTypeDAO instance
     * @return OracleOrderTypeDAO instance
     */
	IOrderTypeDAO  getOrderTypeDAO()  throws Exception;
	
	/**
     * Method gets OraclePortDAO instance
     * @return OraclePortDAO instance
     */
	IPortDAO  getPortDAO()  throws Exception;
	
	/**
     * Method gets OracleProductCatalogDAO instance
     * @return OracleProductCatalogDAO instance
     */
	IProductCatalogDAO  getProductCatalogDAO()  throws Exception;
	
	/**
     * Method gets OracleProviderLocationDAO instance
     * @return OracleProviderLocationDAO instance
     */
	IProviderLocationDAO  getProviderLocationDAO()  throws Exception;
	
	/**
     * Method gets OracleServiceInstanceDAO instance
     * @return OracleServiceInstanceDAO instance
     */
	IServiceInstanceDAO  getServiceInstanceDAO()  throws Exception;
	
	/**
     * Method gets OracleServiceInstanceStatusDAO instance
     * @return OracleServiceInstanceStatusDAO instance
     */
	IServiceInstanceStatusDAO  getServiceInstanceStatusDAO()  throws Exception;
	
	/**
     * Method gets OracleServiceLocationDAO instance
     * @return OracleServiceLocationDAO instance
     */
	IServiceLocationDAO  getServiceLocationDAO()  throws Exception;
	
	/**
     * Method gets OracleServiceOrderDAO instance
     * @return OracleServiceOrderDAO instance
     */
	IServiceOrderDAO getServiceOrderDAO() throws Exception;
	
	/**
     * Method gets OracleServiceTypeDAO instance
     * @return OracleServiceTypeDAO instance
     */
	IServiceTypeDAO getServiceTypeDAO() throws Exception;
	
	/**
     * Method gets OracleTaskDAO instance
     * @return OracleTaskDAO instance
     */
	ITaskDAO getTaskDAO() throws Exception;
	
	/**
     * Method gets OracleTaskStatusDAO instance 
     * @return OracleTaskStatusDAO instance
     */
	ITaskStatusDAO getTaskStatusDAO() throws Exception;
	
	/**
     * Method gets OracleUserDAO instance
     * @return OracleUserDAO instance
     */

	IUserDAO getUserDAO() throws Exception;
	
	/**
     * Method gets OracleUserRoleDAO instance
     * @return OracleUserRoleDAO instance
     */
	IUserRoleDAO getUserRoleDAO() throws Exception;
	
	/**
     * Method gets OracleVSupportInstanceDAO instance
     * @return OracleVSupportInstanceDAO instance
     */
	IVSupportInstanceDAO getVSupportInstanceDAO() throws Exception;
	
	/**
     * Method gets OracleVSupportOrderDAO instance
     * @return OracleVSupportOrderDAO instance
     */
	IVSupportOrderDAO getVSupportOrderDAO() throws Exception;
	
	/**
     * Method gets OracleReportDAO instance
     * @return OracleReportDAO instance
     */
	IPortStatusDAO getPortStatusDAO() throws Exception;
	
	/**
     * Method gets OraclePortStatusDAO instance
     * @return OraclePortStatusDAO instance
     */
	IReportDAO getReportDAO() throws Exception;
}
