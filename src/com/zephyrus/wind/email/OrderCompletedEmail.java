package com.zephyrus.wind.email;

import java.util.Date;


/**
 * This class represents email template for notification
 * about completed order
 * @author Igor Litvinenko
 */
public class OrderCompletedEmail extends Email {
    
    /**
     * This constructor builds email template with specified user data.
     * @param orderDate date of order
     * @param userName first name of user that placed order
     * @param orderType scenario the order was created for
     * @param serviceLocation location of provided service
     */
    public OrderCompletedEmail(Date orderDate, String userName, 
    		String orderType, String serviceLocation) {
        
    	super("taskAvailable.html");
        
        subject = String.format("Your order has been completed");
        message = String.format(message, orderDate, userName, orderType, serviceLocation);
    }
}
