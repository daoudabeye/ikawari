package org.mobibank.ui.view.admin.tarrifs;

import org.mobibank.backend.data.entity.GrilleWallet;
import org.mobibank.backend.services.GrilleWalletService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class GrilleWalletPresenter extends AbstractCrudPresenter<GrilleWallet, GrilleWalletService, GrilleWalletView> {

	/**
	 * 
	 */

	@Autowired
	public GrilleWalletPresenter(GrilleWalletDataProvider dataProvider, NavigationManager navigationManager,
			GrilleWalletService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(GrilleWallet item) {
		super.editItem(item);
		getView().setListedItems();
	}
}
