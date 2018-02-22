package org.mobibank.backend.numerate;

public enum Virement {

	COMPTE("Vers compte Banque "),
	COMPTE_PRINCIPAL("Compte Principal");
	
	private String value;
	
	private Virement(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.value;
	}
}
