package com.zephyrus.wind.email;

/**
 * This class represents email template for successful registration
 * @author Igor Litvinenko
 */
public class RegistrationSuccessfulEmail extends Email {
    
    /**
     * This constructor builds email template with specified user data.
     * @param firstName user's first name
     * @param login user's login
     * @param password user's password
     */
    public RegistrationSuccessfulEmail(String firstName, String login, String password) {
        
    	super("registrationSuccessful.html");
        
        subject = String.format("Congratulation, %s, you are registred!", firstName);
        message = String.format(message, firstName, login, password);
    }
}