package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum PieceIdentite {

	CARTE_IDENTITE("Carte d'identite"),
	CARTE_CONSULAIRE("Carte consulaire"),
	PASSPORT("Passport"),
	PERMIS("Permis"),
	AUTRE("Autre");
	
	private String value;
	
	private PieceIdentite(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static PieceIdentite from(String value){
		if(value.equals("Carte d'identite"))
			return PieceIdentite.CARTE_IDENTITE;
		else if(value.equals("Carte consulaire"))
			return PieceIdentite.CARTE_CONSULAIRE;
		else if(value.equals("Passport"))
			return PieceIdentite.PASSPORT;
		else if(value.equals("Autre"))
			return PieceIdentite.AUTRE;
		return null;
	}

	public static Collection<PieceIdentite> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(PieceIdentite.values());
	}
}
