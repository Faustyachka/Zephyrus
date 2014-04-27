package com.zephyrus.wind.helpers;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
																									// REVIEW: documentation expected
public class MailSend {																				
	public static void send(String title, String[] recipients, String content) throws Exception {	// REVIEW: method should obtain Email class as param (in this class methods getTitle() and getBody() should be presented)

		InternetAddress from = new InternetAddress("system@zephyrus", "Zephyrus");
		InitialContext ctx = new InitialContext();
		Session session = (Session) ctx.lookup("mail/gmail");

		// Create email and headers.
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(from);
		msg.setSubject(title);
		for(String recipient : recipients)															// REVIEW: braces expected
			msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		msg.setContent(content, "text/plain");														// REVIEW: should be changed to text/html in the future

		Transport.send(msg);
		
	}
}
