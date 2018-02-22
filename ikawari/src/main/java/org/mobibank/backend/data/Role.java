package org.mobibank.backend.data;

/**
 * 
 * @author BEYE
 *
 */
public class Role {
	public static final String ADMIN = "ADMIN";
	public static final String CONTROLLER = "CONTROLLER";
	public static final String PROVIDER = "PROVIDER";
	public static final String BANQUE = "BANQUE";
	public static final String MASTER = "MASTER";
	public static final String POINTSERVICE = "POINTSERVICE";
	public static final String AMBASSADEUR = "AMBASSADEUR";
	public static final String DISTRIBUTEUR = "DISTRIBUTEUR";
	public static final String OPERATEUR = "OPERATEUR";
	public static final String CLIENT = "CLIENT";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] { ADMIN, CONTROLLER, PROVIDER, BANQUE,  MASTER, POINTSERVICE, AMBASSADEUR, DISTRIBUTEUR, CLIENT};
	}
	
	public static String[] getAllStructures() {
		return new String[] { PROVIDER, BANQUE, POINTSERVICE};
	}

	public static String[] getAllAgentRoles() {
		return new String[] { MASTER, AMBASSADEUR, DISTRIBUTEUR, OPERATEUR};
	}
	
	public static String[] getAllManagerRoles() {
		return new String[] { ADMIN, CONTROLLER };
	}
	
}
