package org.mobibank.payme.backend.numerate;

import java.util.Arrays;
import java.util.Collection;

public enum ProfileName {
	
	ADMIN,
	BANQUE,
	MASTER,
	PAYEUR,
	CLIENT,
	AGENCE,
	PROVIDER,
	OPERATEUR,
	DISTRIBUTEUR,
	CONTROLLEUR,
	AGENT,
	POINT_SERVICE;
	
	public int getId(){
        return ordinal();
    }
	
	public String role(){
		return "ROLE_" + this.name();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name();
	}
	
	public static ProfileName fromRole(String role){
		return ProfileName.valueOf(role.substring(5));
	}

	public static Collection<ProfileName> asList() {
		// TODO Auto-generated method stub
		return Arrays.asList(ProfileName.values());
	}

}
