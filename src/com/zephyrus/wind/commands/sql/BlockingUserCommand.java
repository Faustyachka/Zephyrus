package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.model.User;

/**
 * 																									// REVIEW: documentation expected
 * @author Alexandra Beskorovaynaya
 *
 */
public class BlockingUserCommand extends SQLCommand {

	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int userId = Integer.parseInt(request.getParameter("id"));
		IDAO<User> dao = getOracleDaoFactory().getUserDAO();
		User user = dao.findById(userId);		
		if (user.getStatus().equals(USER_STATUS.BLOCKED.geValue())) {								// REVIEW: typo: geValue method
		user.setStatus(USER_STATUS.ACTIVE.geValue());												// REVIEW: wrong formatting
		} else {
			user.setStatus(USER_STATUS.BLOCKED.geValue());											// REVIEW: typo: geValue method
		}
		dao.update(user);
		return null;																				// REVIEW: null always returned
	}

}
