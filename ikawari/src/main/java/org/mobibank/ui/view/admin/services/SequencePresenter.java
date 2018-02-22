package org.mobibank.ui.view.admin.services;

import org.mobibank.backend.data.entity.Sequences;
import org.mobibank.backend.services.SequenceService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class SequencePresenter extends AbstractCrudPresenter<Sequences, SequenceService, SequenceView> {
	
	/**
	 * 
	 */

	@Autowired
	public SequencePresenter(SequenceDataProvider sequenceDataProvider, NavigationManager navigationManager,
			SequenceService service) {
		super(navigationManager, service, sequenceDataProvider);
	}
	
	@Override
	protected void editItem(Sequences item) {
		super.editItem(item);
		getView().setServiceList();
	}

}
