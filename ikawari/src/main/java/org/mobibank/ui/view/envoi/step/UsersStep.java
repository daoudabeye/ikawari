package org.mobibank.ui.view.envoi.step;

import org.mobibank.backend.data.entity.TransfertUser;
import org.mobibank.backend.numerate.PieceIdentite;
import org.mobibank.ui.view.envoi.EnvoiView;
import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.Stepper;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.jarektoro.responsivelayout.ResponsiveRow.MarginSize;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class UsersStep extends Step{
	Binder<TransfertUser> bindSender, bindReceiver;
	public TransfertUser sender = new TransfertUser();
	public TransfertUser receiver = new TransfertUser();
	
	public UsersStep(EnvoiView view) {
		super(false);
		
		ResponsiveLayout responsiveLayout = new ResponsiveLayout();
		responsiveLayout.addRow(benefRow());
		responsiveLayout.addRow(senderRow());
		
		TextArea motif = new TextArea("Motif d'envoi");
		responsiveLayout.addRow()
		.withAlignment(Alignment.MIDDLE_LEFT)
		.withMargin(MarginSize.SMALL)
		.addComponent(motif);

		getNextButton().setCaption("Suivant");
		addStepNextListener(e -> {
			Stepper stepper = e.getSource();
			stepper.hideError();
			if (isValid()) {
				stepper.next();
				view.setUserInfos();
			} else {
				stepper.showError(new RuntimeException("Merci de corriger les champs en rouge dans le formulaire"));
			}
		});

		setEditable(true);
		setCaption("Users");
		setDescription("Détails sur expediteur et bénéficiaire");
		setIcon(VaadinIcons.USERS);
		setContent(responsiveLayout);
	}
	
	public Boolean isValid(){
		try {
			bindReceiver.writeBean(receiver);
			bindSender.writeBean(sender);
		} catch (ValidationException e) {
			return false;
		}
		return bindReceiver.isValid() && bindSender.isValid();
	}

	private ResponsiveRow benefRow() {
		TextField nom, prenom, adresse;
		TextField telephone;
		bindReceiver = new Binder<TransfertUser>();

		nom = new TextField("Nom");
		bindReceiver.forField(nom)
		.asRequired("Merci de saisir le nom.")
		.withValidator(new StringLengthValidator("Nom doit etres d'au moins 2 caractère et au plus 60", 2, 60))
		.bind(TransfertUser::getNom, TransfertUser::setNom);
		
		prenom = new TextField("Prénom");
		bindReceiver.forField(prenom)
		.asRequired("Merci de saisir le prenom.")
		.withValidator(new StringLengthValidator("Le prenom doit etres d'au moins 2 caractère et au plus 60", 2, 60))
		.bind(TransfertUser::getPrenom, TransfertUser::setPrenom);
		
		adresse = new TextField("Adresse");
		bindReceiver.forField(adresse)
		.asRequired("Merci de saisir l'adresse.")
		.withValidator(new StringLengthValidator("L'adresse doit etres d'au moins 2 caractère et au plus 100", 2, 100))
		.bind(TransfertUser::getAdresse, TransfertUser::setAdresse);
		
		telephone = new TextField("Téléphone");
		bindReceiver.forField(telephone)
		.asRequired("Merci de saisir le numéro de téléphone.")
		.bind(TransfertUser::getTelephone, TransfertUser::setTelephone);

		ResponsiveRow row =new ResponsiveRow();
		row.withAlignment(Alignment.MIDDLE_LEFT)
		.withMargin(MarginSize.SMALL);
		row.setCaption("Bénéficiare");
		row.addComponents(nom, prenom, adresse, 
						telephone );
		row.withAlignment(Alignment.MIDDLE_LEFT);

		return row;
	}

	private ResponsiveRow senderRow() {
		TextField nom, prenom, adresse, numeroPiece;
		TextField telephone;
		ComboBox<PieceIdentite> typePiece;
		bindSender = new Binder<TransfertUser>();
		
		nom = new TextField("Nom");
		bindSender.forField(nom)
		.asRequired("Merci de saisir le nom.")
		.withValidator(new StringLengthValidator("Nom doit etres d'au moins 2 caractère et au plus 60", 2, 60))
		.bind(TransfertUser::getNom, TransfertUser::setNom);
		
		prenom = new TextField("Prénom");
		bindSender.forField(prenom)
		.asRequired("Merci de saisir le prenom.")
		.withValidator(new StringLengthValidator("Le prenom doit etres d'au moins 2 caractère et au plus 60", 2, 60))
		.bind(TransfertUser::getPrenom, TransfertUser::setPrenom);
		
		adresse = new TextField("Adresse");
		bindSender.forField(adresse)
		.asRequired("Merci de saisir l'adresse.")
		.withValidator(new StringLengthValidator("L'adresse doit etres d'au moins 2 caractère et au plus 100", 2, 100))
		.bind(TransfertUser::getAdresse, TransfertUser::setAdresse);
		
		telephone = new TextField("Téléphone");
		bindSender.forField(telephone)
		.asRequired("Merci de saisir le numéro de téléphone.")
		.bind(TransfertUser::getTelephone, TransfertUser::setTelephone);
		
		numeroPiece = new TextField("Numero ID");
		bindSender.forField(numeroPiece)
		.asRequired("Veuillez indiquer le numéro de la pièce d'identité")
		.bind(TransfertUser::getNumeroPieceIdentite, TransfertUser::setNumeroPieceIdentite);
		
		typePiece = new ComboBox<PieceIdentite>("Type Pièce");
		typePiece.setEmptySelectionAllowed(false);
		typePiece.setItems(PieceIdentite.asList());
		bindSender.forField(typePiece)
		.asRequired("Veuillez indiquer la pièce d'identité")
		.bind(TransfertUser::getPieceIdentite, TransfertUser::setPieceIdentite);
		

		ResponsiveRow row = new ResponsiveRow();
		row.withAlignment(Alignment.MIDDLE_LEFT)
		.withMargin(MarginSize.SMALL);
		row.setCaption("Expéditeur");
		row.addComponents(nom, prenom, adresse, 
				telephone, typePiece, numeroPiece );

		return row;
	}

}
