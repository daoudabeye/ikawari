package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.backend.services.OrdresService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
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
