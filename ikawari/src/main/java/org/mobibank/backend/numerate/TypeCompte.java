package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum TypeCompte {
	
	PRINCIPALE("200"),
	ATTENTE("202"),
	DESTRUCTION("203"),
	COMMISSION("204"),
	TAXES("205"),
	SYSTEME("100"),
	PIVOT("100"),
	MARKTING("101");
	
	private String value;
	private TypeCompte(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}

	public static Collection<TypeCompte> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(TypeCompte.values());
	}
}
