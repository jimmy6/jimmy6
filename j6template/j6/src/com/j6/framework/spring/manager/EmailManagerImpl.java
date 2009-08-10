package com.j6.framework.spring.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.EmailValidator;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.j6.framework.manager.EmailManager;

public class EmailManagerImpl implements EmailManager {

	private SimpleMailMessage simpleMailMessage;

	private MailSender mailSender;

	public EmailManagerImpl() {
	}

	/**
	 * filter email by using EmailValidator.getInstance().isValid().
	 * 
	 * @param emailAddresses
	 * @return
	 */
	private String[] filterEmailAdresses(String... emailAddresses) {
		List<String> emails = new ArrayList<String>();

		// filter empty empty
		for (String email : emailAddresses) {
			if (EmailValidator.getInstance().isValid(email)) {
				emails.add(email);
				// System.out.println("Valid email ====== "+email);
			}
		}

		return emails.toArray(new String[0]);
	}

	private String filterEmailAdresses(String emailAddresses) {
		String validEmail = "";

		if (EmailValidator.getInstance().isValid(emailAddresses)) {
			validEmail = emailAddresses;
		}

		return validEmail;
	}

	/**
	 * Send email.<br>
	 * Thread will be use.<br>
	 * Email will be send by using Bcc.<br>
	 * 
	 * @param emailAddresses
	 */
	public void sendEmails(String... emailAddresses) {

		simpleMailMessage = new SimpleMailMessage(simpleMailMessage);
		simpleMailMessage.setBcc(filterEmailAdresses(emailAddresses));

		EmailManagerThread emailManagerThread = new EmailManagerThread();
		emailManagerThread.start();
	}

	/**
	 * Send email.<br>
	 * Thread will be use.<br>
	 * Email will be send by using Bcc.<br>
	 * Custom message will be use.<br>
	 * 
	 * @param emailAddress
	 */
	public void sendEmail(String msg, String emailAddress) {

		simpleMailMessage = new SimpleMailMessage(simpleMailMessage);
		simpleMailMessage.setBcc(filterEmailAdresses(emailAddress));
		simpleMailMessage.setText(msg);

		EmailManagerThread emailManagerThread = new EmailManagerThread();
		emailManagerThread.start();
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	private class EmailManagerThread extends Thread {

		public void run() {
			try {
				mailSender.send(simpleMailMessage);
			} catch (MailException e) {
				e.printStackTrace();
			}
		}
	}
}
