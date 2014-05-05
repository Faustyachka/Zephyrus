package com.zephyrus.wind.managers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.zephyrus.wind.commands.interfaces.Command;
import com.zephyrus.wind.commands.nosql.AboutCommand;
import com.zephyrus.wind.commands.nosql.ContactsCommand;
import com.zephyrus.wind.commands.nosql.HomeCommand;
import com.zephyrus.wind.commands.nosql.LogoutCommand;
import com.zephyrus.wind.commands.nosql.NoCommand;
import com.zephyrus.wind.commands.nosql.RegisterPageCommand;
import com.zephyrus.wind.commands.nosql.ServicesCommand;
import com.zephyrus.wind.commands.nosql.StartCommand;
import com.zephyrus.wind.commands.sql.AdminCommand;
import com.zephyrus.wind.commands.sql.AssignTaskCommand;
import com.zephyrus.wind.commands.sql.BlockingUserCommand;
import com.zephyrus.wind.commands.sql.CancelOrderCommand;
import com.zephyrus.wind.commands.sql.CreateCableCommand;
import com.zephyrus.wind.commands.sql.CreateCircuitCommand;
import com.zephyrus.wind.commands.sql.CreateCircuitViewCommand;
import com.zephyrus.wind.commands.sql.CreateConnectionCommand;
import com.zephyrus.wind.commands.sql.CreateDeviceCommand;
import com.zephyrus.wind.commands.sql.CreateNewPassComand;
import com.zephyrus.wind.commands.sql.CreateUserCommand;
import com.zephyrus.wind.commands.sql.CustomerOrdersCommand;
import com.zephyrus.wind.commands.sql.CustomerServicesCommand;
import com.zephyrus.wind.commands.sql.CustomerSupportCommand;
import com.zephyrus.wind.commands.sql.DeleteCableCommand;
import com.zephyrus.wind.commands.sql.DeleteCircuitCommand;
import com.zephyrus.wind.commands.sql.DeleteCircuitViewCommand;
import com.zephyrus.wind.commands.sql.DeleteConnectionCommand;
import com.zephyrus.wind.commands.sql.DeleteConnectionPropertiesCommand;
import com.zephyrus.wind.commands.sql.DeviceCreationPropertiesCommand;
import com.zephyrus.wind.commands.sql.DisconnectOrdersCommand;
import com.zephyrus.wind.commands.sql.DisconnectServiceInstanceCommand;
import com.zephyrus.wind.commands.sql.DisplayTasksCommand;
import com.zephyrus.wind.commands.sql.GenerateReportCommand;
import com.zephyrus.wind.commands.sql.GetCSVDisconnectOrdersCommand;
import com.zephyrus.wind.commands.sql.GetCSVNewOrdersCommand;
import com.zephyrus.wind.commands.sql.GetCSVProfitabilityCommand;
import com.zephyrus.wind.commands.sql.GetCSVUtilizationCommand;
import com.zephyrus.wind.commands.sql.GetExcelDisconnectOrdersCommand;
import com.zephyrus.wind.commands.sql.GetExcelNewOrdersCommand;
import com.zephyrus.wind.commands.sql.GetExcelProfitabilityCommand;
import com.zephyrus.wind.commands.sql.GetExcelUtilizationCommand;
import com.zephyrus.wind.commands.sql.LoginCommand;
import com.zephyrus.wind.commands.sql.MappingCommand;
import com.zephyrus.wind.commands.sql.ModifyCircuitCommand;
import com.zephyrus.wind.commands.sql.ModifyCircuitViewCommand;
import com.zephyrus.wind.commands.sql.ModifyServiceCommand;
import com.zephyrus.wind.commands.sql.NewConnectionPropertiesCommand;
import com.zephyrus.wind.commands.sql.NewOrdersCommand;
import com.zephyrus.wind.commands.sql.OrdersAndInstancesDisplayingCommand;
import com.zephyrus.wind.commands.sql.ProceedOrderCommand;
import com.zephyrus.wind.commands.sql.ProfitabilityByMonthCommand;
import com.zephyrus.wind.commands.sql.ProviderLocationsDisplayingCommand;
import com.zephyrus.wind.commands.sql.RegisterCommand;
import com.zephyrus.wind.commands.sql.ReportChoosingCommand;
import com.zephyrus.wind.commands.sql.SaveOrderCommand;
import com.zephyrus.wind.commands.sql.SendOrderCommand;
import com.zephyrus.wind.commands.sql.ShowReportCommand;
import com.zephyrus.wind.commands.sql.TaskRedirectorCommand;
import com.zephyrus.wind.commands.sql.UtilizationReportCommand;
																										// REVIEW: documentation expected
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
	private static final String SENDORDER_COMMAND = "sendOrder";
	private static final String CUSTOMERORDERS_COMMAND = "customerOrders";
	private static final String CUSTOMERSERVICES_COMMAND = "customerServices";
	private static final String CREATE_ACCOUNT_COMMAND = "createaccount";
	private static final String BLOCKING_USER_COMMAND = "blocking";
	private static final String CREATE_NEW_PASSWORD_COMMAND = "createnewpass";		
	private static final String CUSTOMER_SUPPORT_COMMAND = "customersupport";
	private static final String CREATE_DEVICE_COMMAND = "createDevice";
	private static final String CREATE_CABLE_COMMAND = "createCable";
	private static final String SUPPORT_COMMAND = "support";
	private static final String INSTALLATION_COMMAND = "installation";
	private static final String PROVISION_COMMAND = "provision";
	private static final String ASSIGN_TASK_COMMAND = "assignTask";
	private static final String TASK_REDIRECTOR_COMMAND = "taskRedirector";
	private static final String GENERATEREPORT_COMMAND = "generateReport";
	private static final String CREATE_CIRCUIT_COMMAND = "createCircuit";
	private static final String CREATE_CONNECTION_COMMAND = "createConnection";
	private static final String DELETE_CONNECTION_COMMAND = "deleteConnection";
	private static final String DISCONNECT_CONNECTION_PROPERTIES_COMMAND = "disconnectConnectionProperties";
	private static final String NEW_CONNECTION_PROPERTIES_COMMAND = "newConnectionProperties";
	private static final String DEVICE_CREATION_PROPERTIES_COMMAND = "deviceCreationProperties";
	private static final String ORDERS_AND_SERVICES_COMMAND = "ordersAndServices";
	private static final String CREATE_CIRCUIT_VIEW_COMMAND = "createCircuitView";
	private static final String DELETE_CABLE_COMMAND = "deleteCable";
	private static final String DELETE_CIRCUIT_VIEW_COMMAND = "deleteCircuitView";
	private static final String DELETE_CIRCUIT_COMMAND = "deleteCircuit";
	private static final String DISCONNECT_SERVICE_INSTANCE = "disconnectServiceInstance";
	private static final String PROVIDERS_COMMAND = "providerLocations";
	private static final String CANCEL_ORDER_COMMAND = "cancelOrder";
	private static final String CONTACTS_COMMAND = "contacts";
	private static final String ABOUT_COMMAND = "about";
	private static final String SERVICES_COMMAND = "services";
	private static final String START_COMMAND = "start";
	private static final String MODIFY_SERVICE_COMMAND = "modifyService";
	private static final String REPORTS_CHOOSING_COMMAND = "reportChoosing";
	private static final String SHOW_REPORT_COMMAND = "showReport";
	private static final String NEW_ORDERS_REPORT_COMMAND = "newOrders";
	private static final String GET_EXCEL_NEW_ORDERS_COMMAND = "getExcelNewOrders";
	private static final String GET_CSV_NEW_ORDERS_COMMAND = "getCSVNewOrders";
	private static final String DISCONNECT_ORDERS_REPORT_COMMAND = "disconnectOrders";
	private static final String GET_EXCEL_DISCONNECT_ORDERS_COMMAND = "getExcelDisconnectOrders";
	private static final String GET_CSV_DISCONNECT_ORDERS_COMMAND = "getCSVDisconnectOrders";
	private static final String PROFITABILITY_REPORT_COMMAND = "profitabilityReport";
	private static final String GET_EXCEL_PROFITABILITY_COMMAND = "getExcelProfitability";
	private static final String GET_CSV_PROFITABILITY_COMMAND = "getCSVProfitability";
	private static final String MODIFY_CIRCUIT_VIEW_COMMAND = "modifyCircuitView";
	private static final String MODIFY_CIRCUIT_COMMAND = "modifyCircuit";
	private static final String UTILIZATION_REPORT_COMMAND = "utilizationReport";
	private static final String GET_EXCEL_UTILIZATION_COMMAND = "getUtilizationExcel";
	private static final String GET_CSV_UTILIZATION_COMMAND = "getUtilizationCSV";


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
		commands.put(CUSTOMERORDERS_COMMAND, new CustomerOrdersCommand());
		commands.put(CUSTOMERSERVICES_COMMAND, new CustomerServicesCommand());
		commands.put(SENDORDER_COMMAND, new SendOrderCommand());
		commands.put(CREATE_ACCOUNT_COMMAND, new CreateUserCommand());
		commands.put(BLOCKING_USER_COMMAND, new BlockingUserCommand());
		commands.put(CREATE_NEW_PASSWORD_COMMAND, new CreateNewPassComand());
		commands.put(CUSTOMER_SUPPORT_COMMAND, new CustomerSupportCommand());
		commands.put(CREATE_DEVICE_COMMAND, new CreateDeviceCommand());
		commands.put(CREATE_CABLE_COMMAND, new CreateCableCommand());
		commands.put(GENERATEREPORT_COMMAND, new GenerateReportCommand());
		commands.put(SUPPORT_COMMAND, new DisplayTasksCommand());
		commands.put(INSTALLATION_COMMAND, new DisplayTasksCommand());
		commands.put(PROVISION_COMMAND, new DisplayTasksCommand());
		commands.put(TASK_REDIRECTOR_COMMAND, new TaskRedirectorCommand());
		commands.put(ASSIGN_TASK_COMMAND, new AssignTaskCommand());
		commands.put(CREATE_CIRCUIT_COMMAND, new CreateCircuitCommand());
		commands.put(CREATE_CONNECTION_COMMAND, new CreateConnectionCommand());
		commands.put(DELETE_CONNECTION_COMMAND, new DeleteConnectionCommand());
		commands.put(DISCONNECT_CONNECTION_PROPERTIES_COMMAND, new DeleteConnectionPropertiesCommand());
		commands.put(NEW_CONNECTION_PROPERTIES_COMMAND, new NewConnectionPropertiesCommand());
		commands.put(DEVICE_CREATION_PROPERTIES_COMMAND, new DeviceCreationPropertiesCommand());
		commands.put(ORDERS_AND_SERVICES_COMMAND, new OrdersAndInstancesDisplayingCommand());
		commands.put(CREATE_CIRCUIT_VIEW_COMMAND, new CreateCircuitViewCommand());
		commands.put(DELETE_CABLE_COMMAND, new DeleteCableCommand());
		commands.put(DELETE_CIRCUIT_COMMAND, new DeleteCircuitCommand());
		commands.put(DELETE_CIRCUIT_VIEW_COMMAND, new DeleteCircuitViewCommand());
		commands.put(DISCONNECT_SERVICE_INSTANCE, new DisconnectServiceInstanceCommand());
		commands.put(PROVIDERS_COMMAND, new ProviderLocationsDisplayingCommand());
		commands.put(CANCEL_ORDER_COMMAND, new CancelOrderCommand());
		commands.put(ABOUT_COMMAND, new AboutCommand());
		commands.put(SERVICES_COMMAND, new ServicesCommand());
		commands.put(CONTACTS_COMMAND, new ContactsCommand());
		commands.put(START_COMMAND, new StartCommand());
		commands.put(MODIFY_SERVICE_COMMAND, new ModifyServiceCommand());
		commands.put(REPORTS_CHOOSING_COMMAND, new ReportChoosingCommand());
		commands.put(SHOW_REPORT_COMMAND, new ShowReportCommand());
		commands.put(NEW_ORDERS_REPORT_COMMAND, new NewOrdersCommand());
		commands.put(GET_EXCEL_NEW_ORDERS_COMMAND, new GetExcelNewOrdersCommand());
		commands.put(GET_CSV_NEW_ORDERS_COMMAND, new GetCSVNewOrdersCommand());
		commands.put(DISCONNECT_ORDERS_REPORT_COMMAND, new DisconnectOrdersCommand());
		commands.put(GET_EXCEL_DISCONNECT_ORDERS_COMMAND, new GetExcelDisconnectOrdersCommand());
		commands.put(GET_CSV_DISCONNECT_ORDERS_COMMAND, new GetCSVDisconnectOrdersCommand());
		commands.put(PROFITABILITY_REPORT_COMMAND, new ProfitabilityByMonthCommand());
		commands.put(GET_EXCEL_PROFITABILITY_COMMAND, new GetExcelProfitabilityCommand());
		commands.put(GET_CSV_PROFITABILITY_COMMAND, new GetCSVProfitabilityCommand());
		commands.put(MODIFY_CIRCUIT_COMMAND, new ModifyCircuitCommand());
		commands.put(MODIFY_CIRCUIT_VIEW_COMMAND, new ModifyCircuitViewCommand());
		commands.put(UTILIZATION_REPORT_COMMAND, new UtilizationReportCommand());
		commands.put(GET_EXCEL_UTILIZATION_COMMAND, new GetExcelUtilizationCommand());
		commands.put(GET_CSV_UTILIZATION_COMMAND, new GetCSVUtilizationCommand());

	}
																										// REVIEW: documentation expected
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
																										// REVIEW: documentation expected
	public Command getCommand(String action) {
		Command command = commands.get(action);
		if (command == null) {
			// if command doesn't exist
			command = new NoCommand();
		}
		return command;
	}

}
