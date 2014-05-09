package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.workflow.ModifyScenarioWorkflow;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand. // REVIEW: link isn't working
 * Method realized SI modify
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
 * @return massage of successful modify order creation											// REVIEW: return
 * @author Miroshnychenko Nataliya
 */

public class ModifyOrderCreateCommand extends SQLCommand {
	
	private ServiceOrder modifyOrder = null;

	
	/**
	 * This method started modify scenario workflow
	 * Method gets parameter of service instance ID that will be modify 
	 * and product catalog ID, that user has been selected
	 * 
	 * @see com.zephyrus.wind.model.ServiceInstance
	 * @see com.zephyrus.wind.enums.PAGES
	 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
	 * 
	 * @return page with confirmation of successful creation of modify order			// REVIEW: always?
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		if(request.getParameter("serviceInstance") == null){
        	request.setAttribute("message", "Service Instance is not selected");
        	return PAGES.MESSAGE_PAGE.getValue();
        }
		
		if(request.getParameter("product") == null){
        	request.setAttribute("message", "Service of product catalog is not selected");
        	return PAGES.MESSAGE_PAGE.getValue();
        }
		
		IServiceInstanceDAO serviceInstanceDAO = getOracleDaoFactory().getServiceInstanceDAO();
		Integer serviceInstanceID = Integer.parseInt(request.getParameter("serviceInstance"));	// REVIEW: what if parse failed?
		ServiceInstance serviceInstance = serviceInstanceDAO.findById(serviceInstanceID);
		
		IProductCatalogDAO productCatalogDAO = getOracleDaoFactory().getProductCatalogDAO();
		Integer productCatalogId = Integer.parseInt(request.getParameter("product"));				// REVIEW: what if parse failed?
		ProductCatalog productCatalog = productCatalogDAO.findById(productCatalogId);
		
		modifyOrder = createModifyOrder(serviceInstance, productCatalog);							// REVIEW: you have modifyOrder variable with class scope. so why do you need to return it? either return nothing or make this variable local and delete class-scope variable (prefered)
		
		ModifyScenarioWorkflow workflow = new ModifyScenarioWorkflow(getOracleDaoFactory(), modifyOrder);	// REVIEW: try block expected
		workflow.proceedOrder();
		workflow.close();

		request.setAttribute("message", "Modify order has been created successfuly <br>"				// REVIEW: HTML
						+ "<br><a href='/Zephyrus/customerServices'> <input type='button' value='Back to"
						+ " services' class='button'></a>");
		return PAGES.MESSAGE_PAGE.getValue();
		
	}
	
	/**
	 * This method create order for modify scenario workflow 
	 * Method gets parameter of service instance that will be disconnect and new Product Catalog
	 * In the processes of modify SI will be created Service Order with type MODIFY and status ENTERING
	 * 																			// REVIEW: params specification expected
	 * @see com.zephyrus.wind.model.ServiceOrder
	 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
	 * 
	 * @return modify service order												// REVIEW: rephrase
	 */

	private ServiceOrder createModifyOrder(ServiceInstance serviceInstance, ProductCatalog productCatalog) throws Exception {
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = new ServiceOrder();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(ORDER_STATUS.ENTERING.getId());	
		order.setOrderStatus(orderStatus);										// REVIEW: gaps between blocks of code would be appreciated
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		OrderType orderType = new OrderType();
		orderType.setId(ORDER_TYPE.MODIFY.getId());	
		order.setOrderType(orderType);
		order.setProductCatalog(productCatalog);
		ServiceOrder modifyServiceOrders = orderDAO.getSICreateOrder(serviceInstance);
		order.setServiceLocation(modifyServiceOrders.getServiceLocation());
		order.setServiceInstance(serviceInstance);
		modifyOrder = orderDAO.insert(order);
		return modifyOrder;
	}



}
