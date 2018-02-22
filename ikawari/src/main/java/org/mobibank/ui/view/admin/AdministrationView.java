package org.mobibank.ui.view.admin;

import javax.annotation.PostConstruct;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.services.AppService;
import org.mobibank.ui.navigation.NavigationManager;
import org.mobibank.ui.view.admin.pays.EntiteView;
import org.mobibank.ui.view.admin.pays.PaysView;
import org.mobibank.ui.view.admin.pays.ZoneView;
import org.mobibank.ui.view.admin.services.MyPaymeView;
import org.mobibank.ui.view.admin.services.OrdresView;
import org.mobibank.ui.view.admin.services.SequenceView;
import org.mobibank.ui.view.admin.structure.StructureView;
import org.mobibank.ui.view.admin.tarrifs.CorridoreView;
import org.mobibank.ui.view.admin.tarrifs.DeviseView;
import org.mobibank.ui.view.admin.tarrifs.GrilleTransfertView;
import org.mobibank.ui.view.admin.tarrifs.GrilleWalletView;
import org.mobibank.ui.view.admin.user.AgentView;
import org.mobibank.ui.view.admin.user.ClientView;
import org.mobibank.ui.view.admin.user.CompteView;
import org.mobibank.ui.view.admin.user.ManagerView;
import org.mobibank.ui.view.admin.user.UtilisateurView;
import org.springframework.security.access.annotation.Secured;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AdministrationView.VIEW_NAME)
@MenuCaption("Admin")
@MenuIcon(VaadinIcons.ARROW_FORWARD)
@Secured(Role.ADMIN)
public class AdministrationView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Administration";
	
	private final NavigationManager navigationManager;
	AppService appService;

	MenuBar menuBar;
	Panel viewcontent = new Panel();
	
	MenuItem params;
	MenuItem gestionPays;
	MenuItem gestionServices;
	MenuItem gestionUtilisateur;
	MenuItem gestionPartenaires;
	MenuItem gestionGrille;
	
	MenuItem zoneItem;
	MenuItem paysItem;
	MenuItem entiteItem;
	
	MenuItem sequenceItem;
//	MenuItem serviceItem;
	MenuItem myPaymeItem;
	MenuItem ordresItem;
	
	MenuItem profileItem;
	MenuItem utilisateurItem;
	MenuItem accountItem;
	MenuItem comptesItem;
	
	MenuItem managerItem;
	MenuItem agentItem;
	MenuItem agenceItem;
	MenuItem clientItem;
	
	MenuItem apiItem;
	MenuItem corridoresItem;
	MenuItem grilleWalletItem;
	MenuItem grilleTransfertItem;
	
	MenuItem devise;

	@PostConstruct
	public void init(){
		setSizeFull();
		
		menuBar = new MenuBar();
		addComponent(menuBar);
		addComponent(viewcontent);
		this.setExpandRatio(viewcontent, 1);
		viewcontent.setSizeFull();
		
		gestionPays = menuBar.addItem("Gestion Pays", VaadinIcons.CONTROLLER, null);
		zoneItem = gestionPays.addItem("Zone", zone);
		zoneItem = gestionPays.addItem("Pays", pays);
		zoneItem = gestionPays.addItem("Entités", entite);
		
		gestionServices = menuBar.addItem("Services", VaadinIcons.SERVER, null);
//		serviceItem = gestionServices.addItem("Gestion services", appServices);
		myPaymeItem = gestionServices.addItem("My Payme", MyPaymeServices);
		ordresItem = gestionServices.addItem("Payme fs", OrdresServices);
		sequenceItem = gestionServices.addItem("Schema comptable", sequence);
		
		gestionUtilisateur = menuBar.addItem("Utilisateurs", VaadinIcons.USERS, null);
		
		utilisateurItem  = gestionUtilisateur.addItem("Gestion utilisateurs", null);
		agenceItem = utilisateurItem.addItem("Structure", VaadinIcons.OFFICE, structures);
		managerItem = utilisateurItem.addItem("Manager", VaadinIcons.USER_CHECK, manager);
		agentItem = utilisateurItem.addItem("Agents", VaadinIcons.USERS, agents);
		agentItem = utilisateurItem.addItem("Clients", VaadinIcons.USERS, clients);
		
		accountItem = gestionUtilisateur.addItem("Accès Internet", accounts);
		comptesItem = gestionUtilisateur.addItem("Wallet", wallet);
		
		gestionGrille = menuBar.addItem("Tarrifs", VaadinIcons.MONEY, null);
		corridoresItem = gestionGrille.addItem("Corridores", corridores);
		grilleWalletItem = gestionGrille.addItem("Grillle Wallets", grilleWallets);
		grilleTransfertItem = gestionGrille.addItem("Grillle Tranferts", transfert);
		
		devise = menuBar.addItem("Dévise", VaadinIcons.MONEY_EXCHANGE, change);
		
	}
	
	public AdministrationView(NavigationManager navigationManager, AppService appService) {
		this.navigationManager = navigationManager;
		this.appService = appService;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		viewcontent.setContent(new ParametreDesign(appService));
	}
	
	MenuBar.Command zone = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(ZoneView.class);
		}
	};
	MenuBar.Command pays = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(PaysView.class);
		}
	};
	MenuBar.Command entite = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(EntiteView.class);
		}
	};
	
//	MenuBar.Command appServices = new Command() {
//		private static final long serialVersionUID = 1L;
//		@Override
//		public void menuSelected(MenuItem selectedItem) {
//			navigationManager.navigateTo(ServiceView.class);
//		}
//	};
	
	MenuBar.Command MyPaymeServices = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(MyPaymeView.class);
		}
	};
	
	MenuBar.Command OrdresServices = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(OrdresView.class);
		}
	};
	
	MenuBar.Command sequence = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(SequenceView.class);
		}
	};
	
	
	MenuBar.Command manager = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(ManagerView.class);
		}
	};
	
	MenuBar.Command agents = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(AgentView.class);
		}
	};
	
	MenuBar.Command clients = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(ClientView.class);
		}
	};
	
	MenuBar.Command structures = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(StructureView.class);
		}
	};
	
	MenuBar.Command corridores = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(CorridoreView.class);
		}
	};
	
	MenuBar.Command grilleWallets = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(GrilleWalletView.class);
		}
	};
	
	MenuBar.Command transfert = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(GrilleTransfertView.class);
		}
	};
	
	MenuBar.Command accounts = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(UtilisateurView.class);
		}
	};
	
	MenuBar.Command wallet = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(CompteView.class);
		}
	};
	
	MenuBar.Command change = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationManager.navigateTo(DeviseView.class);
		}
	};

}
