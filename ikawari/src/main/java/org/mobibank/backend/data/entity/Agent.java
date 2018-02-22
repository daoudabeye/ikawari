package org.mobibank.backend.data.entity;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.mobibank.backend.numerate.Genre;

/**
 * 
 * @author BEYE
 * 
 * Table Agent : MASTER, DISTRIBUTEUR, OPERATEUR
 * 
 * MASTER : le profile master gère une zone et dispose de sous agents
 * qui font des opérations sur lesquelles il gagne des commissions
 * 
 * DISTRIBUTEUR : le profile distributeur est en rélation direct avec le client
 * il dispose d'un compte principale et d'un compte commission sur lequel ses gains
 * lui sont reversé.
 * 
 * OPERATEUR : le profile Opérateur ne dispose pas de compte propre
 * il opére sur le compte d'une stucture.
 *
 */
@Entity
//@Table(name = Agent.TABLE_NAME)
@DiscriminatorValue(value = "AGENT")
public class Agent extends PersonnePhysique {
	
//	public static final String TABLE_NAME = "agent";
	
	private Boolean isPayeur;

	//L'agent peut avoir plusieurs sous agent ex: Master
	//mapping n:1 agent -> master
	@OneToMany(mappedBy="master")
	private Set<Agent> sousAgents;
	
	//L'agent paut appartenir à un autre agent
	//mapping 1: master -> agent
	@ManyToOne
	@JoinColumn
	private Agent master;
	
	//L'agent peut appartenir à une structure ex: Agence
	//mapping n:1 Agents -> agence
	@ManyToOne
	@JoinColumn
	private Structure structure;
	
//	//L'agent peut avoir payer des transactions
//	@OneToMany(mappedBy="agent")
//	private Set<Transfert> transferts;

	//Mapping 1:n Master -> points services
	@OneToMany(mappedBy="master")
	private Set<Structure> servicePoints;
	
	public Agent() {
		super();
		this.setPassword("paymesys");
		this.isPayeur = false;
	}
	public Agent(String username, String name, String password, String role, String nom, String prenom, String ville, Genre genre, Agent master) {
		super(username, password, role);
		this.nom = nom;
		this.prenom = prenom;
		this.ville = ville;
		this.genre = genre;
		this.master = master;
		this.isPayeur = false;
	}

	public Boolean isPayeur() {
		return isPayeur;
	}
	public void setPayeur(Boolean isPayeur) {
		this.isPayeur = isPayeur;
	}
	public Agent getMaster() {
		return master;
	}

	public void setMaster(Agent master) {
		this.master = master;
	}
	
	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	public Set<Agent> getSousAgents() {
		return sousAgents;
	}
	public void setSousAgents(Set<Agent> sousAgents) {
		this.sousAgents = sousAgents;
	}
//	public Set<Transfert> getTransferts() {
//		return transferts;
//	}
//	public void setTransferts(Set<Transfert> transferts) {
//		this.transferts = transferts;
//	}
	public Set<Structure> getServicePoints() {
		return servicePoints;
	}
	public void setServicePoints(Set<Structure> servicePoints) {
		this.servicePoints = servicePoints;
	}

}
