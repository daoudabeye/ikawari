package org.mobibank.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.ui.Button;

/**
 * La page principale contient le menu et la zone dedié à l'affichage des vues.
 * 
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
//@SpringViewDisplay
//@UIScope
@Deprecated
public class MainView extends MainDesign implements ViewDisplay {
	private final Map<Class<? extends View>, Button> navigationButtons = new HashMap<>();
	private final SecuredViewAccessControl viewAccessControl;

	protected final NavigationManager navigationManager;

	@Autowired
	public MainView(NavigationManager navigationManager, SecuredViewAccessControl viewAccessControl) {
		this.navigationManager = navigationManager;
		this.viewAccessControl = viewAccessControl;
	}

	@PostConstruct
	public void init() {
//		attachNavigation(home, HomeView.class);
//		attachNavigation(dashboard, DashboardView.class);
//		attachNavigation(transferts, EnvoiView.class);
//		attachNavigation(utilisateurs, FormView.class);
//		attachNavigation(admin, AdministrationView.class);
//		attachNavigation(operation, OperationView.class);
//		attachNavigation(historique, HistoriqueView.class);
//		attachNavigation(profile, ProfileView.class);
//		attachNavigation(mtcn, TransfertsView.class);
//		attachNavigation(repport, RapportsView.class);
//		
//		deconnexion.addClickListener(e -> logout());
	}

	/**
	 * Makes clicking the given button navigate to the given view if the user
	 * has access to the view.
	 * <p>
	 * If the user does not have access to the view, hides the button.
	 *
	 * @param navigationButton
	 *            the button to use for navigatio
	 * @param targetView
	 *            the view to navigate to when the user clicks the button
	 */
	private void attachNavigation(Button navigationButton, Class<? extends View> targetView) {
		boolean hasAccessToView = viewAccessControl.isAccessGranted(targetView);
		navigationButton.setVisible(hasAccessToView);

		if (hasAccessToView) {
			navigationButtons.put(targetView, navigationButton);
			navigationButton.addClickListener(e -> navigationManager.navigateTo(targetView));
		}
	}

	@Override
	public void showView(View view) {

//		content.removeAllComponents();
//		content.addComponent(view.getViewComponent());
//
//		navigationButtons.forEach((viewClass, button) -> button.setStyleName("selected", viewClass == view.getClass()));
//
//		Button menuItem = navigationButtons.get(view.getClass());
//		String viewName = "";
//		if (menuItem != null) {
//			viewName = menuItem.getCaption();
//		}
//		activeViewName.setValue(viewName);
	}

	/**
	 * Logs the user out after ensuring the currently open view has no unsaved
	 * changes.
	 */
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
