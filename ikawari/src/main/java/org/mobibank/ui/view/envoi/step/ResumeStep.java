package org.mobibank.ui.view.envoi.step;

import org.vaadin.addons.md_stepper.Step;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class ResumeStep extends Step {

	public Label content;
	
	public ResumeStep() {
		super(false);

		getBackButton().setCaption("Retour");
		getNextButton().setCaption("Confirmer");
		
		setEditable(false);
		setCaption("Recap & Validation");
		setDescription("résume des saisies");
		setIcon(VaadinIcons.CLIPBOARD_TEXT);
		
		ResponsiveLayout layout = new ResponsiveLayout();
		this.setContent(layout);
		
		content = new Label("Resume");
		content.setContentMode(ContentMode.HTML);
		
		ResponsiveRow row = layout.addRow()
		.withAlignment(Alignment.MIDDLE_CENTER)
		.withMargin(true)
		.withComponents(content);
		
		row.setSizeFull();
		
	}

}
