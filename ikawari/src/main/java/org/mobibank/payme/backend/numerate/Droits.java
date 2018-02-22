package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum Droits {

	AJOUT, SUPPRESSION, MODIFICATION;
	
	public static List<Droits> asList(){
		return Arrays.asList(Droits.values());
	}
}
