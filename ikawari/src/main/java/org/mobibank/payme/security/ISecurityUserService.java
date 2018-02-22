package org.mobibank.payme.security;

public interface ISecurityUserService {

	String validatePasswordResetToken(long id, String token);
}
