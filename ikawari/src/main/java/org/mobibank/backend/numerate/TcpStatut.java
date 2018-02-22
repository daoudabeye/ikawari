package org.mobibank.backend.numerate;

public enum TcpStatut {

	SEN,
	ACK,
	TRANSMIS,
	ATTENTE;
	
	public String value() {
		return name();
	}
	
	public static TcpStatut formValue(String v){
		return valueOf(v);
	}
}
