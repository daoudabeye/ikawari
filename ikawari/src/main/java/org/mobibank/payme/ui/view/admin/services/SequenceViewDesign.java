package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.entity.Sequences;
import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.backend.numerate.Context;
import org.mobibank.payme.backend.numerate.SchemaCompte;
import org.mobibank.payme.backend.numerate.TypeTransaction;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class SequenceViewDesign  extends AbstractCrudViewDesign<Sequences> {

	/**
	 * 
	 */
	Grid<Sequences> list;
	TextField sequence;
	ComboBox<TypeTransaction> sens;
	ComboBox<SchemaCompte> compte;
	TwinColSelect<Context> context;
	ComboBox<Services> service;
	TextArea description;

	public SequenceViewDesign() {
		list = new Grid<Sequences>(Sequences.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("sequence", "sens", "compte", "context", "service", "description");
		addGrid(list);
		
		sequence = new TextField("Sequence");
		sequence.setValueChangeMode(ValueChangeMode.LAZY);
		sequence.setValueChangeTimeout(500);
		
		sens = new ComboBox<TypeTransaction>("Sens");
		sens.setItems(TypeTransaction.asList());
		
		compte = new ComboBox<SchemaCompte>("Comptes");
		//compte.setItems(SchemaCompte.asList());
		
		context = new TwinColSelect<Context>("Context");
		//context.setItems(Context.asList());
		
		service = new ComboBox<Services>("Service");
		service.setEmptySelectionAllowed(false);
		
		description = new TextArea("Description");
		description.setValueChangeMode(ValueChangeMode.LAZY);
		description.setValueChangeTimeout(500);
		
		addField(service);
		addField(sequence);
		addField(sens);
		addField(compte);
		addField(context);
		addField(description);
	}
	
	public void bindForm(BeanValidationBinder<Sequences> binder){
		binder.forField(service)
		.bind(Sequences::getService, Sequences::setService);
		binder.forField(sequence)
		.withConverter(new StringToIntegerConverter("n'est une sequence valable"))
		.bind(Sequences::getSequence, Sequences::setSequence);
		binder.forField(sens)
		.bind(Sequences::getSens, Sequences::setSens);
		binder.forField(compte)
		.bind(Sequences::getCompte, Sequences::setCompte);
		binder.forField(context)
		.bind(Sequences::getContext, Sequences::setContext);
		binder.forField(description)
		.bind(Sequences::getDescription, Sequences::setDescription);
	}
}
