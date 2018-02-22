package org.mobibank.payme.backend.numerate;

public enum Taxe {

	TVA("TVA"),
	TOB("TOB"),
	TAF("TAF");
	
	private String value;
	
	Taxe(String type){
		this.value = type;
	}
	
	public String getValue(){
		return this.value;
	}
}
