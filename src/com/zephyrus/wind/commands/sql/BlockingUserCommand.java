package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.UserStatus;
import com.zephyrus.wind.model.User;

/**
 * 
 * @author Alexandra Beskorovaynaya
 *
 */
public class BlockingUserCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int userId = Integer.parseInt(request.getParameter("id"));
		IDAO<User> dao = oracleDaoFactory.getUserDAO();
		User user = dao.findById(userId);		
		if (user.getStatus().equals(UserStatus.BLOCKED.geValue())) {
		user.setStatus(UserStatus.ACTIVE.geValue());
		} else {
			user.setStatus(UserStatus.BLOCKED.geValue());
		}
		dao.update(user);
		return null;
	}

}
