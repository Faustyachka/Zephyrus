package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProductCatalogService;

public class ProceedOrderCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		HttpSession session = request.getSession();
        if(request.getParameter("services") == null){
        	request.setAttribute("message", "Choose at least one service");
        	return PAGES.MESSAGE_PAGE.getValue();
        }
        Integer productId = Integer.parseInt(request.getParameter("services"));
        ArrayList<ProductCatalog> services = ( ArrayList<ProductCatalog>) session.getAttribute("products");
        ProductCatalog service = null;
        for(ProductCatalog ser : services)
        	if(ser.getId() == productId)
        		service = ser;
        session.setAttribute("service", service);
        if(session.getAttribute("user") != null){
        	return PAGES.ORDERDETAIL_PAGE.getValue();
        } else {
        	response.sendRedirect(PAGES.LOGIN_PAGE.getValue());
        	return null;
        }
	}

}
