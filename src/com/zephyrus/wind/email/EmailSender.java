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
import com.zephyrus.wind.model.User;

/**
 * This class provides functionality for email sending
 * @author Igor Litvinenko
 */
public class EmailSender {
	
	/** SMTP server properties */
    private static Properties props;

    public EmailSender() throws IOException {
    	InputStream input = getClass().getResourceAsStream("email.properties");
    	props = new Properties();
    	props.load(input);
    }
    
    /**
     * This method sends email to specified user
     * @param user User to send email to
     * @param mail Email to send
     */
    public void sendEmail(User user, Email email) {
    	Session session = getAuthSession();
        Address address = getUserAddress(user);

        /* Send mail */
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mail.user")));
            message.setRecipient(Message.RecipientType.BCC, address);
            message.setSubject(email.getSubject());
            message.setContent(email.getMessage(), "text/html");
            Transport.send(message);
        } catch (MessagingException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * This method sends emails to all users of specified user group
     * @param role user group to send emails to
     * @param mail message to send
     */
    public void sendEmail(ROLE role, Email mail) {
        Session session = getAuthSession();
        
        int index = 1;
        int maxNumberToSend = Integer.parseInt(props.getProperty("mail.maxaddressesinrow"));
        Address[] addresses;
        do {
        	addresses = getGroupAddresses(role, index, maxNumberToSend);
        	index += maxNumberToSend;
        	
        	if(addresses.length != 0) {
        		/* Send mail */
    	        try {
    	            Message message = new MimeMessage(session);
    	            message.setFrom(new InternetAddress(props.getProperty("mail.user")));
    	            message.setRecipients(Message.RecipientType.BCC, addresses);
    	            message.setSubject(mail.getSubject());
    	            message.setContent(mail.getMessage(), "text/html");
    	            Transport.send(message);
    	        } catch (MessagingException e) {
    	            throw new RuntimeException(e);
    	        }
        	}
	        
        } while(addresses.length == maxNumberToSend);
    }
    
    /**
     * This method obtains email addresses of users of specified user group
     * in specified range
     * @param role user group to get addresses for
     * @param firstRow first index of range of user emails
     * @param count number of records in range of user emails
     * @return array of email addresses
     */
    private Address[] getGroupAddresses(ROLE role, int firstRow, int count) {
    	OracleDAOFactory factory = new OracleDAOFactory();
        List<String> addressList = null;
        try {
        	factory.beginConnection();
            IUserDAO userDAO = factory.getUserDAO();
            addressList = userDAO.getGroupEmails(role, firstRow, count);
        } catch (Exception exc) {
        	Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, exc);
        	return null;
		} finally {
            factory.endConnection();
        }
        
        Address[] addresses = new Address[addressList.size()];
        
        /* Get the array of users' addresses */
        for (int i = 0; i < addressList.size(); i++) {
            try {
                addresses[i] = new InternetAddress(addressList.get(i));
            } catch (AddressException ex) {
                Logger.getLogger(EmailSender.class.getName()).log(Level.WARNING,
                        null, ex);
            }
        }
        
        return addresses;
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
    
    /**
     * This method gets email address of given user 
     * @param user User to get email address for
     * @return email address
     */
    private Address getUserAddress(User user) {
        try {
            Address address = new InternetAddress(user.getEmail());
            return address;
        } catch (AddressException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
