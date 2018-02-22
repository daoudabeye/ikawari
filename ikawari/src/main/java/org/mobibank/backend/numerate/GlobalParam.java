package org.mobibank.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum GlobalParam {

	DUREE_MOT_DE_PASSE, TENTATIVE;
	
	public List<GlobalParam> asList(){
		return Arrays.asList(GlobalParam.values());
	}
	
}
