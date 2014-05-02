package com.zephyrus.wind.email;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class provides basic structure for email templates
 * @author Igor Litvinenko
 */
public abstract class Email {

    protected String subject;
    protected String message;
    
    /**
     * This constructor builds given email template by
     * loading it into <code>message</code> field.
     * It is assumed that email templates are stored in 
     * <code>emailTemplates</code> folder.
     * @param template name of template to build
     */
    public Email(String template) {
        message = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
            		getClass().getClassLoader().getResourceAsStream("emailTemplates/" + template)));
            String line = br.readLine();
            while(line != null) {
                message += line;
                line = br.readLine();
            }
            br.close();
        } catch (Exception exc) {
            throw new RuntimeException("Template build failed", exc);
        }
    }
    
    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
