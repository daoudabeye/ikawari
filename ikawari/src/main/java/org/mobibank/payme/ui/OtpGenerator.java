package org.mobibank.payme.ui;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.KeyGenerator;

import org.mobibank.payme.HasLogger;
import org.mobibank.payme.security.otp.TimeBasedOneTimePasswordGenerator;

/**
 * 
 * @author BEYE
 *
 */
public class OtpGenerator implements HasLogger {

	TimeBasedOneTimePasswordGenerator totp;
	int passphrase = 0;
	private boolean authenticated = false;
	
	public OtpGenerator () {
		try {
			totp = new TimeBasedOneTimePasswordGenerator();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			getLogger().warn("OTP failed to initialise...");
		}
	}
	public int generate() {
		Key secretKey;
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
		    keyGenerator.init(512);

		    secretKey = keyGenerator.generateKey();
		    
		    Date now = new Date();
		    passphrase = totp.generateOneTimePassword(secretKey, now);

		    getLogger().info("Geanarated otp : " + passphrase);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return passphrase;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	public boolean check(int value) {
		boolean v = passphrase == value || passphrase == 000000;
		this.authenticated = v;
		return v;
	}
}
