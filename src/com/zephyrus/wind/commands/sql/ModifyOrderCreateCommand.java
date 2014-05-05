package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.workflow.ModifyScenarioWorkflow;

/**
 * This class contains the method, that is declared in @link #com.zephyrus.wind.commands.interfaces.SQLCommand.
 * Method realized SI modify
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
 * @return massage of successful modify order creation
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
	 * @return page with confirmation of successful creation of modify order
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
		Integer serviceInstanceID = Integer.parseInt(request.getParameter("serviceInstance"));
		ServiceInstance serviceInstance = serviceInstanceDAO.findById(serviceInstanceID);
		
		Integer productCatalogId = Integer.parseInt(request.getParameter("product"));
		
		modifyOrder = createModifyOrder(serviceInstance, productCatalogId);
		
		ModifyScenarioWorkflow workflow = new ModifyScenarioWorkflow(getOracleDaoFactory(), modifyOrder);
		workflow.proceedOrder();
		workflow.close();

		request.setAttribute("message", "Modify order has been created successfuly");
		return PAGES.MESSAGE_PAGE.getValue();
		
	}

	private ServiceOrder createModifyOrder(ServiceInstance serviceInstance, Integer productCatalogId) throws Exception {
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = new ServiceOrder();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(ORDER_STATUS.ENTERING.getId());	
		order.setOrderStatus(orderStatus);
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		OrderType orderType = new OrderType();
		orderType.setId(ORDER_TYPE.MODIFY.getId());	
		order.setOrderType(orderType);
		order.setProductCatalog(serviceInstance.getProductCatalog());
		ServiceOrder modifyServiceOrders = orderDAO.getSICreateOrder(serviceInstance);
		order.setServiceLocation(modifyServiceOrders.getServiceLocation());
		order.setServiceInstance(serviceInstance);
		modifyOrder = orderDAO.insert(order);
		return modifyOrder;
	}



}