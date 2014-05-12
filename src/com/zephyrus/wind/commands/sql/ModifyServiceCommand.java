package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;															
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceInstance;

/**
 * This class contains the method, that is declared in 
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. 
 * Method realized SI modify
 * 
 * @see com.zephyrus.wind.model.ProductCatalog
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IProductCatalogDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
 * @author Miroshnychenko Nataliya
 */

public class ModifyServiceCommand extends SQLCommand{

	/**
	 * This method selects products from Product Catalog for modify SI service
	 * Method gets parameter of service instance ID that will be modify
	 * 
	 * @see com.zephyrus.wind.model.ProductCatalog
	 * @see com.zephyrus.wind.model.ServiceInstance
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.IProductCatalogDAO
	 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
	 * 
	 * @return page with list of available products		
	 * 
	 */

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		IProductCatalogDAO productCatalogDAO = getOracleDaoFactory().getProductCatalogDAO();
		ArrayList<ProductCatalog> products = null;
		
		Integer serviceInstanceID;

		if(request.getParameter("id") == null){
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
        }
		
		IServiceInstanceDAO serviceInstanceDAO = getOracleDaoFactory().getServiceInstanceDAO();

		try {
			serviceInstanceID = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceInstance serviceInstance = serviceInstanceDAO.findById(serviceInstanceID);	

		if (serviceInstance == null) {
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ProviderLocation providerLocation = serviceInstance.getProductCatalog().getProviderLoc();
		products =  productCatalogDAO.getProductsByProviderLocation(providerLocation);
		
		if (products == null){
			request.setAttribute("messageNumber", MessageNumber.PRODUCTS_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		request.setAttribute("serviceInstance", serviceInstance);
		request.setAttribute("products", products);


		return PAGES.MODIFYSERVICE_PAGE.getValue();
	}

}
