package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum Devise {

	CFA, EURO, DOLLAR;
	
	public static List<Devise> asList(){
		return Arrays.asList(Devise.values());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name();
	}
}
