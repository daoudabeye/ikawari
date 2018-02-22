package org.mobibank.payme.backend.data.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.mobibank.payme.backend.numerate.Devise;
import org.mobibank.payme.backend.numerate.StatutCompte;
import org.mobibank.payme.backend.numerate.TypeCompte;

@Entity
@Table(name = Compte.TABLE_NAME)
@NamedQuery(name="Compte.findAll", query = "SELECT c FROM Compte c")
public class Compte extends AbstractEntity{
	
	public static final String TABLE_NAME = "comptes";

	@Enumerated
	private TypeCompte type;
	
	private BigDecimal solde;
	
	@Enumerated
	private Devise devise;
	
	@Enumerated
	private StatutCompte statut;
	
	@ManyToOne
	@JoinColumn
	private Utilisateur utilisateur;
	
	@OneToMany
	private List<Transaction> transaction;
	
	public Compte() {
		// TODO Auto-generated constructor stub
		super();
		this.solde = BigDecimal.ZERO;
	}

	public Compte(TypeCompte type, Devise devise, StatutCompte statut, BigDecimal solde, Utilisateur utilisateur) {
		super();
		this.type = type;
		this.statut = statut;
		this.solde = solde;
		this.utilisateur = utilisateur;
		this.devise = devise;
	}
	
	public Compte(TypeCompte type, Devise devise, Utilisateur utilisateur) {
		super();
		this.type = type;
		this.utilisateur = utilisateur;
		this.solde = BigDecimal.ZERO;
		this.statut = StatutCompte.INSTANCE;
		this.devise = devise;
	}
	
	public Compte(TypeCompte type, Devise devise, Utilisateur utilisateur, double solde) {
		super();
		this.type = type;
		this.utilisateur = utilisateur;
		this.statut = StatutCompte.INSTANCE;
		this.solde = BigDecimal.valueOf(solde);
		this.devise = devise;
	}
	
	public void debiter(BigDecimal montant){
		solde = solde.subtract(montant);
	}
	
	public void crediter(BigDecimal montant){
		solde = solde.add(montant);
	}

	public TypeCompte getType() {
		return type;
	}

	public void setType(TypeCompte type) {
		this.type = type;
	}

	public BigDecimal getSolde() {
		return solde;
	}

	public void setSolde(BigDecimal solde) {
		this.solde = solde;
	}

	public StatutCompte getStatut() {
		return statut;
	}

	public void setStatut(StatutCompte statut) {
		this.statut = statut;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return type+" - "+solde;
	}

	public Devise getDevise() {
		return devise;
	}

	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
