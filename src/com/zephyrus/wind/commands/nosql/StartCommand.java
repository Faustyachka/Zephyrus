package com.zephyrus.wind.commands.nosql;

import javax.servlet.http.HttpServletRequest;						
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;
import com.zephyrus.wind.model.UserRole;												// REVIEW: unused import
																						// REVIEW: documentation expected
public class StartCommand implements Command {
																						// REVIEW: documentation expected
@Override
public String execute(HttpServletRequest request,
HttpServletResponse response) throws Exception {

	
	if (request.isUserInRole("ADMIN")==false && request.isUserInRole("INSTALLATION")==false &&			// REVIEW: check on every user role is a bad style. What if other role is added? use getUserPrincipal instead
			request.isUserInRole("PROVISION")==false && request.isUserInRole("SUPPORT")==false) {
		
		request.setAttribute("title", MessageManager.WELCOME);
		return PAGES.START_PAGE.getValue();
	}
	else {
		request.setAttribute("message", "Access to this page is prohibited.");
		return PAGES.MESSAGE_PAGE.getValue();
	}
	

}

}
