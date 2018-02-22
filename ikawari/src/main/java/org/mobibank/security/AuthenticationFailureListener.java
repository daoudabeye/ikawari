package org.mobibank.security;

import javax.servlet.http.HttpServletRequest;

import org.mobibank.HasLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;

import com.vaadin.spring.annotation.SpringComponent;

/**
 * 
 * @author BEYE
 * Authentication failure handler
 *
 */
@SpringComponent
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent>, HasLogger {

	public static final String NAME_PARAMETER = "username";
	public static final String FAILURE_MESSAGE = "Echec de connexion username : ";
	public static final String LOGIN_MESSAGE = "Echec de connexion username : ";

	private final LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
		super();
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		//ancien
		//WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
		//loginAttemptService.loginFailed(auth.getRemoteAddress());

		//nouvelle implementation
		final String xfHeader = request.getHeader("X-Forwarded-For");
		String remote = xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];

		loginAttemptService.loginFailed(remote);

		getLogger().warn("Tentative de connexion de "+
				event.getAuthentication().getName()+" depuis l'adresse "+ remote);
	}
}
