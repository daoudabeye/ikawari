package org.mobibank.ui.view.admin.services;

import org.mobibank.backend.data.entity.Services;
import org.mobibank.backend.services.AppService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
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
