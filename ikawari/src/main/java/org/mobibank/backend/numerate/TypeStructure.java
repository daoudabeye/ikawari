package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum TypeStructure {

	BANQUE, PROVIDER, PAYEUR, AGENCE, POINT_SERVICE;

	public static Collection<TypeStructure> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(TypeStructure.values());
	}
}
