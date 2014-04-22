package com.zephyrus.wind.enums;

public enum Pages {
	HOME_PAGE("index.jsp"),
    MESSAGE_PAGE("view/message.jsp"),
    ADMIN_PAGE("admin/index.jsp"),
    SUPPORT_PAGE("support/index.jsp"),
    SUPPORT_VIEW_SO_PAGE("support/viewUserSO"),
    SUPPORT_VIEW_SI_PAGE("support/viewUserSI"),
    PROVISION_PAGE("provision/index.jsp"),
    INSTALLATION_PAGE("installation/index.jsp"),
    CUSTOMER_PAGE("customer/index.jsp"),
    REGISTER_PAGE("view/register.jsp"),
    REPORT_PAGE("view/report.jsp");
    
    String value;
    Pages(String page){
        value = page;
    }
    
    public String getValue(){
        return value;
    }
}
