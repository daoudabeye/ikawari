package org.mobibank.payme.backend.numerate;

public enum ValidationType {

	NOUVEL_UTILISATEUR("nouvel_utilisateur"),
	TRANSACTION("transaction"),
	OPERATION("operation");
	
	private String value;
	
	private ValidationType(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
