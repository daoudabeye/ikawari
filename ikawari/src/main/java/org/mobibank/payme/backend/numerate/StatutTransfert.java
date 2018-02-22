package org.mobibank.payme.backend.numerate;

import com.vaadin.shared.util.SharedUtil;

public enum StatutTransfert {

	PAYER, ANNULER, TRANSMIS, INSTANCE, NOUVEAU;
	
	/**
	 * Gets a version of the enum identifier in a human friendly format.
	 *
	 * @return a human friendly version of the identifier
	 */
	public String getDisplayName() {
		return SharedUtil.upperCaseUnderscoreToHumanFriendly(name());
	}

	/**
	 * Gets a enum value for which {@link #getDisplayName()} returns the given
	 * string. Match is case-insensitive.
	 *
	 * @return the enum value with a matching display name
	 */
	public static StatutTransfert forDisplayName(String displayName) {
		for (StatutTransfert state : values()) {
			if (displayName.toLowerCase().equals(state.getDisplayName().toLowerCase())) {
				return state;
			}
		}
		return null;
	}
}
