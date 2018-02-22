package org.mobibank.ui.view.admin.structure;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class StructureViewDesign extends AbstractCrudViewDesign<Structure> {

	/**
	 * 
	 */
	protected Grid<Structure> list;
	TextField designation;
	TextField identifiant;
	TextField telephone;
	ComboBox<Agent> master;
	ComboBox<String> role;
	ComboBox<Pays> pays;
	ComboBox<Entite> entites;
	PasswordField password;
	
	public StructureViewDesign() {
		
		list = new Grid<Structure>(Structure.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		addGrid(list);
		
		designation = new TextField("Désignation");
		master = new ComboBox<Agent>("Master");
		master.setDescription("Pour une Agence indiquez l'agent master(Facultatif)");
		identifiant = new TextField("Identifiant");
		telephone = new TextField("Téléphone");
		role = new ComboBox<>("Profile");
		role.setItems(Role.getAllStructures());
		pays = new ComboBox<>("Pays");
		entites = new ComboBox<Entite>("Entite");
		password = new PasswordField("Password");
		
		addField(role);
		addField(identifiant);
		addField(password);
		addField(telephone);
		addField(designation);
		addField(pays);
		addField(entites);
		addField(master);
	}
	
	public void bindForm(BeanValidationBinder<Structure> binder){
		binder.forField(designation).bind(Structure::getDesignation, Structure::setDesignation);
		binder.forField(master).bind(Structure::getMaster, Structure::setMaster);
		binder.forField(identifiant).bind(Structure::getUsername, Structure::setUsername);
		binder.forField(telephone).bind(Structure::getTelephone, Structure::setTelephone);
		binder.forField(role).bind(Structure::getRole, Structure::setRole);
		binder.forField(entites).bind(Structure::getEntite, Structure::setEntite);
	}
}
