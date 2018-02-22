package org.mobibank.backend.data.entity;

import java.time.LocalDate;

import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.mobibank.backend.numerate.Genre;
import org.mobibank.backend.numerate.PieceIdentite;

@MappedSuperclass
public class PersonnePhysique extends Utilisateur {

	protected String nom;
	
	protected String prenom;
	
	protected String adresse;
	
	protected String ville;
	
	@Enumerated
	protected Genre genre;
	
	@Enumerated
	protected PieceIdentite pieceIdentite;
	
	protected String numeroPieceId;
	
	protected LocalDate dateNaissance;
	
	public PersonnePhysique() {
		super();
	}
	
	public PersonnePhysique(String username, String password, String role) {
		super(username, password, role);
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public PieceIdentite getPieceIdentite() {
		return pieceIdentite;
	}

	public void setPieceIdentite(PieceIdentite pieceIdentite) {
		this.pieceIdentite = pieceIdentite;
	}

	public String getNumeroPieceId() {
		return numeroPieceId;
	}

	public void setNumeroPieceId(String numeroPieceId) {
		this.numeroPieceId = numeroPieceId;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
}
