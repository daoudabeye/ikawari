package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.backend.services.AppService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class ServicePresenter extends AbstractCrudPresenter<Services, AppService, ServiceView> {

	/**
	 * 
	 */

	@Autowired
	public ServicePresenter(ServiceDataProvider dataProvider, NavigationManager navigationManager,
			AppService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Services item) {
		super.editItem(item);
		getView().setPaysList();
	}
}
