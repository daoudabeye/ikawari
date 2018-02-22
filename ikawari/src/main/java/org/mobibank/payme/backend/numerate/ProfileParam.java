package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum ProfileParam {

	SOLDE_MAX, SOLDE_MIN, DEPASSEMENT, NBR_OP_JOURS,
	PASSWORD_LIFE, TENTATIVE, ACCESS_LOCK;
	
	public static List<ProfileParam> asList(){
		return Arrays.asList(ProfileParam.values());
	}
}
