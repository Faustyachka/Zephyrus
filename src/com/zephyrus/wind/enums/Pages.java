package com.zephyrus.wind.enums;

public enum Pages {
	HOME_PAGE("/view/home.jsp"),
    MESSAGE_PAGE("/view/message.jsp");
    
    String value;
    Pages(String page){
        value = page;
    }
    
    public String getValue(){
        return value;
    }
}
