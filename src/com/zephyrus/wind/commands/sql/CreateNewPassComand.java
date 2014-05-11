package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.MessageNumber;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in 		
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to change
 * password to the Customer User by Customer Support Engineer.
 * @author Alexandra Beskorovaynaya
 */
public class CreateNewPassComand extends SQLCommand {

	/**
	 * This method allows to change password for Customer User by Customer
	 * Support Engineer. It check's the correctness of input data and if
	 * everything is OK changes the password to given user.
	 * 
	 * @return in success case the page of changing password confirmation. In
	 *         the case of error returns the page of new password input with the
	 *         message about error.
	 * 
	 */
	@Override
	protected String doExecute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, Exception {

		User support = (User) request.getSession().getAttribute("user");

		// checking is user authorized
		if (support == null
				|| support.getRole().getId() != ROLE.SUPPORT.getId()) {
			request.setAttribute("messageNumber", MessageNumber.LOGIN_SUPPORT_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		int userId;

		if (request.getParameter("userId") == null) {	
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}

		IUserDAO dao = getOracleDaoFactory().getUserDAO();
		User user = dao.findById(userId);
		if (user == null) {
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		String pass = request.getParameter("password");
		String confPass = request.getParameter("confirmpass");
		
		if (pass==null||confPass==null) {
			request.setAttribute("messageNumber", MessageNumber.USER_ID_ERROR.getId());
			return PAGES.MESSAGE_PAGE.getValue();
		}
		
		if (pass.equals("")) {
			request.setAttribute("error", "Password can not be empty");
			request.setAttribute("userId", userId);
			return "support/changepass.jsp";
		}
		if (!(pass.equals(confPass))) {
			request.setAttribute("error", "Passwords do not coincide");
			request.setAttribute("userId", userId);
			return "support/changepass.jsp"; 
		}
		String password = SHAHashing.getHash(pass);
		user.setPassword(password);

		dao.update(user);
		request.setAttribute("messageNumber", MessageNumber.PASSWORD_CHANGED_MESSAGE.getId());
		return PAGES.MESSAGE_PAGE.getValue();

	}

}
