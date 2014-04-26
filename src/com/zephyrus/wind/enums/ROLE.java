package com.zephyrus.wind.enums;
																									// REVIEW: documentation expected
public enum ROLE {
	ADMIN("/Zephyrus/admin",1),
    SUPPORT(PAGES.SUPPORT_PAGE.getValue(),2),
    PROVISION(PAGES.PROVISION_PAGE.getValue(),3),
    INSTALLATION(PAGES.INSTALLATION_PAGE.getValue(),4),
    CUSTOMER("/Zephyrus/customerOrders",5);
    
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
