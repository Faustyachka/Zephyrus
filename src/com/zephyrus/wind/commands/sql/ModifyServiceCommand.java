package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProviderLocation;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;

public class ModifyServiceCommand extends SQLCommand{

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		IServiceInstanceDAO serviceInstanceDAO = getOracleDaoFactory().getServiceInstanceDAO();
		IProductCatalogDAO productCatalogDAO = getOracleDaoFactory().getProductCatalogDAO();
		ServiceInstance serviceInstance = null;
		Integer serviceInstanceID = null; 
		ArrayList<ProductCatalog> products = null;

		if (request.getParameter("id") != null){
		serviceInstanceID = Integer.parseInt(request.getParameter("id"));
		}
		if(serviceInstanceID != null){
			serviceInstance = serviceInstanceDAO.findById(serviceInstanceID);
		} else {
			request.setAttribute("errorMessage", "Service Instance doesn`t exist!");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		ProviderLocation providerLocation = serviceInstance.getProductCatalog().getProviderLoc();
		products =  productCatalogDAO.getProductsByProviderLocation(providerLocation);
		
		request.setAttribute("serviceInstance", serviceInstance);
		request.setAttribute("products", products);
		
		
		return PAGES.MODIFYSERVICE_PAGE.getValue();
	}

}
