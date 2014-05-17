package com.zephyrus.wind.commands.sql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.model.User;

/**
 * This class contains the method, that is declared in 								
 * com.zephyrus.wind.commands.interfaces.SQLCommand. It is supposed to write 
 * user to session after authorization and to redirect him on the home page.		
 * @author Bogdan Bondar
 */
public class LoginCommand extends SQLCommand {
	
	/**
	 * This method check is user's account blocked, write logged user into the session 
	 * and redirects user to home page which corresponds to his role.
	 *  
	 * @return String url of page for redirecting. It will be the page with order details
	 * for customer user, which select the service from services page, otherwise it will 
	 * be home page which corresponds to user's role.																					
	 */
	@Override
	public String doExecute(HttpServletRequest request,		
			HttpServletResponse response) throws Exception {
		String userName = request.getUserPrincipal().getName();
		IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
		User user = userDAO.findByEmail(userName);
		if(user != null){																		// REVIEW: user will be redirected to the home page, whether or not he was found in system?
			if(user.getStatus()==USER_STATUS.BLOCKED.getValue()){								
				request.logout();
				request.setAttribute("message", "Your account " + userName + "is blocked!");
				return PAGES.MESSAGE_PAGE.getValue();
			} 
			if (request.isUserInRole("ADMIN")==false && request.isUserInRole("INSTALLATION")==false &&		// REVIEW: not the best practice. Why don't change statement to isUserInRole("CUSTOMER")?
					request.isUserInRole("PROVISION")==false && request.isUserInRole("SUPPORT")==false) {
				if(request.getSession().getAttribute("user") == null)							// REVIEW: no braces + comments needed
					request.getSession().setAttribute("user", user);
				if(request.getSession().getAttribute("service") != null){
					return PAGES.ORDERDETAIL_PAGE.getValue();
				}
				for(ROLE role : ROLE.values()){												// REVIEW: this should be transfered to separate method
					if(user.getRole().getId() == role.getId()){
						response.sendRedirect(role.getHome());
						return null;
					}
				}
			}
			if (request.isUserInRole("ADMIN")==true || request.isUserInRole("INSTALLATION")==true ||		// REVIEW: ones more - why don't substitute it with != isUserInRole("CUSTOMER")? or simply write "else"
					request.isUserInRole("PROVISION")==true || request.isUserInRole("SUPPORT")==true) {
				
				if(request.getSession().getAttribute("user") == null) {
					request.getSession().setAttribute("user", user);
				}
				for(ROLE role : ROLE.values()){											// REVIEW: that's why method is needed
					if(user.getRole().getId() == role.getId()){
						response.sendRedirect(role.getHome());
						return null;
					}
				}
			}
		}
		
		return PAGES.HOME_PAGE.getValue();
	}

}
