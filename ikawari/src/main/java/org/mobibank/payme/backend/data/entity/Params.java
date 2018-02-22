package org.mobibank.payme.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.mobibank.payme.backend.numerate.ServiceParams;

@Embeddable
public class Params {

	@Column(name = "parametre")
	private ServiceParams parametre;
	
	@Column(name = "valeur")
	private String valeur;
	
	public Params() {
		// TODO Auto-generated constructor stub
	}
	
	public Params(ServiceParams parametre, String valeur) {
		super();
		this.parametre = parametre;
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return parametre+"("+valeur+")";
	}

	public ServiceParams getParametre() {
		return parametre;
	}

	public void setParametre(ServiceParams parametre) {
		this.parametre = parametre;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}	
}
