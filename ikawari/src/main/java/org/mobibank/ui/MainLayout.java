package org.mobibank.ui;

import javax.annotation.PostConstruct;

import org.mobibank.ui.navigation.NavigationManager;
import org.mobibank.ui.view.admin.AdministrationView;
import org.mobibank.ui.view.dashboard.DashboardView;
import org.mobibank.ui.view.envoi.EnvoiView;
import org.mobibank.ui.view.home.HomeView;
import org.mobibank.ui.view.operations.OperationView;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayout;
import com.github.appreciated.app.layout.builder.AppLayoutConfiguration.Position;
import com.github.appreciated.app.layout.builder.CDIAppLayoutBuilder;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotification;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.builder.providers.DefaultSpringNavigationElementInfoProvider;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

/**
 * 
 * @author BEYE
 *
 */
@SuppressWarnings("serial")
@SpringViewDisplay
@UIScope
public class MainLayout extends AppLayout implements ViewDisplay {

	private final SecuredViewAccessControl viewAccessControl;
	protected final NavigationManager navigationManager;
	private final SpringViewProvider viewProvider;

	//the view container
	private Panel viewContainer;

	@Autowired
	public MainLayout(NavigationManager navigationManager, SecuredViewAccessControl viewAccessControl,
			SpringViewProvider viewProvider) {
		this.navigationManager = navigationManager;
		this.viewAccessControl = viewAccessControl;
		this.viewProvider = viewProvider;
	}

	DefaultNotification message = new DefaultNotification("Bienvenue", "Notification ...");
	DefaultNotificationHolder notifications = new DefaultNotificationHolder();
	DefaultBadgeHolder badge = new DefaultBadgeHolder(0);

	@PostConstruct
	public void init() {

	}

	public AppLayoutComponent build() {
		CDIAppLayoutBuilder cdi = getCDIBuilder(Behaviour.LEFT_RESPONSIVE_HYBRID)
				.withViewProvider(() -> viewProvider)
				.withNavigationElementInfoProvider(new DefaultSpringNavigationElementInfoProvider())
				.withNavigator(container -> {
					this.viewContainer = container;
					return navigationManager;
				})
				.withTitle("PAYME MOBILE BANKING")
				.addToAppBar(new AppBarNotificationButton(notifications, true))
				.withDesign(AppBarDesign.MATERIAL)
				.add(new MenuHeader("Version 0.1.0", new ThemeResource("logo.png")), Position.HEADER)
				.add(badge, HomeView.class)
				.addClickable("Deconnexion", VaadinIcons.EXIT, clickEvent -> {
					logout();
				}, Position.FOOTER )
				.add("configuration", VaadinIcons.COG, ConfigPanel.class, Position.FOOTER);

		attachNavigation(cdi, DashboardView.class);
		attachNavigation(cdi, EnvoiView.class);
		attachNavigation(cdi, OperationView.class);
		attachNavigation(cdi, AdministrationView.class);

		return cdi.build();
	}

	/**
	 * Logs the user out after ensuring the currently open view has no unsaved
	 * changes.
	 */
	public void logout() {
		ViewLeaveAction doLogout = () -> {
			UI ui = UI.getCurrent();
			ui.getSession().getSession().invalidate();
			ui.getPage().reload();
		};

		navigationManager.runAfterLeaveConfirmation(doLogout);
	}

	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		if(viewContainer != null)
			viewContainer.setContent(view.getViewComponent());
	}

	/**
	 * Makes clicking the given button navigate to the given view if the user
	 * has access to the view.
	 * <p>
	 * If the user does not have access to the view, hides the button.
	 *
	 * @param cdi
	 *            the layout builder
	 * @param targetView
	 *            the view to navigate to when the user clicks the button
	 */
	private CDIAppLayoutBuilder attachNavigation(CDIAppLayoutBuilder cdi, Class<? extends View> targetView) {
		boolean hasAccessToView = viewAccessControl.isAccessGranted(targetView);

		if (hasAccessToView) {
			cdi.add(targetView);
		}

		return cdi;
	}

}
