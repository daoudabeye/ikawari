package org.mobibank.payme.ui.components;

import org.mobibank.payme.backend.data.entity.Change;
import org.mobibank.payme.backend.services.ChangeService;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;

@SpringComponent
@PrototypeScope
public class DeviseGrid extends Grid<Change>{
	public DeviseGrid(ChangeService changeService) {
		// TODO Auto-generated constructor stub
		super(Change.class);
		setSizeFull();
		removeHeaderRow(0);
		
		setColumns("updateDate", "src", "dst", "value");

		setItems(changeService.getRepository().findAll());
	}

}
