package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.Change;
import org.mobibank.payme.backend.numerate.Devise;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class DeviseViewDesign extends AbstractCrudViewDesign<Change> {

	/**
	 * 
	 */
	protected Grid<Change> list;
	protected ComboBox<Devise> src;
	protected ComboBox<Devise> dst;
	protected TextField value;
	
	public DeviseViewDesign() {
		
		list = new Grid<Change>(Change.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("src", "dst", "value" , "updateDate");
		addGrid(list);
		
		src = new ComboBox<Devise>("From");
		src.setItems(Devise.values());
		
		dst = new ComboBox<Devise>("To");
		dst.setItems(Devise.values());
		
		value = new TextField("Valeur");
		
		addField(src);
		addField(dst);
		addField(value);
	}
	
	public void bindForm(BeanValidationBinder<Change> binder){
		binder.forField(src)
		.bind(Change::getSrc, Change::setSrc);
		binder.forField(dst)
		.bind(Change::getDst, Change::setDst);
		binder.forField(value)
		.bind(Change::getStringValue, Change::setValue);;
	}
}
