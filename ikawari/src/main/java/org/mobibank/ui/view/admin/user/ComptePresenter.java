package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.services.CompteService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ComptePresenter extends AbstractCrudPresenter<Compte, CompteService, CompteView> {

	/**
	 * 
	 */

	@Autowired
	public ComptePresenter(CompteDataProvider dataProvider, NavigationManager navigationManager,
			CompteService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Compte item) {
		super.editItem(item);
	}
}
