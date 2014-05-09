package com.zephyrus.wind.commands.sql;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.helpers.SHAHashing;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in @link							// REVIEW: link isn't working
 * #com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to change
 * password to the Customer User by Customer Support Engineer.
 * 
 * @return in success case the page of changing password confirmation.					// REVIEW: return
 * 
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
			request.setAttribute("errorMessage", "You should login under "			// REVIEW: HTML
					+ "Support's account to view this page!<br>"
					+ " <a href='/Zephyrus/view/login.jsp'><input type='"
					+ "button' class='button' value='Login'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		int userId;

		if (request.getParameter("userId") == null) {								// REVIEW: you have user attribute and userId param. Why do you need both?
			request.setAttribute(
					"errorMessage",
					"Such user does not exist.<br>"
							+ "<a href='/Zephyrus/support/index.jsp'> <input type='"	// REVIEW: HTML
							+ "button' class='button' value='Home'/> </a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			request.setAttribute("errorMessage", "User ID is not valid. "
					+ "You must choose user from the table!<br>"
					+ "<a href='/Zephyrus/support/index.jsp'><input type='"
					+ "button' class='button' value='Home'/></a>");
			return PAGES.MESSAGE_PAGE.getValue();
		}

		IUserDAO dao = getOracleDaoFactory().getUserDAO();
		User user = dao.findById(userId);
		if (user == null) {
			request.setAttribute("error", "User does not exist");
			return "support/changepass.jsp";
		}
		String pass = request.getParameter("pass");
		String confPass = request.getParameter("confpass");
		if (pass.equals("")) {
			request.setAttribute("error", "Password can not be empty");
			request.setAttribute("userId", userId);
			return "support/changepass.jsp";
		}
		if (!(pass.equals(confPass))) {
			request.setAttribute("error", "Passwords do not coincide");
			request.setAttribute("userId", userId);
			return "support/changepass.jsp"; // TODO javascript checking
		}
		String password = SHAHashing.getHash(pass);
		user.setPassword(password);

		dao.update(user);
		request.setAttribute("message",
				"Password changed <br> <a href='/Zephyrus/customersupport'><input type='"	// REVIEW: HTML
						+ "button' class='button' value='Home'/></a>");
		return PAGES.MESSAGE_PAGE.getValue();

	}

}
