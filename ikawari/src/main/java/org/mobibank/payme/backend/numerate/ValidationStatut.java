package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum ValidationStatut {

	VALIDER,
	INSTANCE,
	REJETER;
	
	public String getValue() {
		return this.name();
	}
	
	public static List<ValidationStatut> asList(){
		return Arrays.asList(ValidationStatut.values());
	}
}
