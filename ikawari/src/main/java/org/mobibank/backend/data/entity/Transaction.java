package org.mobibank.backend.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mobibank.backend.numerate.Module;
import org.mobibank.backend.numerate.StatutCode;
import org.mobibank.backend.numerate.TypeTransaction;

@Entity
@Table(name = Transaction.TABLE_NAME )
@NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")
public class Transaction extends AbstractEntity {
	
	public static final String TABLE_NAME = "transaction";

	private String reference;
	
	@Enumerated
	private TypeTransaction type;
	
	@Enumerated
	private Module operation;
	
	@Enumerated
	private StatutCode statut;
	
	private Double montant;
		
	private String note;
	
	private LocalDate dateValeur;
	
	private Long validationReference;
	
	private int sequence;
	
	@ManyToOne
	@JoinColumn
	private Compte compte;
	
	public Transaction(String reference, TypeTransaction type, Module operation, StatutCode statut,
			Double montant, LocalDate dateValeur) {
		super();
		this.reference = reference;
		this.type = type;
		this.operation = operation;
		this.statut = statut;
		this.montant = montant;
		this.dateValeur = dateValeur;
	}
	
	public Transaction(String reference, TypeTransaction type, Module operation, StatutCode statut,
			Double montant, String note, LocalDate dateValeur,
			Long validationReference, Compte compte) {
		super();
		this.reference = reference;
		this.type = type;
		this.operation = operation;
		this.statut = statut;
		this.montant = montant;
		this.note = note;
		this.dateValeur = dateValeur;
		this.validationReference = validationReference;
		this.compte = compte;
	}

	public Transaction() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public Boolean isValidate(){
		return !statut.equals(StatutCode.INSTANCE);
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public TypeTransaction getType() {
		return type;
	}

	public void setType(TypeTransaction type) {
		this.type = type;
	}

	public Module getOperation() {
		return operation;
	}

	public void setOperation(Module operation) {
		this.operation = operation;
	}

	public StatutCode getStatut() {
		return statut;
	}

	public void setStatut(StatutCode statut) {
		this.statut = statut;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalDate getDateValeur() {
		return dateValeur;
	}

	public void setDateValeur(LocalDate dateValeur) {
		this.dateValeur = dateValeur;
	}

	public Long getValidationReference() {
		return validationReference;
	}

	public void setValidationReference(Long validationReference) {
		this.validationReference = validationReference;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
