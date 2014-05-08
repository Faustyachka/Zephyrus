package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;
import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IDAO;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.managers.LockManager;
import com.zephyrus.wind.model.User;

/**
 * The BlockingUserCommand class provide users blocking by administrator. Get
 * from the jsp user id and change his status (blocked or unblocked) on
 * opposite.
 * 
 * @author Alexandra Beskorovaynaya
 * 
 */
public class BlockingUserCommand extends SQLCommand {
	/**
	 * This method receives the id of user, whose status must be changed to the
	 * opposite. After the status changing or in the case of error the alert on
	 * the administrator's jsp displays.
	 * 
	 * @return String url of page for redirecting. Always returns null because
	 *         it is no need to redirect on message page after each
	 *         blocking/unblocking. In administrator's jsp ajax query is used
	 *         for blocking/unblocking.
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		int userId;
		int status;

		// check the presence of user's id
		if (request.getParameter("id") == null
				|| request.getParameter("status") == null) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Please, choose user from table!");
			return null;
		}
		try {
			userId = Integer.parseInt(request.getParameter("id"));
			status = Integer.parseInt(request.getParameter("status"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(
					"Input parameters are not valid! Choose user from table!");
			return null;
		}

		IDAO<User> dao = getOracleDaoFactory().getUserDAO();
		User user = dao.findById(userId);

		// check the presence of user with such id in DB
		if (user == null) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("User doesn't exist!");
			return null;
		}

		Lock lock = LockManager.getLock(user.getId());
		lock.lock();
		try {
			if (status == user.getStatus()) {
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("User's statys already changed!");
				return null;
			}

			// change the user's status to opposite
			if (user.getStatus().equals(USER_STATUS.BLOCKED.getValue())) {
				user.setStatus(USER_STATUS.ACTIVE.getValue());
			} else {
				user.setStatus(USER_STATUS.BLOCKED.getValue());
			}
			dao.update(user);
			
			// return the message about successful status changing
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("success");
			return null;
		} finally {
			lock.unlock();
		}
		
	}

}
