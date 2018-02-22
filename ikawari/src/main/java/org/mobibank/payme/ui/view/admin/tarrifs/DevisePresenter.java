package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.Change;
import org.mobibank.payme.backend.services.ChangeService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class DevisePresenter extends AbstractCrudPresenter<Change, ChangeService, DeviseView> {

	/**
	 * 
	 */

	@Autowired
	public DevisePresenter(DeviseDataProvider dataProvider, NavigationManager navigationManager,
			ChangeService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Change item) {
		super.editItem(item);
	}
}
