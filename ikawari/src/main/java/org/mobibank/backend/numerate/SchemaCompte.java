package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum SchemaCompte {

	P_AGENT, C_AGENT, P_MASTER, C_MASTER,
	P_BENEF, C_BANQUE, ATTENTE, C_PROVIDER, TAXE, MARKETING,
	P_PAYER, PIVOT;
	
	public static Collection<SchemaCompte> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(SchemaCompte.values());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name();
	}
}
