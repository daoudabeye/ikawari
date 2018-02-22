package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Client;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.numerate.Genre;
import org.mobibank.backend.numerate.PieceIdentite;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class ClientViewDesign extends AbstractCrudViewDesign<Client> {

	/**
	 * 
	 */
	protected Grid<Client> list;
	protected TextField nom;
	protected TextField prenom;
	protected TextField  adresse;
	protected TextField  ville;
	protected TextField  numeroPieceId;
	protected ComboBox<Genre> genre;
	protected ComboBox<PieceIdentite> pieceIdentite;
	protected DateField dateNaissance;
	TextField username;
	ComboBox<String> role;
	
	protected ComboBox<Pays> pays;
	protected ComboBox<Entite> entites;
	protected TextField telephone;

	public ClientViewDesign() {
		
		list = new Grid<Client>(Client.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("nom", "prenom","adresse", "ville", "pieceIdentite","numeroPieceId", "genre","dateNaissance");
		addGrid(list);
		
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
		role = new ComboBox<String>("Role");
		role.setItems(Role.CLIENT);
		
		username = new TextField("Username");
		
		pays = new ComboBox<>("Pays");
		pays.setEmptySelectionAllowed(false);
		entites = new ComboBox<Entite>("Entite");
		
		telephone = new TextField("Téléphone");
		
		addField(role);
		addField(username);
		addField(telephone);
		addField(nom);
		addField(prenom);
		addField(genre);
		addField(adresse);
		addField(ville);
		addField(pays);
		addField(entites);
		addField(numeroPieceId);
		addField(pieceIdentite);
		addField(dateNaissance);
		
	}
	
	public void bindForm(BeanValidationBinder<Client> binder){
		binder.forField(nom).bind(Client::getNom, Client::setNom);
		binder.forField(prenom).bind(Client::getPrenom, Client::setPrenom);
		binder.forField(adresse).bind(Client::getAdresse, Client::setAdresse);
		binder.forField(ville).bind(Client::getVille, Client::setVille);
		binder.forField(numeroPieceId).bind(Client::getNumeroPieceId, Client::setNumeroPieceId);
		binder.forField(genre).bind(Client::getGenre, Client::setGenre);
		binder.forField(pieceIdentite).bind(Client::getPieceIdentite, Client::setPieceIdentite);
		binder.forField(dateNaissance).bind(Client::getDateNaissance, Client::setDateNaissance);
		binder.forField(role).bind(Client::getRole, Client::setRole);
		binder.forField(username).bind(Client::getUsername, Client::setUsername);
		binder.forField(entites).bind(Client::getEntite, Client::setEntite);
		binder.forField(telephone).bind(Client::getTelephone, Client::setTelephone);
	}
}
