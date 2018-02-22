package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.GrilleWallet;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class GrilleWalletViewDesign extends AbstractCrudViewDesign<GrilleWallet> {

	/**
	 * 
	 */
	protected Grid<GrilleWallet> list;
	protected TextField montantMin;
	protected TextField montantMax;
	protected TextField frais;
	protected ComboBox<Services> services;
	protected ComboBox<Pays> pays;
	
	public GrilleWalletViewDesign() {
		
		list = new Grid<GrilleWallet>(GrilleWallet.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("montantMin", "montantMax", "frais", "services", "pays");
		addGrid(list);
		
		montantMin = new TextField("Min");
		montantMax = new TextField("Max");
		frais = new TextField("Frais");
		services = new ComboBox<Services>("Service");
		pays = new ComboBox<Pays>("PAYS");
		
		addField(pays);
		addField(services);
		addField(montantMin);
		addField(montantMax);
		addField(frais);
	}
	
	public void bindForm(BeanValidationBinder<GrilleWallet> binder){
		binder.forField(montantMin)
		.withConverter(new StringToDoubleConverter("Valeur Incorrect"))
		.bind(GrilleWallet::getMontantMin, GrilleWallet::setMontantMin);
		binder.forField(montantMax)
		.withConverter(new StringToDoubleConverter("Valeur Incorrect"))
		.bind(GrilleWallet::getMontantMax, GrilleWallet::setMontantMax);
		binder.forField(frais)
		.withConverter(new StringToDoubleConverter("Valeur Incorrect"))
		.bind(GrilleWallet::getFrais, GrilleWallet::setFrais);
		binder.forField(services)
		.bind(GrilleWallet::getServices, GrilleWallet::setServices);
		binder.forField(pays)
		.bind(GrilleWallet::getPays, GrilleWallet::setPays);
	}
}
