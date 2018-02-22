package org.mobibank.payme.ui.view.envoi.step;

import org.mobibank.payme.backend.operations.Envois;
import org.vaadin.addons.md_stepper.Step;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ConfirmationStep extends Step {

	/**
	 * 
	 */
	Label content = new Label("");

	public ConfirmationStep() {
		
		setCaption("Confirmation");
		setDescription("Resultat de l'execution");
		setIcon(VaadinIcons.CHECK);
		setContent(content);
	}

	public void showReceipt(Envois envois) {
		content.setValue(envois.getNote());
	}
	
}
