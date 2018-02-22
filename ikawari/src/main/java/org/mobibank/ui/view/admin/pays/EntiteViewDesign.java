package org.mobibank.ui.view.admin.pays;

import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class EntiteViewDesign extends AbstractCrudViewDesign<Entite> {

	/**
	 * 
	 */
	protected Grid<Entite> list;
	protected TextField designation;
	protected TextField type;
	protected TextField prefixe;
	protected TextField niveau;
	protected ComboBox<Entite> parent;
	protected ComboBox<Pays> pays;

	public EntiteViewDesign() {

		list = new Grid<Entite>(Entite.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("designation", "type", "prefixe", "niveau", "parent", "pays");
		addGrid(list);
		
		designation = new TextField("Désignation");
		designation.setValueChangeMode(ValueChangeMode.LAZY);
		designation.setValueChangeTimeout(500);
		
		type = new TextField("Type");
		type.setValueChangeMode(ValueChangeMode.LAZY);
		type.setValueChangeTimeout(500);
		
		prefixe = new TextField("Préfixe");
		prefixe.setValueChangeMode(ValueChangeMode.LAZY);
		prefixe.setValueChangeTimeout(500);
		
		niveau = new TextField("Niveau");
		niveau.setValueChangeMode(ValueChangeMode.LAZY);
		niveau.setValueChangeTimeout(500);
		
		parent = new ComboBox<Entite>("Parent");
		pays = new ComboBox<Pays>("Pays");
		
		addField(designation);
		addField(type);
		addField(prefixe);
		addField(niveau);
		addField(parent);
		addField(pays);
	}
	
	public void bindForm(BeanValidationBinder<Entite> binder){
		binder.forField(designation)
		.bind(Entite::getDesignation, Entite::setDesignation);
		
		binder.forField(type)
		.bind(Entite::getType, Entite::setType);
		binder.forField(prefixe)
		.bind(Entite::getPrefixe, Entite::setPrefixe);
		binder.forField(niveau)
		.withConverter(new StringToIntegerConverter("Doit etre un nombre entier"))
		.bind(Entite::getNiveau, Entite::setNiveau);
		binder.forField(parent)
		.bind(Entite::getParent, Entite::setParent);
		binder.forField(pays)
		.bind(Entite::getPays, Entite::setPays);
	}
}
