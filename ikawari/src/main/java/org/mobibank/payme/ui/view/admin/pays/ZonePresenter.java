package org.mobibank.payme.ui.view.admin.pays;

import org.mobibank.payme.backend.data.entity.Zone;
import org.mobibank.payme.backend.services.ZoneService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ZonePresenter extends AbstractCrudPresenter<Zone, ZoneService, ZoneView> {
	
	/**
	 * 
	 */

	@Autowired
	public ZonePresenter(ZoneDataProvider zoneDataProvider, NavigationManager navigationManager,
			ZoneService service) {
		super(navigationManager, service, zoneDataProvider);
	}
	
	@Override
	protected void editItem(Zone item) {
		super.editItem(item);
	}

}
