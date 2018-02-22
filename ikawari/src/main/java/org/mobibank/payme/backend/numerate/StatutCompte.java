package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum StatutCompte {

	ACTIF,
	GELER,
	INACTIF,
	INSTANCE;

	public static Collection<StatutCompte> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(StatutCompte.values());
	}
	
}
