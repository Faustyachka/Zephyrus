package com.zephyrus.wind.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zephyrus.wind.dao.factory.OracleDAOFactory;
import com.zephyrus.wind.dao.interfaces.IUserDAO;
import com.zephyrus.wind.enums.ROLE;

/**
 * This class provides functionality for email sending
 * @author Igor Litvinenko
 */
public class EmailSender {
	
	/** SMTP server properties */
    private static Properties props;
    private Session session;
    
    public EmailSender() throws IOException {
    	InputStream input = getClass().getResourceAsStream("email.properties");
    	props = new Properties();
    	props.load(input);
    	
    	session = getAuthSession();
    }
    
    /**
     * This method sends email to specified user
     * @param emailAddress email address to send email to
     * @param mail Email to send
     */
    public void sendEmail(String emailAddress, Email email) {
    	Address address = null;
    	try {
            address = new InternetAddress(emailAddress);
        } catch (AddressException exc) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.WARNING, null, exc);
            return;
        }

        /* Send mail */
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mail.systemaddress")));
            message.setRecipient(Message.RecipientType.BCC, address);
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage(), "text/html");
            Transport.send(message);
        } catch (MessagingException exc) {
        	Logger.getLogger(EmailSender.class.getName()).log(Level.WARNING, null, exc);
        }
    }

    /**
     * This method sends emails to all users of specified user group
     * @param role user group to send emails to
     * @param mail message to send
     */
    public void sendEmail(ROLE role, Email mail) {
        int index = 1;
        /*
         * Variable is used to load recipient addresses several in a row,
         * because in requires less queries to DB
         */
        int maxNumberToSendInRow = 100;
        List<String> addresses;
        do {
        	addresses = getGroupAddresses(role, index, maxNumberToSendInRow);
        	index += maxNumberToSendInRow;
        	
        	if(!addresses.isEmpty()) {
        		for(int i = 0; i < addresses.size(); i++) {
            		sendEmail(addresses.get(i), mail);
            	}
        	}
        } while(addresses.size() == maxNumberToSendInRow);
    }
    
    /**
     * This method obtains email addresses of users of specified user group
     * in specified range
     * @param role user group to get addresses for
     * @param firstRow first index of range of user emails
     * @param count number of records in range of user emails
     * @return array of email addresses
     */
    private List<String> getGroupAddresses(ROLE role, int firstRow, int count) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        List<String> addressList = null;
        try {
        	factory.beginConnection();
            IUserDAO userDAO = factory.getUserDAO();
            addressList = userDAO.getGroupEmails(role, firstRow, count);
            return addressList;
        } catch (Exception exc) {
        	Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, exc);
        	throw new RuntimeException("DAO exception", exc);
		} finally {
            factory.endConnection();
        }
    }
    
    /**
     * This method authenticates into SMTP server using
     * provided auth properties
     * @return auth session
     */
    private Session getAuthSession() {
    	Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.user"), 
                								  props.getProperty("mail.password"));
            }
    	});
    	
    	return session;
    }
}
