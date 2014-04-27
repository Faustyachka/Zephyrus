package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;																
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IServiceInstanceDAO;
import com.zephyrus.wind.dao.interfaces.IServiceOrderDAO;													// REVIEW: unused imports found
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.ServiceInstance;
import com.zephyrus.wind.model.ServiceOrder;
import com.zephyrus.wind.model.User;
																											// REVIEW: documentation expected
public class GetUsersSI extends SQLCommand {
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		Gson gson = new Gson();
		String action=(String)request.getParameter("action");
		response.setContentType("application/json");
		if(action==null) {
			return PAGES.SUPPORT_PAGE.getValue();
		} else {
		if (action.equals("list")) {																		// REVIEW: bad formatting
		try{     
				IServiceInstanceDAO dao = getOracleDaoFactory().getServiceInstanceDAO();	
				int id = Integer.parseInt(request.getParameter("id"));
				ArrayList<ServiceInstance> SO = dao.getServiceInstancesByUserId(id);	
			    JsonElement element = gson.toJsonTree(SO, new TypeToken<List<ServiceInstance>>() {}.getType()); // REVIEW: should be refactored into few more lines
			    JsonArray jsonArray = element.getAsJsonArray();			
			    String listData=jsonArray.toString();   						
			    listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";  		
			    response.getWriter().print(listData);
			    return null;			
			    }catch(Exception ex){
			
			     String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
			
			     response.getWriter().print(error);
			
			     ex.printStackTrace();
			
			    }
		} }																									// REVIEW: bad formatting
		return PAGES.SUPPORT_VIEW_SI_PAGE.getValue();
}
}
