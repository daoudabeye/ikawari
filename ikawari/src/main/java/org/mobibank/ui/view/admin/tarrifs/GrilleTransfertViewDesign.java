package org.mobibank.ui.view.admin.tarrifs;

import org.mobibank.backend.data.entity.Corridore;
import org.mobibank.backend.data.entity.GrilleTransfert;
import org.mobibank.backend.data.entity.Services;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class GrilleTransfertViewDesign extends AbstractCrudViewDesign<GrilleTransfert> {

	/**
	 * 
	 */
	protected Grid<GrilleTransfert> list;
	protected TextField montantMin;
	protected TextField montantMax;
	protected TextField frais;
	protected ComboBox<Services> services;
	protected ComboBox<Corridore> corridore;
	
	public GrilleTransfertViewDesign() {
		
		list = new Grid<GrilleTransfert>(GrilleTransfert.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("montantMin", "montantMax", "frais", "services", "corridore");
		addGrid(list);
		
		montantMin = new TextField("Min");
		montantMax = new TextField("Max");
		frais = new TextField("Frais");
		services = new ComboBox<Services>("Service");
		corridore = new ComboBox<Corridore>("Corridores");
		
		addField(services);
		addField(corridore);
		addField(montantMin);
		addField(montantMax);
		addField(frais);
	}
	
	public void bindForm(BeanValidationBinder<GrilleTransfert> binder){
		binder.forField(montantMin)
		.withConverter(new StringToDoubleConverter("Valeur Incorrect"))
		.bind(GrilleTransfert::getMontantMin, GrilleTransfert::setMontantMin);
		binder.forField(montantMax)
		.withConverter(new StringToDoubleConverter("Valeur Incorrect"))
		.bind(GrilleTransfert::getMontantMax, GrilleTransfert::setMontantMax);
		binder.forField(frais)
		.withConverter(new StringToDoubleConverter("Valeur Incorrect"))
		.bind(GrilleTransfert::getFrais, GrilleTransfert::setFrais);
		binder.forField(services)
		.bind(GrilleTransfert::getServices, GrilleTransfert::setServices);
		binder.forField(corridore)
		.bind(GrilleTransfert::getCorridore, GrilleTransfert::setCorridore);
	}
}
