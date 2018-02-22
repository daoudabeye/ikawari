package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Manager;
import org.mobibank.backend.numerate.Droits;
import org.mobibank.backend.numerate.Genre;
import org.mobibank.backend.numerate.PieceIdentite;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class ManagerViewDesign extends AbstractCrudViewDesign<Manager> {

	/**
	 * 
	 */
	protected Grid<Manager> list;
	protected TwinColSelect<Droits> droits;
	protected TextField nom;
	protected TextField prenom;
	protected TextField  adresse;
	protected TextField  ville;
	protected TextField  numeroPieceId;
	protected ComboBox<Genre> genre;
	protected ComboBox<PieceIdentite> pieceIdentite;
	protected DateField dateNaissance;
	ComboBox<String> role;
	TextField identifiant;

	public ManagerViewDesign() {
		
		list = new Grid<Manager>(Manager.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("nom", "prenom","adresse", "ville", "pieceIdentite","numeroPieceId", "genre","dateNaissance");
		addGrid(list);
		
		
		droits = new TwinColSelect<>("Droits");
		droits.setItems(Droits.asList());
		
		identifiant = new TextField("Identifiant");
		nom = new TextField("Nom");
		prenom = new TextField("Prénom");
		adresse = new TextField("Adresse");
		ville = new TextField("Ville");
		numeroPieceId = new TextField("Numéro Id");
		
		genre = new ComboBox<Genre>("Genre");
		genre.setItems(Genre.asList());
		genre.setEmptySelectionAllowed(false);
		
		pieceIdentite = new ComboBox<PieceIdentite>("Pièce Id");
		pieceIdentite.setItems(PieceIdentite.asList());
		pieceIdentite.setEmptySelectionAllowed(false);
		
		dateNaissance = new DateField("Date Naissance");
		
		role = new ComboBox<>("Profile");
		role.setItems(Role.getAllManagerRoles());
		
		addField(role);
		addField(identifiant);
		addField(nom);
		addField(prenom);
		addField(adresse);
		addField(ville);
		addField(numeroPieceId);
		addField(genre);
		addField(pieceIdentite);
		addField(dateNaissance);
		addField(droits);
		
	}
	
	public void bindForm(BeanValidationBinder<Manager> binder){
		binder.forField(droits)
		.bind(Manager::getDroits, Manager::setDroits);
		binder.forField(nom).bind(Manager::getNom, Manager::setNom);
		binder.forField(prenom).bind(Manager::getPrenom, Manager::setPrenom);
		binder.forField(adresse).bind(Manager::getAdresse, Manager::setAdresse);
		binder.forField(ville).bind(Manager::getVille, Manager::setVille);
		binder.forField(numeroPieceId).bind(Manager::getNumeroPieceId, Manager::setNumeroPieceId);
		binder.forField(genre).bind(Manager::getGenre, Manager::setGenre);
		binder.forField(pieceIdentite).bind(Manager::getPieceIdentite, Manager::setPieceIdentite);
		binder.forField(dateNaissance).bind(Manager::getDateNaissance, Manager::setDateNaissance);
		binder.forField(identifiant).bind(Manager::getUsername, Manager::setUsername);
	}
}
