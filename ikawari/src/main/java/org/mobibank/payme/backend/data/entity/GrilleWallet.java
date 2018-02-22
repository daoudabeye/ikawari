package org.mobibank.payme.backend.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@DiscriminatorValue(value = "WALLET")
public class GrilleWallet extends Grille {
	
	@ManyToOne
	@JoinColumn
	private Pays pays;
	
	public GrilleWallet(Services services, double montantMin, double montantMax, double frais, Pays pays) {
		super(montantMin, montantMax, frais, services);
		this.pays = pays;
	}

	public GrilleWallet() {
		super();
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}
	
}
