package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.services.AgentService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class AgentPresenter extends AbstractCrudPresenter<Agent, AgentService, AgentView> {

	/**
	 * 
	 */

	@Autowired
	public AgentPresenter(AgentDataProvider dataProvider, NavigationManager navigationManager,
			AgentService service) {
		super(navigationManager, service, dataProvider);
	}
	
	@Override
	protected void editItem(Agent item) {
		super.editItem(item);
		getView().addPaysSelecteListener();
	}
	
}
