package org.mobibank.payme.ui;

import org.mobibank.payme.backend.data.Role;

import com.vaadin.ui.ComboBox;

@SuppressWarnings("serial")
public class RoleSelect extends ComboBox<String> {

	/**
	 * 
	 */

	public RoleSelect(String[] roleItems) {
		setCaption("Role");
		setEmptySelectionAllowed(false);
		setItems(Role.getAllRoles());
		setTextInputAllowed(false);
		setCaption("Profile");
		setItems(roleItems);
		
	}
	
	public RoleSelect() {
		setCaption("Role");
		setEmptySelectionAllowed(false);
		setItems(Role.getAllRoles());
		setTextInputAllowed(false);
		setCaption("Profile");

	}
}
