package com.zephyrus.wind.commands.sql;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.model.User;

public class BlockingUserCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int userId = Integer.parseInt(request.getParameter("id"));
		IDAO<User> dao = oracleDaoFactory.getUserDAO();
		User user = dao.findById(userId);		
		if (user.getStatus().equals(new BigDecimal(1))) {
		user.setStatus(new BigDecimal(0));
		} else {
			user.setStatus(new BigDecimal(1));
		}
		dao.update(user);
		return null;
		//TODO
	}

}
