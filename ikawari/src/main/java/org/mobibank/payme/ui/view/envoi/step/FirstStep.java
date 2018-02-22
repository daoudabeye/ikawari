package org.mobibank.payme.ui.view.envoi.step;

import java.util.HashSet;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.StringUtils;
import org.mobibank.payme.backend.data.entity.Agent;
import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Structure;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.numerate.TypeTransfert;
import org.mobibank.payme.backend.services.AgentService;
import org.mobibank.payme.backend.services.OrdresService;
import org.mobibank.payme.backend.services.PaysService;
import org.mobibank.payme.backend.services.StructureService;
import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class FirstStep extends Step {

	public ComboBox<Ordres> services;
	public ComboBox<Pays> destination;
	public ComboBox<Agent> payeur;
	public ComboBox<Structure> pointService;
	public ComboBox<TypeTransfert> deliveryMethode;
	
	public TextField montant;

	public FirstStep() {
		super();

		ResponsiveLayout responsiveLayout = new ResponsiveLayout();
		ResponsiveRow row = responsiveLayout.addRow()
				.withAlignment(Alignment.MIDDLE_LEFT)
				.withMargin(true);

		services = new ComboBox<Ordres>("Services");
		services.setEmptySelectionAllowed(false);
		services.setWidth("200px");
		services.setItems(BeanLocator.find(OrdresService.class).findForCurrentUser(Module.ENVOIS));
		services.setItemCaptionGenerator(e -> e.getName());
		row.addComponent(services);
		
		montant = new TextField("Montant");
		montant.setPlaceholder("Montant transfert");
		row.addComponent(montant);

		ResponsiveRow row1 = responsiveLayout.addRow()
				.withAlignment(Alignment.MIDDLE_LEFT)
				.withMargin(true);
		
		destination = new ComboBox<>("Pays");
		destination.setPlaceholder("Pays destination");
		destination.setEmptySelectionAllowed(false);

		row1.addComponent(destination);

		payeur = new ComboBox<>("Payeur");
		payeur.setPlaceholder("Payeur");
		payeur.setEmptySelectionAllowed(false);
		payeur.setItemCaptionGenerator(p -> p.getNom());
		row1.addComponent(payeur);

		pointService = new ComboBox<>("Point Service");
		pointService.setPlaceholder("Point service");
		pointService.setEmptySelectionAllowed(false);
		pointService.setItemCaptionGenerator(e -> e.getDesignation());
		row1.addComponent(pointService);

		ResponsiveRow row2 = responsiveLayout.addRow()
				.withAlignment(Alignment.MIDDLE_LEFT)
				.withMargin(true);

		deliveryMethode = new ComboBox<TypeTransfert>("Type");
		deliveryMethode.setItems(TypeTransfert.asList());
		row2.addComponent(deliveryMethode);

		getNextButton().setCaption("Suivant");
		addStepNextListener(e -> {
			Stepper stepper = e.getSource();
			stepper.hideError();
			if (services.getValue() == null && StringUtils.isEmpty(montant.getValue())) {
				stepper.showError(new RuntimeException("Veuillez remplir correctement la section."));
			} else {
				stepper.next();
			}
		});

		services.addSelectionListener(e -> {
			payeur.setSelectedItem(null);

			if(e.getValue().getNational()) {
				destination.setEnabled(false);
				destination.setSelectedItem(null);
				payeur.setItems(BeanLocator.find(AgentService.class).findLocalPayeurs());
				destination.setItems(new HashSet<>());
			}
			else{
				destination.setEnabled(true);
				payeur.setItems(new HashSet<>());
				destination.setItems(BeanLocator.find(PaysService.class).findAvailableDst());
			}
		});

		destination.addSelectionListener(e -> {
			if(e.getValue() != null)
				payeur.setItems(BeanLocator.find(AgentService.class).findPayeurs(e.getValue()));
			else{
				payeur.setItems(new HashSet<>());
				payeur.setSelectedItem(null);
			}
		});

		payeur.addSelectionListener(e -> {
			pointService.setSelectedItem(null);
			pointService.setItems(BeanLocator.find(StructureService.class).findServicePoint(e.getValue()));
		});

		setCaption("Services");
		setIcon(VaadinIcons.CASH);
		setDescription("Services disponible");
		setContent(responsiveLayout);
	}

}
