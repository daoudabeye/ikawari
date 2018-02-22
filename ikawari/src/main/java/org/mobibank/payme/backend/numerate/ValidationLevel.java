package org.mobibank.payme.backend.numerate;

public enum ValidationLevel {

	HIGHT("Hight"),
	MEDIUM("Medium"),
	LOW("Low");
	
	private String value;
	private ValidationLevel(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
