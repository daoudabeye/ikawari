package org.mobibank.payme.security;

import org.mobibank.payme.PaymeApplication;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.security.google2fa.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final RedirectAuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
			RedirectAuthenticationSuccessHandler successHandler, AuthenticationFailureHandler authenticationFailureHandler) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.successHandler = successHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login
		// page
		http.csrf().disable();
		http.headers().frameOptions().disable();

		//To avoid the scenario which allows multiple concurrent sessions for the same user
		http.sessionManagement().maximumSessions(1);
		
		/**
		 * protection against typical Session Fixation attacks 
		 * by configuring what happens to an existing session when the user tries to authenticate again
		 * on authentication a new HTTP Session is created,
		 * the old one is invalidated and the attributes from the old session are copied over
		 * 		when “none” is set, the original session will not be invalidated
		 * 		when “newSession” is set, a clean session will be created without any of the attributes from the old session being copied over
		 */
		http.sessionManagement().sessionFixation().newSession();
		
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg = http
				.authorizeRequests();

		// Allow access to static resources ("/VAADIN/**")
		reg = reg.antMatchers("/VAADIN/**").permitAll();
		// Allow access to static resources ("/webres/**")
		reg = reg.antMatchers("/webres/**").permitAll();
		// Require authentication for all URLS ("/**")
		reg = reg.antMatchers("/**").hasAnyAuthority(Role.getAllRoles());
		HttpSecurity sec = reg.and();
		
		//Authorise to change or update password
		reg = reg.antMatchers("/user/updatePassword*","/user/savePassword*","/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE");

		// Allow access to login page without login
		FormLoginConfigurer<HttpSecurity> login = sec.formLogin().permitAll();
		login = login.loginPage(PaymeApplication.LOGIN_URL).loginProcessingUrl(PaymeApplication.LOGIN_PROCESSING_URL)
				//.failureUrl(Application.LOGIN_FAILURE_URL)
				.successHandler(successHandler)
				.failureHandler(authenticationFailureHandler)
				.authenticationDetailsSource(authenticationDetailsSource);
		login.and()
		.logout().logoutSuccessUrl(PaymeApplication.LOGOUT_URL)
		.deleteCookies("JSESSIONID")
		.and()
		.rememberMe()
			.rememberMeServices(rememberMeServices())
			.key("remember-me-key");
	}

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key", userDetailsService);
	}

}
