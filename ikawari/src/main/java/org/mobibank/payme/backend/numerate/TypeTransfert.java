package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.List;

public enum TypeTransfert {

	CASH_TO_CASH("CASH_TO_CASH"),
	MOBILE_WALLET("MOBILE_WALLET"),
	CASH_CARD_RELOAD("CASH_CARD_RELOAD"),
	PICK_UP_ANYWHERE("PICK_UP_ANYWHERE"),
	BANK_DEPOSIT("BANK_DEPOSIT");
	
	private String value;
	private TypeTransfert(String value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static List<TypeTransfert> asList(){
		return Arrays.asList(TypeTransfert.values());
	}
	
}
