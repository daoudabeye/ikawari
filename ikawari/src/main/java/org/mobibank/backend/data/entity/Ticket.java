package org.mobibank.backend.data.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mobibank.backend.numerate.StatutTicket;

@Entity
@Table(name = Ticket.TABLE_NAME)
@NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
public class Ticket extends AbstractEntity {
	
	public static final String TABLE_NAME = "ticket";

	@Column(unique = true)
	private String token;
	
	private BigDecimal valeur;
	
	private LocalDateTime expiration;
	
	private Boolean self;
	
	private String beneficiaire;
	
	@Enumerated
	StatutTicket statut;
	
	@ManyToOne
	@JoinColumn
	private Client client;

	public Ticket() {
		super();
	}
	
	public Ticket(String token, BigDecimal valeur, LocalDateTime expiration, Boolean self, String beneficiaire) {
		super();
		this.token = token;
		this.valeur = valeur;
		this.expiration = expiration;
		this.self = self;
		this.beneficiaire = beneficiaire;
		statut = StatutTicket.VALIDE;
	}

	public BigDecimal getValeur() {
		return valeur;
	}

	public void setValeur(BigDecimal valeur) {
		this.valeur = valeur;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public Boolean getSelf() {
		return self;
	}

	public void setSelf(Boolean self) {
		this.self = self;
	}

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public StatutTicket getStatut() {
		return statut;
	}

	public void setStatut(StatutTicket statut) {
		this.statut = statut;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
