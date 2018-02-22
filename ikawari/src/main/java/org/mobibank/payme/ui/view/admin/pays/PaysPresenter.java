package org.mobibank.payme.ui.view.admin.pays;

import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.services.PaysService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class PaysPresenter extends AbstractCrudPresenter<Pays, PaysService, PaysView>{
	
	/**
	 * 
	 */

	@Autowired
	public PaysPresenter(PaysDataProvider paysDataProvider, NavigationManager navigationManager,
			PaysService service) {
		super(navigationManager, service, paysDataProvider);
	}
	
	@Override
	protected void editItem(Pays item) {
		super.editItem(item);
		getView().setZones(item);
		getView().setProvidersList();
		getView().setBanquesList();
	}

}
