package org.mobibank.backend.data.entity;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;

import org.mobibank.backend.numerate.Droits;

/**
 * 
 * @author BEYE
 * 
 * ADMIN, SUPERVISEUR
 *
 */
@Entity
@DiscriminatorValue(value = "MANAGER")
@NamedQuery(name = "Manager.findAll", query = "SELECT m FROM Manager m")
public class Manager extends PersonnePhysique {
	
	@ElementCollection(fetch = FetchType.EAGER)
	protected Set<Droits> droits;
	
	public Manager() {
		// TODO Auto-generated constructor stub
		super();
		this.setPassword("paymesys");
	}
	
	public Manager(String username, String name, String password, String role, String nom, Set<Droits> droits) {
		super(username, password, role);
		this.nom = nom;
		this.droits = droits;
	}

	public Set<Droits> getDroits() {
		return droits;
	}

	public void setDroits(Set<Droits> droits) {
		this.droits = droits;
	}
}
