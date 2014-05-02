package com.zephyrus.wind.email;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Igor Litvinenko
 */
public class Email {

    protected String subject;
    protected String message;

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
