package org.mobibank.payme.ui.view.admin.pays;

import org.mobibank.payme.backend.data.entity.Entite;
import org.mobibank.payme.backend.services.EntiteService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class EntitePresenter extends AbstractCrudPresenter<Entite, EntiteService, EntiteView> {

	/**
	 * 
	 */

	@Autowired
	public EntitePresenter(EntiteDataProvider dataProvider, NavigationManager navigationManager,
			EntiteService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Entite item) {
		super.editItem(item);
		getView().setPaysList();
		getView().setEnitesList();
	}
}
