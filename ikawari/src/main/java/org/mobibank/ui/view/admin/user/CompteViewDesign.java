package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.numerate.StatutCompte;
import org.mobibank.backend.numerate.TypeCompte;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class CompteViewDesign extends AbstractCrudViewDesign<Compte> {

	/**
	 * 
	 */
	protected Grid<Compte> list;
	protected ComboBox<TypeCompte> type;
	protected TextField solde;
	protected ComboBox<StatutCompte> statut;
	protected ComboBox<Utilisateur> utilisateur;
	
	public CompteViewDesign() {
		
		list = new Grid<Compte>(Compte.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("type", "solde", "devise", "statut", "utilisateur");
		addGrid(list);
		
		type = new ComboBox<>("Type");
		type.setItems(TypeCompte.values());
		
		solde = new TextField("Solde");
		solde.setReadOnly(true);
		
		statut = new ComboBox<StatutCompte>("Statut");
		statut.setEmptySelectionAllowed(false);
		statut.setItems(StatutCompte.asList());
		
		utilisateur = new ComboBox<Utilisateur>("Utilisateur");
		
		addField(type);
		addField(solde);
		addField(statut);
		addField(utilisateur);
	}
	
	public void bindForm(BeanValidationBinder<Compte> binder){
		
		binder.forField(type)
		.asRequired("Veuillez indiquer le type de compte")
		.bind(Compte::getType, Compte::setType);
//		
		binder.forField(solde)
		.withConverter(new StringToBigDecimalConverter("Solde incorrect"))
		.bind(Compte::getSolde, Compte::setSolde);
//		
		binder.forField(statut)
		.bind(Compte::getStatut, Compte::setStatut);
//		
		binder.forField(utilisateur)
		.bind(Compte::getUtilisateur, Compte::setUtilisateur);
	}
}
