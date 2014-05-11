package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IProviderLocationDAO;
import com.zephyrus.wind.model.ProviderLocation;
																					// REVIEW: documentation expected
public class ProviderLocationsDisplayingCommand extends SQLCommand {
    
	int MAX_PROVIDER_LOCATIONS_TO_DISPLAY = 10;
	
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
