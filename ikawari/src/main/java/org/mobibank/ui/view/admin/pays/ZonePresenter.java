package org.mobibank.ui.view.admin.pays;

import org.mobibank.backend.data.entity.Zone;
import org.mobibank.backend.services.ZoneService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
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
