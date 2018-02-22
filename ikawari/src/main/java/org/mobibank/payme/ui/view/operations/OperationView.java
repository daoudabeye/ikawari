package org.mobibank.payme.ui.view.operations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.HasLogger;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.operations.Operation;
import org.mobibank.payme.backend.services.MyPaymeService;
import org.mobibank.payme.backend.services.OperationService;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.addons.md_stepper.HorizontalStepper;
import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;
import org.vaadin.addons.md_stepper.VerticalStepper;
import org.vaadin.dialogs.ConfirmDialog;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.jarektoro.responsivelayout.ResponsiveRow.SpacingSize;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@SpringView(name = OperationView.VIEW_NAME)
@MenuCaption("Operation")
@MenuIcon(VaadinIcons.ARROW_FORWARD)
@Secured({Role.BANQUE, Role.DISTRIBUTEUR, Role.OPERATEUR, Role.MASTER, Role.CLIENT})
public class OperationView extends VerticalLayout implements View, HasLogger{
	public static final String VIEW_NAME = "Operation";
	Step serviceStep, operationStep, executionStep;

	ComboBox<MyPayme> services;
	TextField numero, montant;
	Label resultat;

	@PostConstruct
	public void init() {

		services = new ComboBox<MyPayme>("Opération");
		services.setEmptySelectionAllowed(false);

		ResponsiveLayout responsiveLayoutStep1 = new ResponsiveLayout();
		ResponsiveRow row = responsiveLayoutStep1.addRow()
				.withAlignment(Alignment.MIDDLE_CENTER)
				.withMargin(true);
		row.addComponent(services);

		numero = new TextField("Numéro");

		montant = new TextField("Montant");
		
		resultat = new Label();
		resultat.setContentMode(ContentMode.HTML);

		ResponsiveLayout responsiveLayoutStep2 = new ResponsiveLayout();
		ResponsiveRow rowStep2 = responsiveLayoutStep2.addRow()
				.withAlignment(Alignment.MIDDLE_CENTER)
				.withMargin(true)
				.withSpacing(SpacingSize.SMALL, true);

		ResponsiveColumn column1 = new ResponsiveColumn();
		column1.setComponent(numero);
		rowStep2.addColumn(column1);

		ResponsiveColumn column2 = new ResponsiveColumn();
		column2.setComponent(montant);
		rowStep2.addColumn(column2);

		serviceStep = new Step(false, "Operation", responsiveLayoutStep1);
		serviceStep.getNextButton().setCaption("Suivant");
		serviceStep.setEditable(true);
		serviceStep.addStepNextListener(e -> {
			Stepper stepper = e.getSource();
			stepper.hideError();
			if (services.getValue() == null) {
				stepper.showError(new RuntimeException("Veuillez selectionner le service"));
			} else {
				stepper.next();
			}
		});

		operationStep = new Step(false, "Opération", "Détails de l'opération", responsiveLayoutStep2);
		operationStep.getBackButton().setCaption("Retour");
		operationStep.getNextButton().setCaption("Suivant");
		operationStep.addStepNextListener(e -> {
			Stepper stepper = e.getSource();
			stepper.hideError();
			if ("".equals(numero.getValue()) || numero.getValue() == null
					|| "".equals(montant.getValue()) || montant.getValue() == null) {
				stepper.showError(new RuntimeException("Veuillez remplire le formulaire"));
			} else {
				ConfirmDialog.show(getUI(), "Etes-vous sûr de valider l'opération ?", c -> {
					if (c.isConfirmed()) {
						stepper.showFeedbackMessage("Traitement en cours...");
						//execution
						try {
							Operation operation = BeanLocator.find(OperationService.class).execute(services.getValue(),
									numero.getValue(), Double.valueOf(montant.getValue()));
							resultat.setValue(operation.getNote());
							resultat.addStyleName(ValoTheme.LABEL_SUCCESS);
						} catch (Exception e2) {
							// TODO: handle exception
							resultat.setValue(e2.getMessage());
							resultat.addStyleName(ValoTheme.LABEL_FAILURE);
						}
						
						stepper.hideFeedbackMessage();
						stepper.next();
					} else {
						// User did not confirm
						// CANCEL STUFF
						//there is nothing to do
					}
				});
			}
		});

		executionStep = new Step(true, "Execution", "Exécution de l'opération", resultat);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();

		Collection<MyPayme> availableServices = BeanLocator.find(MyPaymeService.class).findForCurrentUser();
		services.setItems(availableServices);

		List<Step> steps = Arrays.asList(serviceStep, operationStep, executionStep);

		if(smallScreen()) {
			VerticalStepper stepper = new VerticalStepper(steps, true);
			stepper.setSizeFull();
			stepper.start();

			addComponent(stepper);
		}else {
			HorizontalStepper stepper = new HorizontalStepper(steps, true);
			stepper.setSizeFull();
			stepper.addStyleName("height-400");
			stepper.start();

			addComponent(stepper);
		}

	}

	private boolean smallScreen() {
		return UI.getCurrent().getPage().getBrowserWindowWidth() < 1000;
	}
}
