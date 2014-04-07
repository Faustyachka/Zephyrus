package com.zephyrus.commands.nosql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.Pages;
import com.zephyrus.wind.managers.MessageManager;


public class HomeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("title", MessageManager.WELCOME);
        return Pages.HOME_PAGE.getValue();
	}
	
}
