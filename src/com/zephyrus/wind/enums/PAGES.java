package com.zephyrus.wind.enums;
																							// REVIEW: documentation expected
public enum PAGES {
	LOGIN_PAGE("login"),
	HOME_PAGE("view/index.jsp"),
	MESSAGE_PAGE("view/message.jsp"),
	ADMIN_PAGE("admin/index.jsp"),
	SUPPORT_PAGE("support/index.jsp"),
	SUPPORT_VIEW_SO_PAGE("support/viewUserSO"),
	SUPPORT_VIEW_SI_PAGE("support/viewUserSI"),
	PROVISION_PAGE("provision/index.jsp"),
	INSTALLATION_PAGE("installation/index.jsp"),
	INSTALLATIONNEWWORKFLOW_PAGE("installation/newWorkflowTasks.jsp"),
	CREATECABLE_PAGE("installation/createcable.jsp"),
	CUSTOMERORDERS_PAGE("customer/index.jsp"),
	CUSTOMERSERVICES_PAGE("customer/customerServices.jsp"),
	REGISTER_PAGE("view/register.jsp"),
	REPORT_PAGE("view/report.jsp"),
	ORDERDETAIL_PAGE("customer/orderDetail.jsp");

	String value;
	PAGES(String page){
		value = page;
	}

	public String getValue(){
		return value;
	}
}
