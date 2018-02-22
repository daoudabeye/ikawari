package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Client;
import org.mobibank.backend.services.ClientService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ClientPresenter extends AbstractCrudPresenter<Client, ClientService, ClientView> {

	/**
	 * 
	 */

	@Autowired
	public ClientPresenter(ClientDataProvider dataProvider, NavigationManager navigationManager,
			ClientService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Client item) {
		super.editItem(item);
	}
}
