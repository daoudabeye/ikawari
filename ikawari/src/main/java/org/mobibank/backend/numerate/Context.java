package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum Context {

	PRINCIPAL, FRAIS, TAXE, C_DIST, C_DIST_R, C_PRO,
	C_MASTER, R_MARKETING, C_BANK;

	public static Collection<Context> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(Context.values());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name();
	}
}
