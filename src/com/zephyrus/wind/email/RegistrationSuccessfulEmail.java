package com.zephyrus.wind.email;

/**
 *
 * @author 
 */
public class RegistrationSuccessfulEmail extends Email {

    private String firstName;
    private String login;
    private String password;
    
    public RegistrationSuccessfulEmail(String firstName, 
					String login, String password) {
        
    	super("registrationSuccessful.html");
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        
        subject = String.format("Congratulation, %s, you are registred!", firstName);
        message = String.format(message, firstName, login, password);
    }
}