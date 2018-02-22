package org.mobibank.ui.view.envoi;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.numerate.Devise;
import org.mobibank.backend.operations.Envois;
import org.mobibank.backend.services.OperationService;
import org.mobibank.ui.view.envoi.step.ConfirmationStep;
import org.mobibank.ui.view.envoi.step.FirstStep;
import org.mobibank.ui.view.envoi.step.ResumeStep;
import org.mobibank.ui.view.envoi.step.UsersStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;
import org.vaadin.addons.md_stepper.VerticalStepper;
import org.vaadin.dialogs.ConfirmDialog;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = EnvoiView.VIEW_NAME)
@MenuCaption("Envoi")
@MenuIcon(VaadinIcons.ARROW_FORWARD)
@Secured({Role.DISTRIBUTEUR, Role.OPERATEUR})
public class EnvoiView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Envoi";
	@Autowired
	OperationService operationService;
	
	FirstStep firstStep;
	UsersStep usersStep;
	ResumeStep resumeStep;
	ConfirmationStep lastStep;
	
	@PostConstruct
	public void init() {
		setWidth("100%");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		
		firstStep = new FirstStep();
		usersStep = new UsersStep(this);
		lastStep = new ConfirmationStep();
		
		resumeStep = new ResumeStep();
		
		resumeStep.addStepNextListener(n -> {
			Stepper stepper = n.getSource();
			ConfirmDialog.show(getUI(), "Etes vous sûre de vouloire effectuer cette opération ?", e -> {
				if(e.isConfirmed()) {
					stepper.showFeedbackMessage("Traitement en cours...");
					Envois envois = operationService.envois(firstStep.services.getValue(), firstStep.deliveryMethode.getValue(),
							Double.valueOf(firstStep.montant.getValue()), firstStep.destination.getValue(),
							usersStep.receiver, usersStep.sender, firstStep.payeur.getValue(),
							firstStep.pointService.getValue(), Devise.CFA);
					lastStep.showReceipt(envois);
					stepper.hideFeedbackMessage();
					stepper.next();
				}
			});
		});
		
		List<Step> steps = Arrays.asList(firstStep, usersStep, resumeStep, lastStep);
		VerticalStepper stepper = new VerticalStepper(steps, true);
		stepper.setSizeFull();
		stepper.start();

		addComponent(stepper);
	}
	
	public Label setUserInfos() {
		// TODO Auto-generated method stub
		String dest = firstStep.services.getValue().getNational() ? "National" : firstStep.destination.getValue().getNom();
		
		Label label = new Label();
		label.setContentMode(ContentMode.HTML);
		StringBuilder builder = new StringBuilder();
		builder.append("Envois de <b>").append(firstStep.montant.getValue()).append(" </b>")
		.append("<br />Déstination : ").append(dest)
		.append("<br />Expéditeur : ").append(usersStep.sender)
		.append("<br />Bénéficiare: ").append(usersStep.receiver)
		.append("<br />Payeur : ").append(firstStep.payeur.getValue())
		.append("<br />Point Service : " ).append(firstStep.pointService.getValue())
		.append("<br />Service : ").append(firstStep.deliveryMethode.getValue());
		
		label.setValue(builder.toString());
		
		resumeStep.content.setValue(builder.toString());
		
		return label;
	}
}
