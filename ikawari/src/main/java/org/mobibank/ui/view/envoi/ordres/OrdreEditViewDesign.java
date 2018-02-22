package org.mobibank.ui.view.envoi.ordres;

import java.time.LocalTime;

import org.mobibank.BeanLocator;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class OrdreEditViewDesign extends VerticalLayout{

	protected HorizontalLayout header;
	protected Label ordreId, stateLabel, due;
	protected OrdreStateSelect state;
	protected DateField dueDate, dateEnvoi;
	protected ComboBox<LocalTime> dueTime, heureEnvoi;
	PayerCombobox payeur;
	TextField nomBenef;
	TextField phoneBenef;
	
	TextField fullName;
	TextField phone;
	
	Button cancel;
	Button ok;
	
	public OrdreEditViewDesign() {
		setSpacing(false);
		setMargin(new MarginInfo(false, true, true, true));
		
		CssLayout form = new CssLayout();
		form.addStyleName("order-form responsive");
		form.setResponsive(true);
		form.setSizeFull();
		addComponent(form);
		
		//Start Header
		header = new HorizontalLayout();
		header.setWidth("100%");
		form.addComponent(header);
		
		ordreId = new Label("#182");
		ordreId.setContentMode(ContentMode.TEXT);
		ordreId.addStyleName(ValoTheme.LABEL_H4);
		ordreId.addStyleName(ValoTheme.LABEL_COLORED);
		header.addComponent(ordreId);
		
		stateLabel = new Label("NEW");
		stateLabel.setContentMode(ContentMode.TEXT);
		stateLabel.addStyleName(ValoTheme.LABEL_H4);
		stateLabel.addStyleName(ValoTheme.LABEL_COLORED);
		stateLabel.setWidth("100%");
		header.addComponent(stateLabel);
		header.setExpandRatio(stateLabel, 1);
		
		state = new OrdreStateSelect();
		header.addComponent(state);
		header.setComponentAlignment(state, Alignment.MIDDLE_RIGHT);
		//End Header
		
		due = new Label("Transfert");
		due.addStyleName("header");
		due.setContentMode(ContentMode.TEXT);
		due.setWidth("100%");
		form.addComponent(due);
		
		HorizontalLayout half = new HorizontalLayout();
		half.setWidth("100%");
		half.addStyleName("half");
		form.addComponent(half);
		
		dueDate = new DateField();
		dueDate.setLenient(true);
		dueDate.setPlaceholder("date");
		dueDate.setWidth("180px");
		half.addComponent(dueDate);
		
		dueTime = new ComboBox<LocalTime>();
		dueTime.setPlaceholder("Heure");
		dueTime.setEmptySelectionAllowed(false);
		dueTime.setTextInputAllowed(false);
		dueTime.setWidth(6, Unit.EM);
		half.addComponent(dueTime);
		half.setExpandRatio(dueTime, 1);	
		
		HorizontalLayout half2 = new HorizontalLayout();
		half2.setWidth("100%");
		half2.addStyleName("half");
		form.addComponent(half2);
		
		Label at = new Label("@ Agent Pay.", ContentMode.TEXT);
		at.addStyleName("large");
		half2.addComponent(at);
		half2.setComponentAlignment(at, Alignment.MIDDLE_CENTER);
		
		payeur = BeanLocator.find(PayerCombobox.class);
		payeur.setWidth("100%");
		payeur.setPlaceholder("Agent Expéditeur");
		half2.addComponent(payeur);
		half2.setExpandRatio(payeur, 1);
		half2.setComponentAlignment(payeur, Alignment.MIDDLE_CENTER);
		
		HorizontalLayout halfAgentExp = new HorizontalLayout();
		halfAgentExp.setWidth("100%");
		halfAgentExp.addStyleName("half");
		form.addComponent(halfAgentExp);
		
		dateEnvoi = new DateField();
		dateEnvoi.setLenient(true);
		dateEnvoi.setPlaceholder("date");
		dateEnvoi.setWidth("180px");
		halfAgentExp.addComponent(dateEnvoi);
		
		heureEnvoi = new ComboBox<LocalTime>();
		heureEnvoi.setPlaceholder("Heure");
		heureEnvoi.setEmptySelectionAllowed(false);
		heureEnvoi.setTextInputAllowed(false);
		heureEnvoi.setWidth(6, Unit.EM);
		halfAgentExp.addComponent(heureEnvoi);
		halfAgentExp.setExpandRatio(heureEnvoi, 1);	
		
		HorizontalLayout half2AgentExp = new HorizontalLayout();
		half2AgentExp.setWidth("100%");
		half2AgentExp.addStyleName("half");
		form.addComponent(half2AgentExp);
		
		Label atAgentExp = new Label("@  Agent Exp.", ContentMode.TEXT);
		atAgentExp.addStyleName("large");
		half2AgentExp.addComponent(atAgentExp);
		half2AgentExp.setComponentAlignment(atAgentExp, Alignment.MIDDLE_CENTER);
		
		PayerCombobox payeurExp = BeanLocator.find(PayerCombobox.class);
		payeurExp.setWidth("100%");
		payeurExp.setPlaceholder("Agent Payeur");
		half2AgentExp.addComponent(payeurExp);
		half2AgentExp.setExpandRatio(payeurExp, 1);
		half2AgentExp.setComponentAlignment(payeurExp, Alignment.MIDDLE_CENTER);
		
		Label beneficiaire = new Label("Béneficiaire", ContentMode.TEXT);
		beneficiaire.addStyleName("header");
		beneficiaire.setWidth("100%");
		form.addComponent(beneficiaire);
		
		nomBenef = new TextField();
		nomBenef.setPlaceholder("Nom");
		nomBenef.addStyleName("half");
		nomBenef.setWidth("100%");
		form.addComponent(nomBenef);
		
		phoneBenef = new TextField();
		phoneBenef.setPlaceholder("Téléphone");
		phoneBenef.addStyleName("half");
		phoneBenef.setWidth("100%");
		form.addComponent(phoneBenef);
		
		TextField prenomBenef = new TextField();
		prenomBenef.setPlaceholder("Nom");
		prenomBenef.addStyleName("half");
		prenomBenef.setWidth("100%");
		form.addComponent(prenomBenef);
		
		Label expediteur = new Label("Expéditeur", ContentMode.TEXT);
		expediteur.addStyleName("header");
		expediteur.setWidth("100%");
		form.addComponent(expediteur);
		
		fullName = new TextField();
		fullName.setPlaceholder("Nom");
		fullName.addStyleName("half");
		fullName.setWidth("100%");
		form.addComponent(fullName);
		
		phone = new TextField();
		phone.setPlaceholder("Téléphone");
		phone.addStyleName("half");
		phone.setWidth("100%");
		form.addComponent(phone);
		
		TextField prenom = new TextField();
		prenom.setPlaceholder("Nom");
		prenom.addStyleName("half");
		prenom.setWidth("100%");
		form.addComponent(prenom);
		
		Label details = new Label("Détails", ContentMode.TEXT);
		details.addStyleName("header");
		details.setWidth("100%");
		form.addComponent(details);
		
		CssLayout infoContainer = new CssLayout();
		infoContainer.addStyleName("product-container");
		infoContainer.setWidth("100%");
		form.addComponent(infoContainer);
		
		Label frais = new Label("Frais");
		frais.addStyleName("half");
		frais.setWidth("100%");
		form.addComponent(frais);
		
		Label Montantfrais = new Label("0.00");
		Montantfrais.addStyleName("half");
		Montantfrais.setWidth("100%");
		form.addComponent(Montantfrais);
		
		Label taxe = new Label("Taxe");
		taxe.addStyleName("half");
		taxe.setWidth("100%");
		form.addComponent(taxe);
		
		Label Montanttaxe = new Label("0.00");
		Montanttaxe.addStyleName("half");
		Montanttaxe.setWidth("100%");
		form.addComponent(Montanttaxe);
		
//		Button add = new Button("Add", VaadinIcons.PLUS);
//		add.setSizeFull();
//		form.addComponent(add);
		
		Label total = new Label("0.00");
		total.addStyleName("total huge bold");
		form.addComponent(total);
		
		CssLayout buttons = new CssLayout();
		buttons.addStyleName("buttons");
		buttons.setWidth("100%");
		form.addComponent(buttons);
		
		cancel = new Button("Cancel", VaadinIcons.CLOSE);
		cancel.addStyleName("cancel");
		buttons.addComponent(cancel);
		
		ok = new Button("Valider", VaadinIcons.ALIGN_RIGHT);
		ok.addStyleName("primary icon-align-right");
		buttons.addComponent(ok);
	}
}
