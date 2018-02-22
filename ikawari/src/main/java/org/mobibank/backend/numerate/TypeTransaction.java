package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum TypeTransaction {

	DEBIT("DEBIT"),
	CREDIT("CREDIT");
	
	private String value;
	private TypeTransaction(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}

	public static Collection<TypeTransaction> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(TypeTransaction.values());
	}
}
