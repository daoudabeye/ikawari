package org.mobibank.payme.backend.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "TRANSFERT")
public class GrilleTransfert extends Grille {
	
	@ManyToOne
	@JoinColumn
	private Corridore corridore;

	public GrilleTransfert() {
		// TODO Auto-generated constructor stub
		super();
	}
		
	public GrilleTransfert(Services services, Corridore corridore, double montantMin, double montantMax, double frais) {
		super(montantMin, montantMax, frais, services);
		this.corridore = corridore;
	}
	
	public Corridore getCorridore() {
		return corridore;
	}

	public void setCorridore(Corridore corridore) {
		this.corridore = corridore;
	}

}
