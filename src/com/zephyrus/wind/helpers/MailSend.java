package com.zephyrus.wind.helpers;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;

public class MailSend {
	public static void send(String title, String[] recipients, String content) throws Exception {

		InternetAddress from = new InternetAddress("system@zephyrus", "Zephyrus");
		InitialContext ctx = new InitialContext();
		Session session = (Session) ctx.lookup("mail/gmail");

		// Create email and headers.
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(from);
		msg.setSubject(title);
		for(String recipient : recipients)
			msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		msg.setContent(content, "text/plain");

		Transport.send(msg);
		
	}
}
