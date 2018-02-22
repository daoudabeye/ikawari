package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Manager;
import org.mobibank.backend.services.ManagerService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ManagerPresenter extends AbstractCrudPresenter<Manager, ManagerService, ManagerView> {

	/**
	 * 
	 */

	@Autowired
	public ManagerPresenter(ManagerDataProvider dataProvider, NavigationManager navigationManager,
			ManagerService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Manager item) {
		super.editItem(item);
	}
}
