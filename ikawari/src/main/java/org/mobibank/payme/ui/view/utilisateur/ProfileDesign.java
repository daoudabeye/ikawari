package org.mobibank.payme.ui.view.utilisateur;

import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.services.UtilisateurService;

import com.vaadin.addon.onoffswitch.OnOffSwitch;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import de.steinwedel.messagebox.ButtonOption;
import de.steinwedel.messagebox.MessageBox;

public class ProfileDesign extends CssLayout {
	PasswordField ancien, nouveau, confirmer;
	TextField nom, prenom, telephone, ville, email;
	TextArea adresse;

	Utilisateur user;
	Binder<Utilisateur> binder;
	Binder<PasswordBean> passwordBinder;
	
	public ProfileDesign(UtilisateurService utilisateurService) {
		addStyleName("form-template");
		setSizeFull();

		user = utilisateurService.getCurrentUser();

		binder = new Binder<Utilisateur>();
		binder.readBean(user);

		PasswordBean passwordBean = new PasswordBean();
		passwordBinder = new Binder<ProfileDesign.PasswordBean>();
		passwordBinder.readBean(passwordBean);
		
		Label titre2FA = new Label("Sécurité");
		titre2FA.addStyleName(ValoTheme.LABEL_COLORED);
		titre2FA.addStyleName("title");
		titre2FA.setContentMode(ContentMode.HTML);
		addComponent(titre2FA);
		
		final OnOffSwitch onOffSwitch = new OnOffSwitch(user.isUsing2FA());
		onOffSwitch.setCaption((user.isUsing2FA() ? "Désactiver" : "Activer") + " la connexion en 2 étapes ");
		onOffSwitch.addValueChangeListener(event -> {
		    user.setUsing2FA(event.getValue());
		    utilisateurService.save(user);
		    onOffSwitch.setCaption((user.isUsing2FA() ? "Desactiver" : "Activer") + " la connexion en 2 etapes ");
		});
		CssLayout onOffSwitchWrapper = new CssLayout(onOffSwitch);
		onOffSwitchWrapper.setStyleName("input-wrapper");
		
		CssLayout onOffSwitchGroup = new CssLayout(onOffSwitchWrapper);
		onOffSwitchGroup.setStyleName("input-group");
		
		CssLayout onOffSwitchSection = new CssLayout(onOffSwitchGroup);
		onOffSwitchSection.setStyleName("form-section");
		addComponent(onOffSwitchSection);

		Label titre = new Label("Changer votre mot de passe");
		titre.addStyleName(ValoTheme.LABEL_COLORED);
		titre.addStyleName("title");
		titre.setContentMode(ContentMode.HTML);
		addComponent(titre);

		ancien = new PasswordField("Ancien Mot de passe");
		CssLayout ancienWrapper = new CssLayout(ancien);
		ancienWrapper.setStyleName("input-wrapper");
		passwordBinder.forField(ancien)
		.withValidator(value -> utilisateurService.isPasswordMatch(value, user.getPassword()), "Le mot de passe fourni n'est pas valide.")
		.bind(PasswordBean::getPassword, PasswordBean::setPassword);

		nouveau = new PasswordField("Nouveau Mot de passe");
		CssLayout nouveauWrapper = new CssLayout(nouveau);
		nouveauWrapper.setStyleName("input-wrapper");
		passwordBinder.forField(nouveau)
		.withValidator(new StringLengthValidator("Minimum 3 caractère", 3, null))
		.bind(PasswordBean::getNewPassword, PasswordBean::setNewPassword);

		confirmer = new PasswordField("Confirmer le nouveau mot de passe");
		CssLayout confirmerWrapper = new CssLayout(confirmer);
		confirmerWrapper.setStyleName("input-wrapper");
		passwordBinder.forField(confirmer)
		//		.withValidator(value -> passwordBean.isMatch(), "Le nouveau mot de passe doit être identique.")
		.bind(PasswordBean::getConfirmation, PasswordBean::setConfirmation);

		CssLayout group1 = new CssLayout(ancienWrapper, nouveauWrapper, confirmerWrapper);
		group1.setStyleName("input-group");

		Button changerPass = new Button("Changer mot de passe");
		changerPass.addStyleName(ValoTheme.BUTTON_PRIMARY);
		CssLayout changerPassWrapper = new CssLayout(changerPass);
		changerPassWrapper.setStyleName("input-wrapper");

		changerPass.addClickListener(e -> {
			passwordBinder.writeBeanIfValid(passwordBean);
			if(passwordBinder.isValid() && passwordBean.isMatch()) {
				utilisateurService.updatePassword(user, confirmer.getValue());
				MessageBox.createInfo()
				.withCaption("PAYME")
				.withHtmlMessage("Mot de passe actualisé avec succès.")
				.withCloseButton(ButtonOption.caption("Fermer"))
				.open();
			}else
				MessageBox.createWarning()
				.withCaption("PAYME")
				.withHtmlMessage("Merci de corriger les erreurs et verrifier que les mots de passes sont identiques.")
				.withCloseButton(ButtonOption.caption("Fermer"))
				.open();

		});

		CssLayout section = new CssLayout(group1, changerPassWrapper);
		section.setStyleName("form-section");
		addComponent(section);

		Label titre2 = new Label("Informations personnelles");
		titre2.addStyleName(ValoTheme.LABEL_COLORED);
		titre2.addStyleName("title");
		titre2.setContentMode(ContentMode.HTML);
		addComponent(titre2);

		//Infos Utilisateur
		nom = new TextField("Nom");
		CssLayout nomWrapper = new CssLayout(nom);
		nomWrapper.setStyleName("input-wrapper");

		prenom = new TextField("Prenom");
		CssLayout prenomWrapper = new CssLayout(prenom);
		prenomWrapper.setStyleName("input-wrapper");

		adresse = new TextArea("Adresse");
		CssLayout adresseWrapper = new CssLayout(adresse);
		adresseWrapper.setStyleName("input-wrapper");

		CssLayout group2 = new CssLayout(nomWrapper, prenomWrapper, adresseWrapper);
		group2.setStyleName("input-group");

		telephone = new TextField("Téléphone");
		telephone.setEnabled(false);
		CssLayout telephoneWrapper = new CssLayout(telephone);
		telephoneWrapper.setStyleName("input-wrapper");

		ville = new TextField("Ville");
		CssLayout villeWrapper = new CssLayout(ville);
		villeWrapper.setStyleName("input-wrapper");

		email = new TextField("E-mail");
		CssLayout emailWrapper = new CssLayout(email);
		emailWrapper.setStyleName("input-wrapper");

		CssLayout group3 = new CssLayout(telephoneWrapper, villeWrapper, emailWrapper);
		group3.setStyleName("input-group");

		Button update = new Button("Mettre à jour");
		update.addStyleName(ValoTheme.BUTTON_PRIMARY);
		CssLayout updateWrapper = new CssLayout(update);
		updateWrapper.setStyleName("input-wrapper");

		update.addClickListener(e -> {
			try {
				MessageBox.createInfo()
				.withCaption("PAYME")
				.withHtmlMessage("Vos informations on bien été mise à jours.")
				.withCloseButton(ButtonOption.caption("Fermer"))
				.open();
			} catch (Exception e2) {
				Notification.show("Erreur", e2.getMessage(), Type.WARNING_MESSAGE);
			}
		});

		CssLayout section2 = new CssLayout(group2, group3, updateWrapper);
		section2.setStyleName("form-section");
		addComponent(section2);

	}

	public class PasswordBean {
		private String password, newPassword, confirmation="";

		public boolean isMatch() {
			return confirmation.equals(newPassword);
		}
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

		public String getConfirmation() {
			return confirmation;
		}

		public void setConfirmation(String confirmation) {
			this.confirmation = confirmation;
		}

	}
}
