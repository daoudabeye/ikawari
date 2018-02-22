package org.mobibank.payme.ui.view.home;

import java.time.format.DateTimeFormatter;

import org.mobibank.payme.backend.data.entity.Validation;
import org.mobibank.payme.backend.services.ValidationService;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

/**
 * 
 * @author BEYE
 *
 *Class fille {@link com.vaadin.ui.Grid}
 */
public class ValidationGrid extends Grid<Validation> {

	Validation v;
	ValidationService validationService;

	/**
	 * Constructeur de la class ValidationGrid
	 * @param validationService le service Backend
	 */
	public ValidationGrid(ValidationService validationService) {
		this.validationService = validationService;
		setSizeFull();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

		addColumn(Validation::getType).setCaption("TYPE");
		addColumn(Validation::getValidationLevel).setCaption("NIVEAU");
		addColumn(Validation::getDateCreation).setRenderer(new LocalDateTimeRenderer(dtf))
		.setCaption("DATE");
		addColumn(Validation::getValidationStatut).setCaption("STATUT");
		addComponentColumn(actions -> new Actions())
		.setCaption("ACTIONS");

		setItems();
	}

	private void setItems(){
		
	}

	/**
	 * Methode appelé pour valider l'instance selectionner
	 */
	private void valider(){
		ConfirmDialog.show(getUI(), "Merci de confirmer:", "Etes vous sure de vouloir valider ?",
				"Oui, valider", "Nom", new ConfirmDialog.Listener() {
			private static final long serialVersionUID = 1L;

			public void onClose(ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					Validation v = asSingleSelect().getValue();
					System.err.println(v);
//					String message = validationService.validate(v, ValidationStatut.VALIDER);
					Notification.show("Activation", "", Type.TRAY_NOTIFICATION);
				}
			}
		});
	}

	/**
	 * Methode pour rejeter l'instance selectionner
	 */
	private void rejeter(){
		Validation v = asSingleSelect().getValue();
		if(v != null){
		}
	}

	public class Actions extends HorizontalLayout{
		private static final long serialVersionUID = 1L;

		Button search, valider, annuler;
		public Actions() {
			search = new Button(VaadinIcons.SEARCH);
			search.addClickListener(e -> valider());

			valider = new Button(VaadinIcons.CHECK);
			valider.addClickListener(e -> valider());

			annuler = new Button(VaadinIcons.CLOSE);
			annuler.addClickListener(e -> rejeter());

			this.addComponent(search);
			this.addComponent(valider);
			this.addComponent(annuler);
			this.setSpacing(false);
			this.setMargin(false);
		}
	}

}
