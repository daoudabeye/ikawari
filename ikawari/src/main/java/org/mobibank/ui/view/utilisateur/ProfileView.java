package org.mobibank.ui.view.utilisateur;

import javax.annotation.PostConstruct;

import org.mobibank.backend.services.EntiteService;
import org.mobibank.backend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;

/**
 * 
 * @author BEYE
 *
 */
@SpringView
public class ProfileView extends Panel implements View {
	public static final String VIEW_NAME = "Gestion";
	
	@Autowired EntiteService entiteService;
	@Autowired UtilisateurService utilisateurService;
	
	Panel viewcontent = new Panel();
	
	@PostConstruct
	public void init(){
		setSizeFull();

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		setContent(new ProfileDesign(utilisateurService));
	}
}
