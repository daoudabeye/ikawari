package org.mobibank.payme.ui.view.admin.user;

import org.mobibank.payme.backend.data.entity.Entite;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.numerate.ValidationStatut;
import org.mobibank.payme.ui.AbstractCrudViewDesign;
import org.mobibank.payme.ui.RoleSelect;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class UtilisateurViewDesign extends AbstractCrudViewDesign<Utilisateur> {

	/**
	 * 
	 */
	protected Grid<Utilisateur> list;
	TextField username;
	TextField telephone;
	TextField email;
	RoleSelect role;
	TextField lastIp;
	DateTimeField lastLogin;
	TextField passwordLife;
	TextField tentative;
	CheckBox locked;
	CheckBox blacklisted;
	ComboBox<Utilisateur> createur;
	ComboBox<Entite> entite;
	ComboBox<ValidationStatut> validationStatut;
	PasswordField password;
	
	public UtilisateurViewDesign() {
		
		list = new Grid<Utilisateur>(Utilisateur.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("username", "telephone", "email", "role", "lastIp", "lastLogin", "passwordLife",
				"tentative", "locked", "blacklisted", "createur", "entite", "validationStatut");
		addGrid(list);
		
		username = new TextField("Username");
		telephone = new TextField("Téléphone");
		email = new TextField("email");
		role = new RoleSelect();
		lastIp = new TextField("Last IP");
		lastLogin = new DateTimeField("Last Login");
		passwordLife = new TextField("Password Life");
		tentative = new TextField("Tentative");
		locked = new CheckBox("Locked");
		blacklisted = new CheckBox("Blacklisted");
		createur = new ComboBox<Utilisateur>("Créateur");
		entite = new ComboBox<Entite>("Entité");
		validationStatut = new ComboBox<ValidationStatut>("Statut Validation");
		validationStatut.setItems(ValidationStatut.asList());
		password = new PasswordField("Password");
		
		addField(username);
		addField(telephone);
		addField(email);
		addField(role);
		addField(lastIp);
		addField(lastLogin);
		addField(passwordLife);
		addField(password);
		addField(tentative);
		addField(locked);
		addField(blacklisted);
		addField(createur);
		addField(entite);
		addField(validationStatut);
	}
	
	public void bindForm(BeanValidationBinder<Utilisateur> binder){
		binder.forField(username).bind(Utilisateur::getUsername, Utilisateur::setUsername);
		binder.forField(telephone).bind(Utilisateur::getTelephone, Utilisateur::setTelephone);
		binder.forField(email).bind(Utilisateur::getEmail, Utilisateur::setEmail);
		binder.forField(role).bind(Utilisateur::getRole, Utilisateur::setRole);
		binder.forField(lastIp).bind(Utilisateur::getLastIp, Utilisateur::setLastIp);
		binder.forField(lastLogin).bind(Utilisateur::getLastLogin, Utilisateur::setLastLogin);
		
		binder.forField(passwordLife)
		.withConverter(new StringToIntegerConverter("Valeur incorrect"))
		.bind(Utilisateur::getPasswordLife, Utilisateur::setPasswordLife);
		
		binder.forField(tentative)
		.withConverter(new StringToIntegerConverter("Valeur incorrect"))
		.bind(Utilisateur::getTentative, Utilisateur::setTentative);
		
		binder.forField(locked).bind(Utilisateur::isLocked, Utilisateur::setLocked);
		binder.forField(blacklisted).bind(Utilisateur::isBlacklisted, Utilisateur::setBlacklisted);
		binder.forField(createur).bind(Utilisateur::getCreateur, Utilisateur::setCreateur);
		binder.forField(entite).bind(Utilisateur::getEntite, Utilisateur::setEntite);
		binder.forField(validationStatut).bind(Utilisateur::getValidationStatut, Utilisateur::setValidationStatut);
	}
}
