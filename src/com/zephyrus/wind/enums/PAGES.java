package com.zephyrus.wind.enums;
																							
public enum PAGES {
	LOGIN_PAGE("login"),
	HOME_PAGE("view/index.jsp"),
	MESSAGE_PAGE("view/message.jsp"),
	ADMIN_PAGE("admin/index.jsp"),
	SUPPORT_PAGE("support/index.jsp"),
	PROVISION_PAGE("provision/index.jsp"),
	INSTALLATION_PAGE("installation/index.jsp"),
	INSTALLATIONNEWWORKFLOW_PAGE("installation/newWorkflowTasks.jsp"),
	INSTALLATIONDISCONNECTWORKFLOW_PAGE("installation/disconnectWorkflowTasks.jsp"),
	CREATECABLE_PAGE("installation/createcable.jsp"),
	CUSTOMERORDERS_PAGE("customer/index.jsp"),
	CUSTOMERSERVICES_PAGE("customer/customerServices.jsp"),
	REGISTER_PAGE("view/register.jsp"),
	REPORT_PAGE("view/report.jsp"),
	ORDERDETAIL_PAGE("customer/orderDetail.jsp"),
	CREATEDEVICE_PAGE("installation/createDevice.jsp"),
	CONTACTS_PAGE("view/contacts.jsp"),
	SERVICES_PAGE("view/services.jsp"),
	ABOUT_PAGE("view/about.jsp"),
	START_PAGE("view/start.jsp"),
	MODIFYSERVICE_PAGE("customer/modifyService.jsp"),
	AUTHORIZE_PAGE("view/authorize.jsp");

	String value;
	PAGES(String page){
		value = page;
	}

	public String getValue(){
		return value;
	}
}
