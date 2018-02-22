package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.GrilleTransfert;
import org.mobibank.payme.backend.services.GrilleTransfertService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class GrilleTransfertPresenter extends AbstractCrudPresenter<GrilleTransfert, GrilleTransfertService, GrilleTransfertView> {

	/**
	 * 
	 */

	@Autowired
	public GrilleTransfertPresenter(GrilleTransfertDataProvider dataProvider, NavigationManager navigationManager,
			GrilleTransfertService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(GrilleTransfert item) {
		super.editItem(item);
		getView().setListedItems();
	}
}
