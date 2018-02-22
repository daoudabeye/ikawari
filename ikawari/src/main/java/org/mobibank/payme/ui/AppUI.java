package org.mobibank.payme.ui;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.HasLogger;
import org.mobibank.payme.security.UserDetailsServiceImpl;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.mobibank.payme.ui.view.AccessDeniedView;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 * 
 * @author BEYE
 *
 */
@SuppressWarnings("serial")
@Theme("apptheme")
@SpringUI
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("PAYME")
public class AppUI extends UI implements HasLogger {

	private final SpringViewProvider viewProvider;
	private final NavigationManager navigationManager;
	private final MainLayout mainLayout;
	
	OtpAthenticationView otpView;
	int otpValue = 0;

	/**
	 * 
	 * @param viewProvider
	 * @param navigationManager
	 * @param mainLayout
	 */
	@Autowired
	public AppUI(SpringViewProvider viewProvider, NavigationManager navigationManager, MainLayout mainLayout) {
		setErrorHandler(event -> {
			Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
			getLogger().error("Error during request", t);
		});
		this.viewProvider = viewProvider;
		this.navigationManager = navigationManager;
		this.mainLayout = mainLayout;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		
		UserDetailsServiceImpl userDetails = BeanLocator.find(UserDetailsServiceImpl.class);
		
		//2 Factor authentication setup
		if(userDetails.getCurrent().isUsing2FA()) {
			if(userDetails.getOtp().isAuthenticated()) {
				setContent(mainLayout.build());
			} else {
				otpValue = userDetails.getOtp().generate();
				otpView = new OtpAthenticationView();
				otpView.addOtpLitener(e -> processing(userDetails.getOtp() ,otpView));
				setContent(otpView);
			}
		}else
			setContent(mainLayout.build());
		
		//Navigating to default state
		navigationManager.navigateToDefaultView();
		
	}
	
	/**
	 * Call back method for page reload...
	 * @param otpGenerator
	 * @param otpView
	 */
	private void processing(OtpGenerator otpGenerator, OtpAthenticationView otpView) {
		if(otpGenerator.check(Integer.valueOf(otpView.getInput()))) {
			getUI().getPage().reload();
		}
		else
			Notification.show("Code icorrecte code :" + otpGenerator.passphrase);
	
	}
	
//	/**
//	 * Logs the user out after ensuring the currently open view has no unsaved
//	 * changes.
//	 */
//	public void logout() {
//		ViewLeaveAction doLogout = () -> {
//			UI ui = getUI();
//			ui.getSession().getSession().invalidate();
//			ui.getPage().reload();
//		};
//
//		navigationManager.runAfterLeaveConfirmation(doLogout);
//	}
}
