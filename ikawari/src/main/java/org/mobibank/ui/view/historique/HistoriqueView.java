package org.mobibank.ui.view.historique;

import javax.annotation.PostConstruct;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.numerate.Plateforme;
import org.mobibank.backend.numerate.StatutCode;
import org.mobibank.backend.services.CompteService;
import org.mobibank.backend.services.OperationService;
import org.mobibank.backend.services.UtilisateurService;
import org.mobibank.ui.components.TransactionGrid;
import org.springframework.security.access.annotation.Secured;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.jarektoro.responsivelayout.ResponsiveRow.MarginSize;
import com.jarektoro.responsivelayout.ResponsiveRow.SpacingSize;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

@SpringView
@Secured({Role.BANQUE, Role.MASTER, Role.DISTRIBUTEUR, Role.OPERATEUR, Role.CLIENT})
public class HistoriqueView extends ResponsiveLayout implements View {

	ResponsiveLayout responsiveLayout;
	ResponsiveRow reponsiveRow;
	ResponsiveColumn filterCol;
	ResponsiveColumn gridCol;

	ComboBox<Compte> comptes;
	ComboBox<Plateforme> plateforme;
	ComboBox<StatutCode> status;
	DateField from, to;
	TextField max, min;

	TransactionGrid transactionGrid;

	@PostConstruct
	public void init() {
		setSizeFull();
		
		comptes = new ComboBox<>("Comptes");
		comptes.setEmptySelectionAllowed(false);

		status = new ComboBox<StatutCode>();
		status.setPlaceholder("Status");
		status.setItems(StatutCode.values());

		from = new DateField();
		from.setPlaceholder("From");

		to = new DateField();
		to.setPlaceholder("To");

		max = new TextField();
		max.setPlaceholder("Max");

		min = new TextField();
		min.setPlaceholder("Min");

		transactionGrid = new TransactionGrid();
//		transactionGrid.setHeight("100%");

		reponsiveRow = addRow()
				.withHorizontalSpacing(false)
				.withMargin(MarginSize.NORMAL)
				.withAlignment(Alignment.TOP_LEFT);
		reponsiveRow.setSizeFull();

		reponsiveRow.addColumn()
		.withDisplayRules(12, 4, 4, 4)
		.withComponent(CreateFilterLayout());

		ResponsiveColumn col = reponsiveRow.addColumn()
		.withDisplayRules(12, 8, 8, 8)
		.withComponent(transactionGrid);
		col.setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		View.super.enter(event);
		
		Utilisateur current = BeanLocator.find(UtilisateurService.class).getCurrentUser();
		comptes.setItems(BeanLocator.find(CompteService.class).find(current));
		comptes.addSelectionListener(e -> {
			transactionGrid.setItems(BeanLocator.find(OperationService.class).findHistorique(e.getValue()));
		});
		
	}

	public ResponsiveRow CreateFilterLayout() {

		ResponsiveRow row = new ResponsiveRow()
				.withMargin(MarginSize.SMALL)
				.withHorizontalSpacing(SpacingSize.SMALL, true)
				.withVerticalSpacing(SpacingSize.SMALL, true);		
		row.setCaption("Filtre");
		
		row.addColumn()
		.withDisplayRules(12, 12, 12, 12)
		.withComponent(comptes);

		row.addColumn()
		.withDisplayRules(12, 12, 12, 6)
		.withComponent(from);

		row.addColumn()
		.withDisplayRules(12, 12, 12, 6)
		.withComponent(to);

		row.addColumn()
		.withDisplayRules(12, 12, 12, 6)
		.withComponent(max);

		row.addColumn()
		.withDisplayRules(12, 12, 12, 6)
		.withComponent(min);

		row.addColumn()
		.withDisplayRules(12, 12, 12, 12)
		.withComponent(status);

		return row;
	}
}
