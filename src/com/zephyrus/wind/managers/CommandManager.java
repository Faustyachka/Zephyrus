package com.zephyrus.wind.managers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.commands.nosql.HomeCommand;
import com.zephyrus.wind.commands.nosql.LogoutCommand;
import com.zephyrus.wind.commands.nosql.NoCommand;
import com.zephyrus.wind.commands.nosql.RegisterPageCommand;
import com.zephyrus.wind.commands.sql.AdminCommand;
import com.zephyrus.wind.commands.sql.BlockingUserCommand;
import com.zephyrus.wind.commands.sql.CreateNewPassComand;
import com.zephyrus.wind.commands.sql.CreateUserCommand;
import com.zephyrus.wind.commands.sql.CustomerSupportCommand;
import com.zephyrus.wind.commands.sql.GenerateReportCommand;
import com.zephyrus.wind.commands.sql.LoginCommand;
import com.zephyrus.wind.commands.sql.MappingCommand;
import com.zephyrus.wind.commands.sql.ProceedOrderCommand;
import com.zephyrus.wind.commands.sql.RegisterCommand;
import com.zephyrus.wind.commands.sql.SaveOrderCommand;

public class CommandManager {

	HashMap<String, Command> commands = new HashMap<String, Command>();

	private static final String HOME_COMMAND = "home";
	private static final String ADMIN_COMMAND = "admin";
	private static final String LOGIN_COMMAND = "login";
	private static final String LOGOUT_COMMAND = "logout";
	private static final String MAPPING_COMMAND = "mapping";
	private static final String REGISTER_COMMAND = "register";
	private static final String REGISTERPAGE_COMMAND = "registerPage";
	private static final String PROCEEDORDER_COMMAND = "proceedOrder";
	private static final String SAVEORDER_COMMAND = "saveOrder";

	private static final String CREATE_ACCOUNT_COMMAND = "createaccount";
	private static final String BLOCKING_USER_COMMAND = "blocking";
	private static final String REASSIGN_COMMAND = "reassign";
	private static final String CREATE_NEW_PASSWORD_COMMAND = "createnewpass";
	private static final String REVIEW_USER_COMMAND = "review";
	private static final String CUSTOMER_SUPPORT_COMMAND = "customersupport";
	private static final String CREATE_DEVICE_COMMAND = "createdevice";
	
	

	private static final String GENERATEREPORT_COMMAND = "generateReport";


	public CommandManager() {

		// filling the table with commands
		commands.put(HOME_COMMAND, new HomeCommand());
		commands.put(ADMIN_COMMAND, new AdminCommand());
		commands.put(LOGIN_COMMAND, new LoginCommand());
		commands.put(LOGOUT_COMMAND, new LogoutCommand());
		commands.put(MAPPING_COMMAND, new MappingCommand());
		commands.put(REGISTER_COMMAND, new RegisterCommand());
		commands.put(REGISTERPAGE_COMMAND, new RegisterPageCommand());
		commands.put(PROCEEDORDER_COMMAND, new ProceedOrderCommand());
		commands.put(SAVEORDER_COMMAND, new SaveOrderCommand());

		commands.put(CREATE_ACCOUNT_COMMAND, new CreateUserCommand());
		commands.put(BLOCKING_USER_COMMAND, new BlockingUserCommand());
		commands.put(CREATE_NEW_PASSWORD_COMMAND, new CreateNewPassComand());
		commands.put(CUSTOMER_SUPPORT_COMMAND, new CustomerSupportCommand());

		commands.put(GENERATEREPORT_COMMAND, new GenerateReportCommand());


	}

	public Command getCommand(HttpServletRequest request) {

		String action = null;
		// geting command out if request
		if (request.getParameter("command") != null) {
			action = request.getParameter("command");
		} else {
			action = request.getServletPath();
			action = action.substring(1);
		}
		// to know what action was performed
		request.getSession().setAttribute("view", action);
		// receiving the object of Command
		return getCommand(action);
	}

	public Command getCommand(String action) {
		Command command = commands.get(action);
		if (command == null) {
			// if command doesn't exist
			command = new NoCommand();
		}
		return command;
	}

}
