package org.mobibank.payme.security;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobibank.payme.HasLogger;
import org.mobibank.payme.PaymeApplication;
import org.mobibank.payme.backend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.annotation.ApplicationScope;

import com.vaadin.spring.annotation.SpringComponent;

/**
 * Redirects to the application after successful authentication.
 */
@SpringComponent
@ApplicationScope
public class RedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler, HasLogger {

	private final String location;

	@Autowired
	private LoginAttemptService loginAttemptService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private UtilisateurService utilisateurService;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public RedirectAuthenticationSuccessHandler() {
		location = PaymeApplication.APP_URL;
	}

	private String getAbsoluteUrl(String url) {
		final String relativeUrl;
		if (url.startsWith("/")) {
			relativeUrl = url.substring(1);
		} else {
			relativeUrl = url;
		}
		return servletContext.getContextPath() + "/" + relativeUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//Old implementation
//		WebAuthenticationDetails auth = (WebAuthenticationDetails) authentication.getDetails();
//		loginAttemptService.loginSucceeded(auth.getRemoteAddress());

		final String xfHeader = request.getHeader("X-Forwarded-For");
		String remote = xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];
		
        loginAttemptService.loginSucceeded(remote);
		utilisateurService.loginSucceeded(authentication.getName(), remote);
		
		addWelcomeCookie(gerUserName(authentication), response);
		//redirectStrategy.sendRedirect(request, response, getAbsoluteUrl(location));
		redirectStrategy.sendRedirect(request, response, PaymeApplication.APP_URL);//wildfly container
		getLogger().info(authentication.getName()+" connecté depuis l'adresse "+ remote);
		
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	private String gerUserName(final Authentication authentication) {
        return ((User)authentication.getPrincipal()).getUsername();
    }
	
	private void addWelcomeCookie(final String user, final HttpServletResponse response) {
        Cookie welcomeCookie = getWelcomeCookie(user);
        response.addCookie(welcomeCookie);
    }

    private Cookie getWelcomeCookie(final String user) {
        Cookie welcomeCookie = new Cookie("welcome", user);
        welcomeCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        return welcomeCookie;
    }
}
