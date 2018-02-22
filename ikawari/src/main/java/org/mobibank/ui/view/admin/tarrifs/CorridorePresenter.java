package org.mobibank.ui.view.admin.tarrifs;

import org.mobibank.backend.data.entity.Corridore;
import org.mobibank.backend.services.CorridoreService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class CorridorePresenter extends AbstractCrudPresenter<Corridore, CorridoreService, CorridoreView> {

	/**
	 * 
	 */

	@Autowired
	public CorridorePresenter(CorridoreDataProvider dataProvider, NavigationManager navigationManager,
			CorridoreService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Corridore item) {
		super.editItem(item);
		getView().setItemList();
	}
}
