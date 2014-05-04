package com.zephyrus.wind.commands.nosql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;

public class AboutCommand implements Command {
	// REVIEW: documentation expected
@Override
public String execute(HttpServletRequest request,
HttpServletResponse response) throws Exception {

request.setAttribute("title", MessageManager.WELCOME);
return PAGES.ABOUT_PAGE.getValue();
}

}
