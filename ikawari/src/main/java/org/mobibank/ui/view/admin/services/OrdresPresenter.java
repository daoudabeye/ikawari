package org.mobibank.ui.view.admin.services;

import org.mobibank.backend.data.entity.Ordres;
import org.mobibank.backend.services.OrdresService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class OrdresPresenter extends AbstractCrudPresenter<Ordres, OrdresService, OrdresView> {

	/**
	 * 
	 */

	@Autowired
	public OrdresPresenter(OrdresDataProvider dataProvider, NavigationManager navigationManager,
			OrdresService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Ordres item) {
		super.editItem(item);
		getView().setPaysList();
	}
}
