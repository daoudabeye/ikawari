package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum TypeRapport {

	MOUVEMENT_COMPTABLE,
	TABLEAU_DE_BORD_GENERAL,
	JOURNAL_CONTREPARTIES,
	SITUATION_GUICHET,
	DETTE_CREANCE,
	TRANSFERT_EN_SUSPENS,
	ORDRE_DE_VIREMENT,
	VERSEMENTS_RETRAITS;
	
	private TypeRapport() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<TypeRapport> asList(){
		return Arrays.asList(TypeRapport.values());
	}
}
