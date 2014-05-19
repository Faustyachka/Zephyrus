package com.zephyrus.wind.managers;

import java.util.ResourceBundle;
																								
public class MessageManager {
	
    private static final String BUNDLE_NAME = "com.carrent.properties.messages";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    
    //Messages
    public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
    public static final String SERVLET_EXCEPTION_ERROR_MESSAGE = "SERVLET_EXCEPTION_ERROR_MESSAGE";
    public static final String IO_EXCEPTION_ERROR_MESSAGE="IO_EXCEPTION_ERROR_MESSAGE";
    public static final String SQL_ERROR_MESSAGE="SQL_ERROR_MESSAGE";
    public static final String COMMAND_ERROR_MESSAGE="COMMAND_ERROR_MESSAGE";
    public static final String DATE_PARSE_ERROR="DATE_PARSE_ERROR";
    public static final String SUCCESSFULL_ORDER="SUCCESSFULL_ORDER";
    public static final String SUCCESSFULL_REGISTER="SUCCESSFULL_REGISTER";
    public static final String ERROR_MESSAGE="ERROR_MESSAGE";
    public static final String WELCOME="WELCOME";
    
    //Labels
    public static final String HOME = "HOME";
    public static final String ERROR = "ERROR";
    public static final String ORDER = "ORDER";
    public static final String REGISTER_PAGE = "REGISTER_PAGE";
    public static final String CONTACTS = "CONTACTS";
    public static final String NOCOMMAND= "NOCOMMAND";
    																							
    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
