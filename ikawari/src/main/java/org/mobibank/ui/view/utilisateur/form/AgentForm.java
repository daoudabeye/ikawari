package org.mobibank.ui.view.utilisateur.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.numerate.Genre;
import org.mobibank.backend.services.AgentService;
import org.mobibank.backend.services.StructureService;
import org.mobibank.backend.services.UtilisateurService;
import org.mobibank.security.SecurityUtils;
import org.mobibank.ui.RoleSelect;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import de.steinwedel.messagebox.MessageBox;

/**
 * 
 * @author BEYE
 *
 */
public class AgentForm extends CssLayout {
	Label titre;
	RoleSelect profile;

	TextField nomField, prenomField, indicatif, email, ville, adresse, identifiant; 
	ComboBox<Genre> genreField;
	ComboBox<Structure> agences;
	DateField dateNaissanceField;
	TextField telephone;

	Button firstAddButton;
	String styleName;
	Map<Button, Component> addButtonGroup = new HashMap<>();
	Map<TextField, TextField> bankMap;

	Button submit;

	CssLayout section;

	UtilisateurService utilisateurService;
	StructureService structureService;
	Utilisateur current;
	Pays currentContry;

	public AgentForm(UtilisateurService utilisateurService, AgentService agentService, StructureService structureService) {
		setStyleName("form-template");
		setSizeFull();
		setResponsive(true);

		this.utilisateurService = utilisateurService;
		this.structureService = structureService;
		current = utilisateurService.getCurrentUser();

		titre = new Label("Création d'un compte agent");
		titre.setStyleName("title");
		titre.setWidth("100%");
		titre.setHeightUndefined();
		titre.setContentMode(ContentMode.TEXT);		

		addComponent(titre);

		addComponent(personalInfoSection("Info Personnel"));
		addComponent(contactInfoSection("Info Contacte"));
		addComponent(submitSection("Creer le compte"));

		//Data Binding
		//Binding data to form
		Binder<Agent> binder = new Binder<>();
		binder.forField(identifiant)
		.withValidator(new StringLengthValidator("Minimun 4 caractètes Max 40", 4, 20))
//		.withValidator(value -> !utilisateurService.exist(value), "Ce identifiant existe déja." )
		.bind(Agent::getUsername, Agent::setUsername);

		binder.forField(profile).asRequired("Merci de selectionner le profile adéquat.")
		.bind(Agent::getRole, Agent::setRole);

		binder.forField(nomField).asRequired("Merci d'indiquer le nom de la structure")
		.withValidator(new StringLengthValidator("Min 2 caractères", 2, 50))
		.bind(Agent::getNom, Agent::setNom);

		binder.forField(prenomField).asRequired("Merci d'indiquer le prenom de la structure")
		.withValidator(new StringLengthValidator("Min 2 caractères", 2, 50))
		.bind(Agent::getPrenom, Agent::setPrenom);

		binder.forField(telephone)
		//				.withValidator(value -> !value.startsWith(currentContry.getIndicatif()),
		//						"Vous devez saisir le numéro sans indicatif.")
//		.withValidator(value -> !utilisateurService.exist(value), "Le numéro existe déja" )
		.bind(Agent::getTelephone, Agent::setTelephone);

		binder.forField(ville).asRequired("Merci d'indiquer la ville")
		.withValidator(new StringLengthValidator("Min 2 caractères", 2, 50))
		.bind(Agent::getVille, Agent::setVille);

		binder.forField(genreField).asRequired("Merci d'indiquer le genre.")
		.bind(Agent::getGenre, Agent::setGenre);

		binder.forField(adresse)
		.bind(Agent::getAdresse, Agent::setAdresse);

		if(agences != null)
			binder.forField(agences)
			.bind(Agent::getStructure, Agent::setStructure);

		binder.forField(dateNaissanceField)
		.asRequired("Veuillez indiquez la date de naissance")
		.bind(Agent::getDateNaissance, Agent::setDateNaissance);

		submit.addClickListener(l ->{
			Agent bean = new Agent();
			try {
				binder.writeBean(bean);
				ConfirmDialog.show(getUI(), "Confirmation", "Etes-vous sûre de vouloir créer le compte ?",
						"Oui", "Valider","Annuler", new ConfirmDialog.Listener() {
					private static final long serialVersionUID = 1L;
					Map<String, String> iban = new HashMap<>();
					@Override
					public void onClose(ConfirmDialog dialog) {
						if(dialog.isConfirmed()){
							try {
								bankMap.forEach((banName, accountNumber) -> iban.put(banName.getValue(), accountNumber.getValue()));
								if(iban.size() > 0)
									bean.setIban(iban);
								agentService.save(bean);
								MessageBox
								.createInfo()
								.withCaption("Info")
								.withMessage("Le compte Agent à été créer avec succès.")
								.withOkButton()
								.open();
							} catch (Exception e) {
								Notification.show("Erreur " +
										e.getMessage(), Type.ERROR_MESSAGE);
								e.printStackTrace();
							}
						}
					}
				});

			} catch (ValidationException e) {
				Notification.show("Erreur lors de la création, " +
						"Merci de corriger les erreurs dans le formulaire.", Type.WARNING_MESSAGE);
			} catch (Exception e) {
				Notification.show("Erreur " +
						e.getMessage(), Type.ERROR_MESSAGE);
			}
		});


	}

	private Component personalInfoSection(String caption) {
		CssLayout personalInfo = new CssLayout();
		personalInfo.setStyleName("form-section");
		personalInfo.setCaption(caption);
		personalInfo.setSizeUndefined();

		if(SecurityUtils.isCurrentUserInRole(Role.MASTER)) {
			List<Component> agenceGroup = new ArrayList<>();
			agences = new ComboBox<Structure>("Point Services");
			agenceGroup.add(agences);
			agences.setItems(BeanLocator.find(StructureService.class).find(current.getId(), Role.POINTSERVICE));
			personalInfo.addComponent(addGroupe(agenceGroup));
		}

		profile = new RoleSelect();

		if(SecurityUtils.isCurrentUserInRole(Role.PROVIDER))
			profile.setItems(Role.MASTER);

		if(SecurityUtils.isCurrentUserInRole(Role.MASTER))
			profile.setItems(Role.DISTRIBUTEUR, Role.OPERATEUR);

		identifiant = new TextField("Identifiant");
		List<Component> profileGroup = new ArrayList<>();
		profileGroup.add(profile);
		profileGroup.add(identifiant);
		personalInfo.addComponent(addGroupe(profileGroup));

		List<Component> group1 = new ArrayList<>();
		nomField = new TextField("Nom");
		prenomField = new TextField("Prénom");
		group1.add(nomField);
		group1.add(prenomField);

		dateNaissanceField = new DateField("Date de Naissance");
		dateNaissanceField.setDateFormat("dd-MM-yyyy");
		genreField = new ComboBox<Genre>("Genre");
		genreField.setItems(Genre.asList());

		List<Component> group2 = new ArrayList<>();
		group2.add(dateNaissanceField);
		group2.add(genreField);

		personalInfo.addComponent(addGroupe(group1));
		personalInfo.addComponent(addGroupe(group2));

		return personalInfo;
	}

	private Component contactInfoSection(String caption) {
		section = new CssLayout();
		section.setStyleName("form-section");
		section.setCaption(caption);
		section.setSizeUndefined();

		indicatif = new TextField();
		indicatif.setTabIndex(0);
		indicatif.setWidth(60, Unit.PIXELS);
		indicatif.setHeightUndefined();
		indicatif.setValueChangeMode(ValueChangeMode.LAZY);
		indicatif.setValueChangeTimeout(400);
		indicatif.setEnabled(false);

		telephone = new TextField();
		telephone.setWidth(100, Unit.PIXELS);

		HorizontalLayout telLayout = new HorizontalLayout(indicatif, telephone);
		telLayout.addStyleName("caption-left");
		telLayout.setCaption("Téléphone");

		email = new TextField("E-mail");

		List<Component> group = new ArrayList<>();
		group.add(telLayout);
		group.add(email);
		section.addComponent(addGroupe(group));

		ville = new TextField("Ville");
		adresse = new TextField("Adresse");

		List<Component> adresseGroup = new ArrayList<>();
		adresseGroup.add(ville);
		adresseGroup.add(adresse);
		section.addComponent(addGroupe(adresseGroup));

		bankMap = new HashMap<>();
		TextField bank = new TextField();
		bank = new TextField("BANK");
		bank.setPlaceholder("Nom de la banque");
		TextField iban = new TextField("NUMERO IBAN");
		iban.setPlaceholder("Numero de compte");
		bankMap.put(bank, iban);

		firstAddButton = new Button();
		firstAddButton.setStyleName("borderless");
		firstAddButton.setIcon(VaadinIcons.PLUS);

		List<Component> group3 = new ArrayList<>();
		group3.add(bank);
		group3.add(iban);
		group3.add(firstAddButton);

		Component g3 = addGroupe(group3);
		addButtonGroup.put(firstAddButton, null);
		section.addComponent(g3);
		firstAddButton.addClickListener(addListener);
		styleName = firstAddButton.getStyleName();

		return section;
	}

	private Component submitSection(String string) {

		CssLayout submitSection = new CssLayout();
		submitSection.setStyleName("form-section");
		submitSection.setCaption(string);
		submitSection.setSizeUndefined();

		submit = new Button("Valider", VaadinIcons.CHECK_SQUARE);
		submit.setStyleName(ValoTheme.BUTTON_FRIENDLY);

		Button annuler = new Button("Annuler", VaadinIcons.BACKSPACE);

		HorizontalLayout h = new HorizontalLayout(submit, annuler);
		submitSection.addComponent(h);
		return submitSection;
	}

	ClickListener addListener = new ClickListener() {
		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton().getIcon().equals(VaadinIcons.PLUS)){
				TextField b = new TextField();
				b = new TextField("BANK");
				b.setPlaceholder("Nom de la banque");
				TextField i = new TextField("NUMERO IBAN");
				i.setPlaceholder("Numero de compte");
				bankMap.put(b, i);

				Button a = new Button();
				a.setStyleName("borderless");
				a.setIcon(VaadinIcons.PLUS);
				a.addClickListener(addListener);

				List<Component> g = new ArrayList<>();
				g.add(b);
				g.add(i);
				g.add(a);

				addButtonGroup.forEach((bu, co) -> {
					bu.setIcon(VaadinIcons.CLOSE);
					bu.setStyleName(ValoTheme.BUTTON_DANGER);
				});

				Component buttonGroup = addGroupe(g);
				addButtonGroup.put(a, buttonGroup);
				section.addComponent(buttonGroup);
			}else{
				Component com = addButtonGroup.get(event.getButton());
				if(com != null){
					section.removeComponent(com);
					addButtonGroup.remove(event.getButton());
				}else{
					addButtonGroup.forEach((bu, co) -> {
						if(co != null)
							section.removeComponent(co);
					});

					addButtonGroup = new HashMap<>();
					addButtonGroup.put(firstAddButton, null);
					firstAddButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
					firstAddButton.setStyleName(styleName);
					event.getButton().setIcon(VaadinIcons.PLUS);
				}
			}
		}
	};

	private CssLayout addGroupe(List<Component> groupComponent){
		CssLayout groupe = new CssLayout();
		groupe.setSizeUndefined();
		groupe.setStyleName("input-group");
		groupComponent.forEach( component -> groupe.addComponent(wrapper(component)));
		return groupe;
	}

	private CssLayout wrapper(Component component){

		CssLayout wrapper = new CssLayout(component);
		wrapper.addStyleName("input-wrapper");
		wrapper.addStyleName("align-bottom");
		wrapper.setSizeUndefined();
		return wrapper;

	}

}
