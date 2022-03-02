package nl.utwente.trimm.group42.models;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	static final String SENDER = "trimm.group42@gmail.com";
	static final String PASS = "trimm12345";
	String subject;
	String text;
	String recepient;
	static final String HOST = "smtp.gmail.com";

	public void send() {
		// Getting system properties
		Properties properties = new Properties();

		// Setting up mail server
		properties.setProperty("mail.smtp.host", HOST);
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");// OPENING TLS channel

		// creating session object to get properties
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER, PASS);
			}
		});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
			message.setSubject(getSubject());
			message.setText(getText());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String getSubject() {
		return this.subject;
	}

	public String getText() {
		return this.text;
	}

	public String getRecepient() {
		return this.recepient;
	}

	public void setRecepient(String recepient) {
		this.recepient = recepient;
	}

	public void setSubject(String subject) {
		this.subject = subject;

	}

	public void setText(String text) {
		this.text = text;
	}

	public String generateSafeToken() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		String token = encoder.encodeToString(bytes);
		return token;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SendEmail email = new SendEmail();
		// email.send();
		System.out.println(email.generateSafeToken());

	}

}
