package org.mobibank.ui.view.admin.pays;

import org.mobibank.backend.data.entity.Zone;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class ZoneViewDesign extends AbstractCrudViewDesign<Zone> {

	/**
	 * 
	 */
	protected TextField designation;
	protected TextField code;
	protected Grid<Zone> list;
	
	public ZoneViewDesign() {
		
		list = new Grid<Zone>(Zone.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("code", "designation");
		addGrid(list);
		
		designation = new TextField("Désignation");
		designation.setValueChangeMode(ValueChangeMode.LAZY);
		designation.setValueChangeTimeout(500);
		
		code = new TextField("code");
		code.setValueChangeMode(ValueChangeMode.LAZY);
		code.setValueChangeTimeout(500);
		
		addField(designation);
		addField(code);
	}
	
	public void bindForm(BeanValidationBinder<Zone> binder){
		binder.forField(designation)
		.bind(Zone::getDesignation, Zone::setDesignation);
		
		binder.forField(code)
		.bind(Zone::getCode, Zone::setCode);
	}
}
