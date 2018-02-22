package org.mobibank.backend.data.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.mobibank.backend.numerate.PieceIdentite;

@Entity
@Table( name = TransfertUser.TABLE_NAME )
@NamedQuery(name = "TransfertUser.findAll", query = "SELECT t FROM TransfertUser t")
public class TransfertUser extends AbstractEntity {
	
	public static final String TABLE_NAME = "transfert_user";

	private String nom;
	
	private String prenom;
	
	private String adresse;
	
	private String telephone;
	
	private String email;
	
	private PieceIdentite pieceIdentite;
	
	private String numeroPieceIdentite;
	
	private String paysEmetteur;
	
	private LocalDate dateExpiration;
		
	private String pays;
	
	private String ville;
	
	@Column(name = "date_naissance")
	private LocalDate dateNaissance;
	
	@OneToOne
	private Transfert transfert;
	
	public TransfertUser() {
		// TODO Auto-generated constructor stub
		super();
	}

	public TransfertUser(String nom, String prenom, String adresse, String telephone, String email,
			PieceIdentite pieceIdentite, String numeroPieceIdentite, String pays, String ville) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.pieceIdentite = pieceIdentite;
		this.pays = pays;
		this.numeroPieceIdentite = numeroPieceIdentite;
		this.ville = ville;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String piece = numeroPieceIdentite == null ? "" : ", Pièce d'identité : "+ numeroPieceIdentite;
		return nom+" "+prenom+", Tel :" +telephone + piece;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Transfert getTransfert() {
		return transfert;
	}

	public void setTransfert(Transfert transfert) {
		this.transfert = transfert;
	}

	public String getNumeroPieceIdentite() {
		return numeroPieceIdentite;
	}

	public void setNumeroPieceIdentite(String numeroPieceIdentite) {
		this.numeroPieceIdentite = numeroPieceIdentite;
	}

	public PieceIdentite getPieceIdentite() {
		return pieceIdentite;
	}

	public void setPieceIdentite(PieceIdentite pieceIdentite) {
		this.pieceIdentite = pieceIdentite;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getPaysEmetteur() {
		return paysEmetteur;
	}

	public void setPaysEmetteur(String paysEmetteur) {
		this.paysEmetteur = paysEmetteur;
	}

	public LocalDate getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(LocalDate dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
}
