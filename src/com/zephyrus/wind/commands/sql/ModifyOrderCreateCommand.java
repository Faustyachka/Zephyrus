package com.zephyrus.wind.commands.sql;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IProductCatalogDAO;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.ORDER_STATUS;
import com.zephyrus.wind.enums.ORDER_TYPE;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.OrderStatus;
import com.zephyrus.wind.model.OrderType;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
import com.zephyrus.wind.workflow.DisconnectScenarioWorkflow;
import com.zephyrus.wind.workflow.ModifyScenarioWorkflow;
import com.zephyrus.wind.workflow.WorkflowException;

/**
 * This class contains the method, that is declared in 
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. 
 * Method realized SI modify
 * 
 * @see com.zephyrus.wind.model.ServiceOrder
 * @see com.zephyrus.wind.model.ServiceInstance
 * @see com.zephyrus.wind.enums.PAGES
 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
 * @see com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO
 * 
 * @author Miroshnychenko Nataliya
 */

public class ModifyOrderCreateCommand extends SQLCommand {


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
	 * Also can return the error page if the received Service Instance ID or Product Catalog ID is not valid
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		ServiceOrder modifyOrder;

		Integer serviceInstanceID;
		Integer productCatalogId;
		
		User user = (User) request.getSession().getAttribute("user");
		
		if (user == null || user.getRole().getId() != ROLE.CUSTOMER.getId()) {
			request.setAttribute("messageNumber", MessageNumber.USER_EXIST_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		if(request.getParameter("serviceInstance") == null){
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		if(request.getParameter("product") == null){
			request.setAttribute("messageNumber", MessageNumber.PRODUCT_CATALOG_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		IServiceInstanceDAO serviceInstanceDAO = getOracleDaoFactory().getServiceInstanceDAO();

		try {
			serviceInstanceID = Integer.parseInt(request.getParameter("serviceInstance"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ServiceInstance serviceInstance = serviceInstanceDAO.findById(serviceInstanceID);

		if (serviceInstance == null || serviceInstance.getUser().getId() != user.getId()) {
			request.setAttribute("messageNumber", MessageNumber.SERVICE_INSTANCE_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		IProductCatalogDAO productCatalogDAO = getOracleDaoFactory().getProductCatalogDAO();

		try {
			productCatalogId = Integer.parseInt(request.getParameter("product"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.PRODUCT_CATALOG_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		ProductCatalog productCatalog = productCatalogDAO.findById(productCatalogId);

		if (productCatalog == null) {
			request.setAttribute("messageNumber", MessageNumber.PRODUCT_CATALOG_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		modifyOrder = createModifyOrder(serviceInstance, productCatalog);							// REVIEW: you have modifyOrder variable with class scope. so why do you need to return it? either return nothing or make this variable local and delete class-scope variable (prefered)


		ModifyScenarioWorkflow workflow = null;

		try {
			workflow = new ModifyScenarioWorkflow(getOracleDaoFactory(), modifyOrder); 
			workflow.proceedOrder();
		} catch (WorkflowException ex) {
			ex.printStackTrace();
			getOracleDaoFactory().rollbackTransaction();
			request.setAttribute("serviceInstanceID", serviceInstanceID);
			request.setAttribute("productCatalogId", productCatalogId);
			request.setAttribute("error", "Failed to modify Service Instance");
			return "customerServices";
		} finally {
			workflow.close();
		}

		request.setAttribute("messageNumber", MessageNumber.SI_MODIFY_COMPLETED_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();
	}

	/**
	 * This method create order for modify scenario workflow 
	 * Method gets parameter of service instance that will be disconnect and new Product Catalog
	 * In the processes of modify SI will be created Service Order with type MODIFY and status ENTERING
	 * 
	 * @param modifyable Service Instance and selected service from Product Catalog 
	 * 																			
	 * @see com.zephyrus.wind.model.ServiceOrder
	 * @see com.zephyrus.wind.dao.interfaces.IServiceOrderDAO
	 * 
	 * @return modify service order												
	 */

	private ServiceOrder createModifyOrder(ServiceInstance serviceInstance, ProductCatalog productCatalog) throws Exception {
		IServiceOrderDAO orderDAO = getOracleDaoFactory().getServiceOrderDAO();
		ServiceOrder order = new ServiceOrder();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(ORDER_STATUS.ENTERING.getId());	
		order.setOrderStatus(orderStatus);	
		
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		
		OrderType orderType = new OrderType();
		orderType.setId(ORDER_TYPE.MODIFY.getId());	
		order.setOrderType(orderType);
		
		order.setProductCatalog(productCatalog);
		
		ServiceOrder modifyServiceOrders = orderDAO.getSICreateOrder(serviceInstance);
		order.setServiceLocation(modifyServiceOrders.getServiceLocation());
		
		order.setServiceInstance(serviceInstance);
		
		ServiceOrder orderAdd = orderDAO.insert(order);
		
		return orderAdd;
	}



}
