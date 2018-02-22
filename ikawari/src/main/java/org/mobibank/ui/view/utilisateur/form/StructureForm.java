package org.mobibank.ui.view.utilisateur.form;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Horaires;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.numerate.Jours;
import org.mobibank.backend.services.StructureService;
import org.mobibank.security.SecurityUtils;
import org.mobibank.ui.RoleSelect;
import org.springframework.security.access.annotation.Secured;
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
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import de.steinwedel.messagebox.MessageBox;

@Secured({Role.PROVIDER, Role.MASTER})
public class StructureForm extends CssLayout {

	Label titre;

	TextField nomField, indicatif, email, identifiant; 
	TextField telephone;
	RoleSelect profile;

	CssLayout section;

	Button submit, horaires;

	VerticalLayout horrairesLayout;
	List<HorraireLine> horraireLines = new ArrayList<>();
	VerticalLayout horraireWindowsContent;

	Structure bean = new Structure();

	public StructureForm(StructureService structureService) {
		setStyleName("form-template");
		setSizeFull();
		setResponsive(true);

		titre = new Label("Creer une strucrure");
		titre.setStyleName("title");
		titre.setWidth("100%");
		titre.setHeightUndefined();
		titre.setContentMode(ContentMode.TEXT);		

		addComponent(titre);

		addComponent(personalInfoSection("Info"));
		addComponent(contactInfoSection("Info Contact"));
		addComponent(HorairesSection("Horaires"));
		addComponent(submitSection("Créer le compte"));

		//Binding data to form
		Binder<Structure> binder = new Binder<>();
		binder.forField(identifiant).asRequired("Vous devez fournir un identifiant")
		.withValidator(new StringLengthValidator("Minimun 4 caractètes Max 40", 4, 20))
//		.withValidator(value -> ! structureService.exist(value), "L'identifiant existe déja." )
		.bind(Structure::getUsername, Structure::setUsername);

		binder.forField(profile).asRequired("Merci de selectionner le profile adéquat.")
		.bind(Structure::getRole, Structure::setRole);

		binder.forField(nomField).asRequired("Merci d'indiquer le nom de la structure")
		.withValidator(new StringLengthValidator("Min 2 caractères", 2, 50))
		.bind(Structure::getDesignation, Structure::setDesignation);

		binder.forField(email)
		.bind(Structure::getEmail, Structure::setEmail);

		binder.forField(telephone)
		.bind(Structure::getTelephone, Structure::setTelephone);

		submit.setDisableOnClick(true);
		submit.addClickListener(l ->{
			try {
				binder.writeBean(bean);
				ConfirmDialog.show(getUI(), "Confirmation", "Etes-vous sûre de vouloir créer le compte ?",
						"Oui", "Anuller","Fermer", new ConfirmDialog.Listener() {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClose(ConfirmDialog dialog) {
						if(dialog.isConfirmed()){
							try {

								horraireLines.forEach(l -> {
									Horaires h = new Horaires(l.jours(), l.debut(), l.fin());
									bean.getHoraires().add(h);
								});

								structureService.save(bean);

								MessageBox
								.createInfo()
								.withCaption("Info")
								.withMessage("Le compte à été créer avec succès.")
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
			}
		});
	}

	private Component personalInfoSection(String caption) {
		CssLayout personalInfo = new CssLayout();
		personalInfo.setStyleName("form-section");
		personalInfo.setCaption(caption);
		personalInfo.setSizeUndefined();

		profile = new RoleSelect(Role.getAllStructures());
		if(SecurityUtils.isCurrentUserInRole(Role.PROVIDER))
			profile.setItems(Role.BANQUE);
		if(SecurityUtils.isCurrentUserInRole(Role.MASTER))
			profile.setItems(Role.POINTSERVICE);
		identifiant = new TextField("Identifiant");
		List<Component> identifiandGroup = new ArrayList<>();
		identifiandGroup.add(profile);
		identifiandGroup.add(identifiant);
		personalInfo.addComponent(addGroupe(identifiandGroup));

		List<Component> group1 = new ArrayList<>();
		nomField = new TextField("Nom");
		group1.add(nomField);

		personalInfo.addComponent(addGroupe(group1));

		return personalInfo;
	}

	private Component contactInfoSection(String caption) {
		section = new CssLayout();
		section.setStyleName("form-section");
		section.setCaption(caption);
		section.setSizeUndefined();

		indicatif = new TextField();
		indicatif.setValue("(+223)");
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

		return section;
	}

	private Component HorairesSection(String string) {

		CssLayout horairesSection = new CssLayout();
		horairesSection.setStyleName("form-section");
		horairesSection.setCaption(string);
		horairesSection.setSizeUndefined();

		horaires = new Button("Horaires", VaadinIcons.TIMER);
		horaires.setStyleName(ValoTheme.BUTTON_QUIET);
		horaires.addClickListener(e -> {
			Window horairesWindows = new Window("Horaires");
			horairesWindows.setModal(true);
			horairesWindows.center();
			horairesWindows.setPositionY(0);

			if(horrairesLayout == null )
				horairesWindows.setContent(addHorairesLayout());
			else
				horairesWindows.setContent(horraireWindowsContent);

			getUI().addWindow(horairesWindows);

		});

		HorizontalLayout h = new HorizontalLayout(horaires);
		horairesSection.addComponent(h);
		return horairesSection;
	}

	private Component addHorairesLayout() {

		horrairesLayout = new VerticalLayout();
		horrairesLayout.setSizeUndefined();

		addHorraire(horrairesLayout);

		Button save = new Button("Enregistrer", VaadinIcons.HARDDRIVE);
		save.addClickListener(event ->{
			//bean.setHoraires(new HashSet<Horaires>());			
		});
		Button close = new Button("Fermer", VaadinIcons.HARDDRIVE);
		HorizontalLayout buttonLayout = new HorizontalLayout(save, close);
		buttonLayout.setSizeUndefined();
		buttonLayout.setMargin(true);
		buttonLayout.setSpacing(true);

		horraireWindowsContent = new VerticalLayout(horrairesLayout, buttonLayout);
		horraireWindowsContent.setSizeUndefined();
		horraireWindowsContent.setExpandRatio(horrairesLayout, 1);

		return horraireWindowsContent;
	}

	public void addHorraire(VerticalLayout layout){

		ComboBox<Jours> jours = new ComboBox<Jours>();
		jours.setItems(Jours.asList());
		jours.setWidth(150, Unit.PIXELS);
		jours.setEmptySelectionAllowed(false);
		DateTimeField debut = new DateTimeField();
		debut.addStyleName("time-only");
		debut.setDateFormat("HH:mm");
		debut.setWidth(100, Unit.PIXELS);
		DateTimeField fin = new DateTimeField();
		fin.addStyleName("time-only");
		fin.setDateFormat("HH:mm");
		fin.setWidth(100, Unit.PIXELS);
		Button add = new Button(VaadinIcons.PLUS);
		add.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		add.addClickListener(addHorraireLitener);

		if(horraireLines.size() == 0){
			jours.setCaption("Jours");
			debut.setCaption("Ouverture");
			fin.setCaption("Fermeture");
		}

		HorizontalLayout hLayout = new HorizontalLayout(jours, debut, fin, add);
		hLayout.setSizeUndefined();
		hLayout.setMargin(true);
		hLayout.setSpacing(true);

		horraireLines.add(new HorraireLine(hLayout, jours, debut, fin, add));

		layout.addComponent(hLayout);
	}

	Button.ClickListener addHorraireLitener = new ClickListener() {
		private static final long serialVersionUID = 1L;

		HorraireLine line;

		@Override
		public void buttonClick(ClickEvent event) {
			if(event.getButton().getIcon().equals(VaadinIcons.PLUS)){
				if(horraireLines.size() < 7){
					addHorraire(horrairesLayout);
					event.getButton().setIcon(VaadinIcons.CLOSE);
				}
			}else{

				horraireLines.forEach(h ->{
					if(h.getAdd().equals(event.getButton())){
						horrairesLayout.removeComponent(h.getLine());
						line = h;
					}
				});

				horraireLines.remove(line);
			}

		}
	};

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
