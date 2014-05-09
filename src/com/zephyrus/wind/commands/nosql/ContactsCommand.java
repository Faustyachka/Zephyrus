package com.zephyrus.wind.commands.nosql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.enums.PAGES;
import com.zephyrus.wind.managers.MessageManager;
																					//REVIEW: Is this class used anywhere? + documentation expected
public class ContactsCommand implements Command {
																					// REVIEW: documentation expected
@Override
public String execute(HttpServletRequest request,
HttpServletResponse response) throws Exception {

request.setAttribute("title", MessageManager.WELCOME);
return PAGES.CONTACTS_PAGE.getValue();
}

}
