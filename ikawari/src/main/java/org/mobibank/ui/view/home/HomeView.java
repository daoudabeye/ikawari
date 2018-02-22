package org.mobibank.ui.view.home;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.services.CompteService;
import org.mobibank.backend.services.UtilisateurService;
import org.mobibank.backend.services.ValidationService;
import org.mobibank.security.SecurityUtils;
import org.mobibank.security.UserDetailsServiceImpl;
import org.mobibank.ui.navigation.NavigationManager;
import org.mobibank.ui.view.admin.reports.RapportsView;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = HomeView.VIEW_NAME)
@MenuCaption("Home")
@MenuIcon(VaadinIcons.HOME)
public class HomeView extends Panel implements View {
	public static final String VIEW_NAME = "Home";

	@Autowired NavigationManager navigator;
	@Autowired CompteService compteService;
	@Autowired ValidationService validationService;
	@Autowired UserDetailsServiceImpl userDetails;
	@Autowired UtilisateurService utilisateurService;

	Panel infos;
	Panel quickView;

	@PostConstruct
	public void init(){
		setSizeFull();
		quickView = new Panel();
		quickView.setSizeFull();
		quickView.setVisible(false);
	}

	public HomeView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(ViewChangeEvent event) {

		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();

		VerticalLayout homeTop = new VerticalLayout();
		content.addComponent(homeTop);

		infos = new Panel();
		infos.setWidthUndefined();
		HorizontalLayout infosLayout = new HorizontalLayout(setUserInfos());
		infosLayout.setSizeUndefined();
		infosLayout.setSpacing(true);
		infosLayout.setMargin(true);
		infos.setContent(infosLayout);
		homeTop.addComponent(infos);

		HorizontalLayout line = new HorizontalLayout();
		line.setStyleName("row");
		line.setSizeUndefined();
		homeTop.addComponent(line);

		Collection<Compte> comptes = compteService.find(utilisateurService.getCurrentUser());
		if(!comptes.isEmpty()){
			CompteGrid compteGrid = new CompteGrid(comptes);
			Resource comptesRes = new ThemeResource("img/comptes.jpg");
			Image comptesImage = new Image("My Wallets", comptesRes);
			line.addComponent(comptesImage);

			quickView.setVisible(true);
			quickView.setContent(compteGrid);
		}

		//		Resource op = new ThemeResource("img/operation.jpg");
		//		Image opImage = new Image("Operations", op);
		//		line.addComponent(opImage);

		if(SecurityUtils.isCurrentUserInRole(Role.getAllStructures())) {
			Resource rapports = new ThemeResource("img/Rapports.jpg");
			Image rapportsImage = new Image("Rapports", rapports);
			rapportsImage.setWidth("80px");
			rapportsImage.setHeight("80px");
			line.addComponent(rapportsImage);
			rapportsImage.addClickListener(e -> navigator.navigateTo(RapportsView.class));
		}

		BrowserWindowOpener opener =
				new BrowserWindowOpener(TauxDeviseUI.class);
		opener.setFeatures("height=500,width=400,resizable");

		Resource change = new ThemeResource("img/Change.jpg");
		Image changeImage = new Image("Taux de change", change);
		opener.extend(changeImage);
		line.addComponent(changeImage);

		content.addComponent(quickView);
		content.setExpandRatio(quickView, 1);

		setContent(content);

		//startTour();

	}

	public Label setUserInfos() {
		// TODO Auto-generated method stub
		Label label = new Label();
		label.setContentMode(ContentMode.HTML);
		label.setValue("Bienvenue,  <b>"+ SecurityUtils.getUsername() +" </b>"
				+ "<br />Profile : " + userDetails.getCurrent().getRole()
				+"<br />Ip Actuelle : " + userDetails.getActualIp()
				+ " Dernière Ip: "+ userDetails.getCurrent().getLastIp()
				+ "<br />Dernière connexion : " + userDetails.getCurrent().getLastLogin()
				+"<br />Version : 1.0");
		return label;
	}
}
