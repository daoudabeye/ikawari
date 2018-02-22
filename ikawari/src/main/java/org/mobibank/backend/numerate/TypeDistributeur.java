package org.mobibank.backend.numerate;

public enum TypeDistributeur {

	LEGER("Leger"),
	LOURD("Lourd");
	
	private String type;
	
	private TypeDistributeur(String type) {
		// TODO Auto-generated constructor stub
		this.type = type;
	}
	
	public String getValue(){
		return this.type;
	}
	
}
