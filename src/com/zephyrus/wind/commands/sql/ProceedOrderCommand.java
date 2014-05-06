package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;	
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProductCatalogService;												// REVIEW: unused import found
																									// REVIEW: documentation & author expected
public class ProceedOrderCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		HttpSession session = request.getSession();
        
		if (request.isUserInRole("ADMIN")==false && request.isUserInRole("INSTALLATION")==false &&
				request.isUserInRole("PROVISION")==false && request.isUserInRole("SUPPORT")==false) {
				if(request.getParameter("services") == null){
		        	request.setAttribute("message", "Choose at least one service");
		        	return PAGES.MESSAGE_PAGE.getValue();
		        }
        Integer productId = Integer.parseInt(request.getParameter("services"));
        ArrayList<ProductCatalog> services = ( ArrayList<ProductCatalog>) session.getAttribute("products");
        ProductCatalog service = null;
        for(ProductCatalog ser : services)	{														// REVIEW: single instructions also should be in braces
        	if(ser.getId() == productId) {															// REVIEW: DAO method should be written to do this
        		service = ser;
        	}
        }
        session.setAttribute("service", service);
        if(session.getAttribute("user") != null){
        	return PAGES.ORDERDETAIL_PAGE.getValue();
        } else {
        	response.sendRedirect(PAGES.LOGIN_PAGE.getValue());
        	return null;
        }
	}
		else {
			request.setAttribute("message", "Access to this page is prohibited.");
			return PAGES.MESSAGE_PAGE.getValue();
		}
	}
		

}
