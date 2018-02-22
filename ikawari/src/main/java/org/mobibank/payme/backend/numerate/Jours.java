package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum Jours {

	LUNDI,
	MARDI,
	MERCREDI,
	JEUDI,
	VENDREDI,
	SAMEDI,
	DIMANCHE;

	public static List<Jours> asList(){
		return Arrays.asList(Jours.values());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name();
	}
}
