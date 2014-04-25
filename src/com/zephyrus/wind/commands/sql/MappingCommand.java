package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.helpers.DistanceCalculator;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ProductCatalogService;
import com.zephyrus.wind.model.ServiceInstanceStatus;
import com.zephyrus.wind.model.ServiceLocation;
/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */
public class MappingCommand extends SQLCommand{

	@Override
	public String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String longitude = null;
        String latitude = null;
        String address = null;
               
        longitude = request.getParameter("longitude");
        latitude = request.getParameter("latitude");
        address = request.getParameter("address");
     
        ServiceLocation sl = new ServiceLocation();
        String s = longitude+","+latitude;
        sl.setServiceLocationCoord(s);
        sl.setAddress(address);
        request.getSession().setAttribute("serviceLocation", sl);
        
        DistanceCalculator dc = new DistanceCalculator();
        
        ArrayList<ProductCatalog> services = dc.getNearestProvidersServices(sl, getOracleDaoFactory());
        request.getSession().setAttribute("products", services);
       
        String json = new Gson().toJson(services);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        System.out.println(json);
		return null;
	}

}
