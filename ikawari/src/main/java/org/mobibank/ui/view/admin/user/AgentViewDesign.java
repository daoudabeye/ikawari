package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.numerate.Genre;
import org.mobibank.backend.numerate.PieceIdentite;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class AgentViewDesign extends AbstractCrudViewDesign<Agent> {

	/**
	 * 
	 */
	protected Grid<Agent> list;
	protected ComboBox<Agent> master;
	protected ComboBox<Structure> agence;
	protected TextField nom;
	protected TextField prenom;
	protected TextField  adresse;
	protected TextField  ville;
	protected TextField  numeroPieceId;
	protected ComboBox<Genre> genre;
	protected ComboBox<PieceIdentite> pieceIdentite;
	protected DateField dateNaissance;
	protected TextField username;
	protected ComboBox<String> role;
	
	protected ComboBox<Pays> pays;
	protected ComboBox<Entite> entites;
	protected TextField telephone;
	
	CheckBox payeur;

	public AgentViewDesign() {
		
		list = new Grid<Agent>(Agent.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("nom", "prenom","adresse", "ville", "pieceIdentite","numeroPieceId", "genre","dateNaissance");
		addGrid(list);
		
		
		master = new ComboBox<Agent>("Master");
		agence = new ComboBox<Structure>("Agence");
		
		payeur = new CheckBox("Is Payeur");
		
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
		role.setItems(Role.getAllAgentRoles());
		
		username = new TextField("Username");
		
		pays = new ComboBox<>("Pays");
		pays.setEmptySelectionAllowed(false);
		entites = new ComboBox<Entite>("Entite");
		
		telephone = new TextField("Téléphone");
		
		addField(username);
		addField(role);
		addField(payeur);
		addField(telephone);
		addField(nom);
		addField(prenom);
		addField(pays);
		addField(entites);
		addField(master);
		addField(agence);
		addField(adresse);
		addField(ville);
		addField(genre);
		addField(pieceIdentite);
		addField(numeroPieceId);
		addField(dateNaissance);
		
	}
	
	public void bindForm(BeanValidationBinder<Agent> binder){
		binder.forField(master)
		.bind(Agent::getMaster, Agent::setMaster);
		binder.forField(agence)
		.bind(Agent::getStructure, Agent::setStructure);
		binder.forField(payeur)
		.bind(Agent::isPayeur, Agent::setPayeur);
		binder.forField(nom).bind(Agent::getNom, Agent::setNom);
		binder.forField(prenom).bind(Agent::getPrenom, Agent::setPrenom);
		binder.forField(adresse).bind(Agent::getAdresse, Agent::setAdresse);
		binder.forField(ville).bind(Agent::getVille, Agent::setVille);
		binder.forField(numeroPieceId).bind(Agent::getNumeroPieceId, Agent::setNumeroPieceId);
		binder.forField(genre).bind(Agent::getGenre, Agent::setGenre);
		binder.forField(pieceIdentite).bind(Agent::getPieceIdentite, Agent::setPieceIdentite);
		binder.forField(dateNaissance).bind(Agent::getDateNaissance, Agent::setDateNaissance);
		binder.forField(role).bind(Agent::getRole, Agent::setRole);
		binder.forField(username).bind(Agent::getUsername, Agent::setUsername);
		binder.forField(entites).bind(Agent::getEntite, Agent::setEntite);
		binder.forField(telephone).bind(Agent::getTelephone, Agent::setTelephone);
	}
}
