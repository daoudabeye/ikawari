package org.mobibank.ui.view.admin.pays;

import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.services.EntiteService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
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
