package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.services.MyPaymeService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class MyPaymePresenter extends AbstractCrudPresenter<MyPayme, MyPaymeService, MyPaymeView> {

	/**
	 * 
	 */

	@Autowired
	public MyPaymePresenter(MyPaymeDataProvider dataProvider, NavigationManager navigationManager,
			MyPaymeService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(MyPayme item) {
		super.editItem(item);
		getView().setPaysList();
	}
}
