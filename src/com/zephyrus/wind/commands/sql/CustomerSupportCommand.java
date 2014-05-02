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
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to display
 * the list of customer users on the index page of Customer Support Engineer.
 * 
 * @return index page of Customer Support Engineer with JTable of Customer
 *         Users.
 * @author Alexandra Beskorovaynaya
 */
public class CustomerSupportCommand extends SQLCommand {

	/**
	 * This method forms the data for JTable on Customer Support index page. It
	 * gets the list of customer users from the DB, transform it to Json
	 * Array and send on the jsp page.
	 * 
	 * @return index page of Customer Support Engineer with JTable of Customer
	 *         Users.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		Gson gson = new Gson();
		String action = (String) request.getParameter("action");
		response.setContentType("application/json");

		// checking is Jtable initialized
		if (action == null) {
			return "support/index.jsp";
		} else {
			//checking is this the AJAX request for users list getting from JSP
			if (action.equals("list")) {
				try {
					int startPageIndex=
					          Integer.parseInt(request.getParameter("jtStartIndex"))+1; //+1 because Oracle counts rows from 1st
					       int numRecordsPerPage=
					          Integer.parseInt(request.getParameter("jtPageSize"));
					IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
					ArrayList<User> lstUser = userDAO
							.getUsersByRoleId(ROLE.CUSTOMER.getId(),startPageIndex, numRecordsPerPage); 
					int userCount=userDAO.count();    // TODO counter
					JsonElement element = gson.toJsonTree(lstUser,
							new TypeToken<List<User>>() {
							}.getType());
					JsonArray jsonArray = element.getAsJsonArray();
					String listData = jsonArray.toString();
					
					//transforming data to form required in JTable
					listData = "{\"Result\":\"OK\",\"Records\":" + listData
							+ ",\"TotalRecordCount\":"+userCount+"}";
					response.getWriter().print(listData);
					return null;
				} catch (Exception ex) {
					String error = "{\"Result\":\"ERROR\",\"Message\":"
							+ ex.getMessage() + "}";

					response.getWriter().print(error);

					ex.printStackTrace();

				}
			}
			return null;
		}
	}

}
