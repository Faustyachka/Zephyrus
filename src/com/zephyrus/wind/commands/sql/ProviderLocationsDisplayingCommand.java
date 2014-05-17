package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProviderLocation;

/**
 * This class contains the method, that is declared in 
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It gets list of
 * provider locations from DB and sends it to jsp page for displaying them 
 * on the Google Map. 
 * 
 * @author Alexandra Beskorovaynaya
 */
public class ProviderLocationsDisplayingCommand extends SQLCommand {
    
	/**The quantity of Provider Locations for displaying on the map*/
	private final int MAX_PROVIDER_LOCATIONS_TO_DISPLAY = 10;
	
	/**
	 * This method gets list of provider locations from DB and sends it to jsp page for displaying them 
	 * on the Google Map. 
	 * 
	 * @return String address of page for redirection. Always return null because
	 * all requests for this command are asynchronous.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		IProviderLocationDAO providerDAO = getOracleDaoFactory().getProviderLocationDAO();
		ArrayList<ProviderLocation> locations = providerDAO.find(1, MAX_PROVIDER_LOCATIONS_TO_DISPLAY);
		request.getSession().setAttribute("providers", locations);
        String json = new Gson().toJson(locations);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
		return null;
	}

}
