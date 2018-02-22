package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum Genre {

	HOMME, FEMME;
	
	public static List<Genre> asList(){
		return Arrays.asList(Genre.values());
	}
}
