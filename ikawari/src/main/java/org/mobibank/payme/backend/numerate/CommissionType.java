package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum CommissionType {
	
	MARKETING("800"),
	MASTER("802"),
	BANQUE("803"),
	DISTRIBUTEUR("804"),
	DISTRIBUTEUR_R("805"),
	AGENT("806"),
	SYSTEME("807"),
	PROVIDER("808");
	
	private String value;
	private CommissionType(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}

	public static Collection<CommissionType> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(CommissionType.values());
	}
}
