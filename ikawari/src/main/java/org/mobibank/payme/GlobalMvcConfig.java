package org.mobibank.payme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 
 * @author BEYE
 *
 */
@Configuration
public class GlobalMvcConfig  extends WebMvcConfigurerAdapter {

	@Autowired
	LocaleChangeInterceptor localeChangeInterceptor;
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("login");
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
    }
}
