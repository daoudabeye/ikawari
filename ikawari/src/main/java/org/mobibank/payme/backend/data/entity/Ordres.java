package org.mobibank.payme.backend.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PAYME")
public class Ordres extends Services {
	
	private Boolean national;

	public Ordres() {
		super();
		this.national = false;
	}

	public Boolean getNational() {
		return national;
	}

	public void setNational(Boolean national) {
		this.national = national;
	}
	
}
