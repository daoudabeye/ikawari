package org.mobibank.backend.data.entity;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mobibank.backend.numerate.Context;
import org.mobibank.backend.numerate.SchemaCompte;
import org.mobibank.backend.numerate.TypeTransaction;

@Entity
@Table(name = Sequences.TABLE_NAME)
@NamedQuery(name="Sequences.findAll", query = "SELECT s FROM Sequences s")
public class Sequences extends AbstractEntity{

	public static final String TABLE_NAME = "sequences";
	
	//Ordre d'execution
	private int sequence;
	
	/**
	 * Sens de l'opération Débit ou Crédit
	 */
	@Enumerated
	private TypeTransaction sens;
	
	/**
	 * Le type de compte concerné par cette opération
	 */
	@Enumerated
	private SchemaCompte compte;
	
	/**
	 * Contient un ou plusieur element du context en vu d'un somme
	 * ex : M + F = montant + frais
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Context> context;
	
	@ManyToOne
	@JoinColumn
	private Services service;
	
	private String description;

	public Sequences() {
		super();
	}
	public Sequences(TypeTransaction sens, SchemaCompte compte, Set<Context> context) {
		super();
		this.sens = sens;
		this.compte = compte;
		this.context = context;
	}

	public void update(Sequences s) {
		this.sens = s.getSens();
		this.compte = s.getCompte();
		this.context = s.getContext();
		this.description = s.getDescription();
		this.sequence = s.getSequence();
		this.service = s.getService();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sequence +"-"+sens +"-" + compte + "-" + toStringContext();
	}
	
	public String toStringContext(){
		String t = "";
		
		for(Context c : context){
			t += c;
		}
		return t;
	}
	public TypeTransaction getSens() {
		return sens;
	}

	public void setSens(TypeTransaction sens) {
		this.sens = sens;
	}

	public SchemaCompte getCompte() {
		return compte;
	}

	public void setCompte(SchemaCompte compte) {
		this.compte = compte;
	}

	public Set<Context> getContext() {
		return context;
	}

	public void setContext(Set<Context> context) {
		this.context = context;
	}
	public Services getService() {
		return service;
	}
	public void setService(Services services) {
		this.service = services;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
