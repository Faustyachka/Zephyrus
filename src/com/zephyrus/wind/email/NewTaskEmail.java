package com.zephyrus.wind.email;

import java.util.Date;

/**
 * This class represents email template for notification
 * about new task in system
 * @author Igor Litvinenko
 */
public class NewTaskEmail extends Email {
    
    /**
     * This constructor builds email template with specified user data.
     * @param orderDate date of order
     * @param firstName first name of user that placed order
     * @param lastName last name of user that placed order
     * @param taskID ID of task
     */
    public NewTaskEmail(Date orderDate, String firstName, 
    		String lastName, int taskID) {
        
    	super("taskAvailable.html");
        
        subject = String.format("New task available");
        message = String.format(message, orderDate, firstName, lastName, taskID);
    }
}
