package org.mobibank.backend.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "MyPAYME")
public class MyPayme extends Services {

	private Boolean fee;
	
	public Boolean getFee() {
		return fee;
	}

	public void setFee(Boolean fee) {
		this.fee = fee;
	}

	@ManyToOne
	@JoinColumn
	private Pays pays;
	
	public MyPayme() {
		super();
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	public boolean hasFee() {
		if(fee == null)
			return false;
		else if(fee)
			return true;
		
		return false;
	}
	
}
