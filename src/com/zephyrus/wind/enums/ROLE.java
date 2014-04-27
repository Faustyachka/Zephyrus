package com.zephyrus.wind.enums;
																									// REVIEW: documentation expected
public enum ROLE {
	ADMIN("/Zephyrus/admin",1),
    SUPPORT("/Zephyrus/support",2),
    PROVISION("/Zephyrus/provision",3),
    INSTALLATION("/Zephyrus/installation",4),
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
    
    /**
     * Method for taking of users index page
     * @return index page for defined user role
     */
    public String getIndexPage(){
    	return home + "/index.jsp";
    }
}
