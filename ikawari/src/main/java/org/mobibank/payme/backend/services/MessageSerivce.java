package org.mobibank.payme.backend.services;

import org.mobibank.payme.HasLogger;
import org.springframework.stereotype.Service;

@Service
public class MessageSerivce implements HasLogger {

	public void sendSMS(String number, String text) {
		getLogger().info("Envoi sms à " + number + ", contenu : "+ text);
	}
	
	public void sendEmail(String email, String object, String content, String html) {
		getLogger().info("Mail à " + email + ", objet : "+ object + "contenu : " +content);
	}
	
	public void sendNotification(String message) {
		getLogger().info("Notification " + message);
	}
}
