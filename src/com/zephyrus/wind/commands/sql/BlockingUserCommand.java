package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.model.User;

/**
 *  The BlockingUserCommand class provide users blocking by administrator. 
 *  Get from the jsp user id and change his status (blocked or unblocked) on opposite.  			
 * @author Alexandra Beskorovaynaya
 *
 */
public class BlockingUserCommand extends SQLCommand {
	/**
	 * @return String url of page for redirecting.
	 * Always returns null because of it is no need to redirect on message 
	 * page after each blocking/unblocking. In administrator's jsp ajax query
	 * is used for blocking/unblocking. 
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {
		
		int userId = Integer.parseInt(request.getParameter("id"));
		IDAO<User> dao = getOracleDaoFactory().getUserDAO();
		User user = dao.findById(userId);		
		if (user.getStatus().equals(USER_STATUS.BLOCKED.getValue())) {								
			user.setStatus(USER_STATUS.ACTIVE.getValue());												
		} else {
			user.setStatus(USER_STATUS.BLOCKED.getValue());											
		}
		dao.update(user);
		return null;																				
	}

}
