package org.mobibank.backend.numerate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public enum Module {

	CREATION("CREATION"),
	DEPOT("DEPOT"),
	RETRAIT("RETRAIT"),
	TRANSFERT("TRANSFERT"),
	LISTE_MTCN("LISTE_MTCN"),
	MTCN("MTCN"),
	PAIEMENT("PAIEMENT"),
	FACTURE("FACTURE"),
	ENVOIS("ENVOIS"),
	RECEVOIR("RECEVOIR"),
	FRAIS("FRAIS"),
	ACHAT("ACHAT"),
	TAXE("TAXE"),
	COMMISSION("COMMISSION"),
	VIREMENT("VIREMENT"),
	CODE_GEN("CODE_GEN"),
	VALIDATION("VALIDATION"),
	ADMIN("ADMIN"),
	UTILISATEUR("UTILISATEUR"),
	AGENCE("AGENCE"),
	AGENT("AGENT"),
	CLIENT("CLIENT"),
	GESTION("GESTION"),
	PROFILE("PROFILE"),
	HISTORIQUE("HISTORIQUE"),
	A_PARAMS("A_PARAMS"),
	A_SERVICE("A_SERVICE"),
	A_UTILISATEUR("A_UTILISATEUR"),
	A_ZONE("A_ZONE"),
	A_PAYS("A_PAYS"),
	A_ENTITE("A_ENTITE"),
	A_PARTENAIRE("A_PAYEUR"),
	A_CORRIDORES("A_CORRIDORES"),
	A_GRILLE_W("A_GRILLE_W"),
	A_GRILLE_T("A_GRILLE_T"),
	G_MANAGER("G_MANAGER"),
	G_AGENCE("G_AGENCE"),
	G_AGENT("G_AGENT"),
	RAPPORTS("RAPPORTS"),
	RECHERCHE("RECHERCHE"),
	BATCH("BATCH"),
	RAPPORTS_BCEAO("RAPPORTS_BCEAO");
	
	private String value;
	private Module(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static List<Module> asList(){
		return Arrays.asList(Module.values());
	}
	
	public static List<Module> asListOperation(){
		ArrayList<Module> op = new ArrayList<>();
		op.add(Module.DEPOT);
		op.add(Module.RETRAIT);
		op.add(Module.PAIEMENT);
		op.add(Module.TRANSFERT);
		op.add(Module.CODE_GEN);
		op.add(Module.CREATION);
		return op;
	}
	
	public static List<Module> asListOrders(){
		ArrayList<Module> op = new ArrayList<>();
		op.add(Module.ENVOIS);
		op.add(Module.RECEVOIR);
		return op;
	}

	/**
	 * Cette fonction extrait les modules d'opération
	 * contenus dans la liste fournis en parametre.
	 * @param services
	 * @return
	 */
	public static List<Module> asListOperation(Set<Module> modules) {
		ArrayList<Module> op = new ArrayList<>();
		if(modules.contains(Module.DEPOT))op.add(Module.DEPOT);
		if(modules.contains(Module.RETRAIT))op.add(Module.RETRAIT);
		if(modules.contains(Module.TRANSFERT))op.add(Module.TRANSFERT);
		if(modules.contains(Module.PAIEMENT))op.add(Module.PAIEMENT);
		if(modules.contains(Module.CODE_GEN))op.add(Module.CODE_GEN);
		if(modules.contains(Module.CREATION))op.add(Module.CREATION);
		return op;
	}
}
