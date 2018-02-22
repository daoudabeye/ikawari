package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.GrilleWallet;
import org.mobibank.payme.backend.services.GrilleWalletService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
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
