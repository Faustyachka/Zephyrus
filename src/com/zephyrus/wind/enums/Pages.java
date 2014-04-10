package com.zephyrus.wind.enums;

public enum Pages {
	HOME_PAGE("index.html"),
    MESSAGE_PAGE("view/message.jsp"),
    ADMIN_PAGE("admin/index.jsp");
    
    String value;
    Pages(String page){
        value = page;
    }
    
    public String getValue(){
        return value;
    }
}
