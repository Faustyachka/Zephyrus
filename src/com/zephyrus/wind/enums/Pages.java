package com.zephyrus.wind.enums;

public enum Pages {
	HOME_PAGE("index.jsp"),
    MESSAGE_PAGE("view/message.jsp"),
    ADMIN_PAGE("admin/index.jsp"),
    SUPPORT_PAGE("support/index.jsp"),
    PROVISION_PAGE("provision/index.jsp"),
    INSTALLATION_PAGE("installation/index.jsp"),
    CUSTOMER_PAGE("customer/index.jsp"),
    REGISTER_PAGE("view/register.jsp");
    
    String value;
    Pages(String page){
        value = page;
    }
    
    public String getValue(){
        return value;
    }
}
