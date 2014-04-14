package com.zephyrus.wind.enums;

public enum ROLE {
	ADMIN(Pages.ADMIN_PAGE.getValue(),1),
    SUPPORT(Pages.SUPPORT_PAGE.getValue(),2),
    PROVISION(Pages.PROVISION_PAGE.getValue(),3),
    INSTALLATION(Pages.INSTALLATION_PAGE.getValue(),4),
    CUSTOMER(Pages.CUSTOMER_PAGE.getValue(),5);
    
    String home;
    Integer id;
    ROLE(String page, Integer id){
        home = page;
        this.id = id;
    }
    
    public String getHome(){
        return home;
    }
    
    public Integer getId(){
        return id;
    }
}
