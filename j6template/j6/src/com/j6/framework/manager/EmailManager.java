package com.j6.framework.manager;

/**
 * 
 * This interface define methods that are used to send email.
 * 
 */
public interface EmailManager {

	/**
	 * Send email. Thread will be use.
	 * 
	 * @param sendToEmail
	 */
	public void sendEmails(String... emailsToBeSent);

	public void sendEmail(String msg, String email);
}
