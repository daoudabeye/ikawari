package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum ContryParam {

	DUREE_MOT_DE_PASSE, TENTATIVE;
	
	public static List<ContryParam> asList(){
		return Arrays.asList(ContryParam.values());
	}
}
