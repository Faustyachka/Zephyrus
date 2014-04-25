package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.model.User;
/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */
public class AdminCommand extends SQLCommand  {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {		   
		IDAO<User> dao = getOracleDaoFactory().getUserDAO();
		ArrayList<User> users = dao.findAll();
		request.setAttribute("users", users);
		return PAGES.ADMIN_PAGE.getValue();
	}
}
