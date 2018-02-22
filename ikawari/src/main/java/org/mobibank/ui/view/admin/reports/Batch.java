package org.mobibank.ui.view.admin.reports;

import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.numerate.TypeRapport;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.themes.ValoTheme;

public class Batch extends CssLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Label title;
	
	public Batch() {
		setSizeFull();
		addStyleName("form-template");
		
		title = new Label("Explorateur de rapports de fin de journnée");
		title.addStyleName("title");
		addComponent(title);
		
		CssLayout filtre = new CssLayout();
		filtre.setCaption("Filtre");
		filtre.addStyleName("form-section");
		addComponent(filtre);
		
		ComboBox<Entite> entite = new ComboBox<Entite>("Entite");
		entite.setPlaceholder("Entité");
		
		ComboBox<Agent> agent = new ComboBox<Agent>("Agent");
		agent.setPlaceholder("Entité");
		
		filtre.addComponent(inputGroup(entite, agent));
		
		ListSelect<TypeRapport> rapport = new ListSelect<>("Type de Rapport");
		rapport.setItems(TypeRapport.asList());
		rapport.setWidth("100%");
		CssLayout inputGroup = new CssLayout();
		inputGroup.addStyleName("input-group");
		CssLayout inputWrapper = new CssLayout();
		inputWrapper.addStyleName("input-wrapper align-bottom");
		inputWrapper.setWidth("100%");
		inputWrapper.addComponent(rapport);
		inputGroup.addComponent(inputWrapper);
		entite.setWidth("100px");
		filtre.addComponent(inputGroup);
		
		DateField from = new DateField("De");
		DateField to = new DateField("A");
		
		filtre.addComponent(inputGroup(from, to));
		
		CssLayout validerSection = new CssLayout();
		validerSection.addStyleName("form-section");
		addComponent(validerSection);
		
		Button valider = new Button("Valider", VaadinIcons.CHECK_CIRCLE);
		valider.addStyleName(ValoTheme.BUTTON_PRIMARY);
		
		validerSection.addComponent(inputGroup(valider));
	}
	
	private CssLayout inputGroup(Component... components) {
		CssLayout inputGroup = new CssLayout();
		inputGroup.addStyleName("input-group");
		
		for(Component c : components) {
			CssLayout inputWrapper = new CssLayout();
			inputWrapper.addStyleName("input-wrapper align-bottom");
			inputWrapper.addComponent(c);
			inputGroup.addComponent(inputWrapper);
		}
		return inputGroup;
	}

}
