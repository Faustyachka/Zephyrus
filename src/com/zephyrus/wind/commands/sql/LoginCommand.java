package com.zephyrus.wind.commands.sql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.SQLCommand;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.enums.ROLE;
import com.zephyrus.wind.enums.USER_STATUS;
import com.zephyrus.wind.model.User;
																								// REVIEW: documentation expected
public class LoginCommand extends SQLCommand {

	@Override
	public String doExecute(HttpServletRequest request,											// REVIEW: public doExecute
			HttpServletResponse response) throws Exception {
		String userName = request.getUserPrincipal().getName();
		IUserDAO userDAO = getOracleDaoFactory().getUserDAO();
		User user = userDAO.findByEmail(userName);
		if(user != null){
			if(user.getStatus()==USER_STATUS.BLOCKED.getValue()){								
				request.logout();
				request.setAttribute("message", "Your account " + userName + "is blocked!");
				return PAGES.MESSAGE_PAGE.getValue();
			} 
			if (request.isUserInRole("ADMIN")==false && request.isUserInRole("INSTALLATION")==false &&
					request.isUserInRole("PROVISION")==false && request.isUserInRole("SUPPORT")==false) {
				if(request.getSession().getAttribute("user") == null)
					request.getSession().setAttribute("user", user);
				if(request.getSession().getAttribute("service") != null){
					return PAGES.ORDERDETAIL_PAGE.getValue();
				}
				for(ROLE role : ROLE.values()){
					if(user.getRole().getId() == role.getId()){
						response.sendRedirect(role.getHome());
						return null;
					}
				}
			}
			if (request.isUserInRole("ADMIN")==true || request.isUserInRole("INSTALLATION")==true ||
					request.isUserInRole("PROVISION")==true || request.isUserInRole("SUPPORT")==true) {
				
				if(request.getSession().getAttribute("user") == null) {
					request.getSession().setAttribute("user", user);
				}
				for(ROLE role : ROLE.values()){
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
