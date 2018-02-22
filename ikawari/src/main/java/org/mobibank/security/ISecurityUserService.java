package org.mobibank.security;

public interface ISecurityUserService {

	String validatePasswordResetToken(long id, String token);
}
