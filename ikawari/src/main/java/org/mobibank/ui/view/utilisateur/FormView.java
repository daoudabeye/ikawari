package org.mobibank.ui.view.utilisateur;

import javax.annotation.PostConstruct;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.services.AgentService;
import org.mobibank.backend.services.ClientService;
import org.mobibank.backend.services.EntiteService;
import org.mobibank.backend.services.StructureService;
import org.mobibank.backend.services.UtilisateurService;
import org.mobibank.security.SecurityUtils;
import org.mobibank.ui.view.utilisateur.form.AgentForm;
import org.mobibank.ui.view.utilisateur.form.ClientForm;
import org.mobibank.ui.view.utilisateur.form.StructureForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SpringView
@Secured({Role.AMBASSADEUR, Role.DISTRIBUTEUR, Role.PROVIDER, Role.MASTER})
public class FormView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Utilisateur";

	@Autowired EntiteService entiteService;
	@Autowired UtilisateurService utilisateurService;
	@Autowired StructureService structureService;
	@Autowired AgentService agentService;
	@Autowired ClientService clientService;

	MenuBar menuBar;
	Panel viewcontent = new Panel();

	MenuItem agent;
	MenuItem agence;
	MenuItem client;

	@PostConstruct
	public void init(){
		setSizeFull();

		menuBar = new MenuBar();
		addComponent(menuBar);
		addComponent(viewcontent);
		setExpandRatio(viewcontent, 1);
		viewcontent.setSizeFull();

		if(SecurityUtils.isCurrentUserInRole(Role.PROVIDER) ||
				SecurityUtils.isCurrentUserInRole(Role.MASTER))
			agence = menuBar.addItem("Structure", VaadinIcons.FILE_CODE, agenceCmd);

		if(SecurityUtils.isCurrentUserInRole(Role.PROVIDER) ||
				SecurityUtils.isCurrentUserInRole(Role.MASTER))
			agent = menuBar.addItem("Nouveau Agent", VaadinIcons.SPECIALIST, agentCmd);

		if(SecurityUtils.isCurrentUserInRole(Role.AMBASSADEUR)||
				SecurityUtils.isCurrentUserInRole(Role.DISTRIBUTEUR))
		client = menuBar.addItem("Nouveau Client", VaadinIcons.FILE_CODE, clientCmd);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	MenuBar.Command agentCmd = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			viewcontent.setContent(new AgentForm(utilisateurService, agentService, structureService));
			viewcontent.setCaption("Nouveau Compte Agent");
		}
	};

	MenuBar.Command agenceCmd = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			viewcontent.setContent(new StructureForm(structureService));
			viewcontent.setCaption("Créer un point service");
		}
	};

	MenuBar.Command clientCmd = new Command() {
		private static final long serialVersionUID = 1L;
		@Override
		public void menuSelected(MenuItem selectedItem) {
			viewcontent.setContent(new ClientForm(clientService));
			viewcontent.setCaption("Nouveau Client");
		}
	};
}
