package org.mobibank.payme;

import org.mobibank.payme.backend.UtilisateurRepository;
import org.mobibank.payme.backend.data.entity.AbstractEntity;
import org.mobibank.payme.backend.util.LocalDateJpaConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.vaadin.spring.events.annotation.EnableEventBus;

/**
 * 
 * @author BEYE
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { UtilisateurRepository.class })
@EntityScan(basePackageClasses = { AbstractEntity.class, LocalDateJpaConverter.class })
@EnableEventBus
public class PaymeApplication extends SpringBootServletInitializer {

	public static final String APP_URL = "/";
	public static final String LOGIN_URL = "/login";
	public static final String LOGOUT_URL = "/login?logout";
	public static final String LOGIN_FAILURE_URL = "/login?error=true";
	public static final String LOGIN_PROCESSING_URL = "/login";
	
	public static void main(String[] args) {
		SpringApplication.run(PaymeApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PaymeApplication.class);
	}
}
