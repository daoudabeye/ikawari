package org.mobibank.ui;

import org.mobibank.BeanLocator;
import org.mobibank.HasLogger;
import org.mobibank.security.UserDetailsServiceImpl;
import org.mobibank.ui.navigation.NavigationManager;
import org.mobibank.ui.view.AccessDeniedView;
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
 * Point d'entré de l'applacation
 */
@SuppressWarnings("serial")
@Theme("apptheme")
@SpringUI
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("PAYME")
public class AppUI extends UI implements HasLogger {

	private final SpringViewProvider viewProvider;
	private final NavigationManager navigationManager;
	//Layout principal
	private final MainLayout mainLayout;
	
	//Page d'authentification par code
	OtpAthenticationView otpView;
	int otpValue = 0;//valeur par defaut

	/**
	 * Constructeur de la class
	 * 
	 * @param viewProvider @SpringViewProvider
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

	/**
	 * Methode d'entrée
	 */
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		UserDetailsServiceImpl userDetails = BeanLocator.find(UserDetailsServiceImpl.class);
		
		//2 Factor authentication setup
		if(userDetails.getCurrent().isUsing2FA()) {
			if(userDetails.getOtp().isAuthenticated()) {//Utilisateur authentifié ?
				setContent(mainLayout.build());
			} else {
				otpValue = userDetails.getOtp().generate();//Géneration du code temporaire
				otpView = new OtpAthenticationView();//Création de la vue
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
