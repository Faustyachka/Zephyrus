package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.model.ProductCatalogService;
import com.zephyrus.wind.model.ServiceLocation;

public class ProceedOrderCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		HttpSession session = request.getSession();
        
        Integer productId = Integer.parseInt(request.getParameter("services"));
        ArrayList<ProductCatalogService> services = ( ArrayList<ProductCatalogService>) session.getAttribute("products");
        ProductCatalogService service = null;
        for(ProductCatalogService ser : services)
        	if(ser.getId() == productId)
        		service = ser;
        session.setAttribute("service", service);
        if(session.getAttribute("user") != null){
        	return Pages.ORDERDETAIL_PAGE.getValue();
        } else {
        	response.sendRedirect(Pages.LOGIN_PAGE.getValue());
        	return null;
        }
	}

}
