package com.zephyrus.wind.commands.sql;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.helpers.DistanceCalculator;
import com.zephyrus.wind.model.ProductCatalog;
import com.zephyrus.wind.model.ServiceLocation;

/**
 * This class contains the method, that is declared in 
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It
 * provides the functionality of available services sending for 
 * displaying it on page of service selection.
 * 
 * @author Alexandra Beskorovaynaya
 * 
 */
public class MappingCommand extends SQLCommand {
	
	/**
	 * Method sends the list of available services on page of 
	 * service selection in accordance of preselected by user
	 * Service Location.
	 * 
	 * @param request http request
	 * @param response http response
	 * 
	 * @return url of page for redirecting. Always return null because
	 * all requests are asynchronous and there is no need for redirecting
	 */
	@Override
	public String doExecute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		// coordinates of Service location
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String address =  request.getParameter("address");

		ServiceLocation sl = new ServiceLocation();
		
		//convert coordinates into format using in DB
		String s = latitude + "," + longitude;
		
		sl.setServiceLocationCoord(s);
		sl.setAddress(address);
		
		//write Service Location into the user session
		request.getSession().setAttribute("serviceLocation", sl);

		DistanceCalculator dc = new DistanceCalculator();
		
		//get services available for the given service location
		ArrayList<ProductCatalog> services = dc.getNearestProvidersServices(sl,
				getOracleDaoFactory());
		
		//if there are no available services
		if (services == null) {
			String noServices = "noServices";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(noServices);
			return null;
		}
		
		//return the list of available services in JSON format
		request.getSession().setAttribute("products", services);
		String json = new Gson().toJson(services);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		System.out.println(json);
		return null;
	}

}
