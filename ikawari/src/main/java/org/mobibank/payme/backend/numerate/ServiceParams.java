package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum ServiceParams {
	LIMTT, SEUIL_VALIDATION;

	private ServiceParams() {
		// TODO Auto-generated constructor stub
	}
	public static Collection<ServiceParams> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(ServiceParams.values());
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name();
	}
}
