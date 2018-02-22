package org.mobibank.ui.view.admin.user;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.services.AgentService;
import org.mobibank.backend.services.EntiteService;
import org.mobibank.backend.services.PaysService;
import org.mobibank.backend.services.StructureService;
import org.mobibank.ui.AbstractCrudView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SpringView
public class AgentView extends AbstractCrudView<Agent> {

	/**
	 * 
	 */
	private final AgentPresenter presenter;
	private final AgentViewDesign viewDesign;


	@Autowired
	public AgentView(AgentPresenter presenter) {
		this.presenter = presenter;
		viewDesign = new AgentViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public AgentViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected AgentPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Agent> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Agent> grid) {
		getViewComponent().list = grid;
	}

	@Override
	protected Component getForm() {
		return getViewComponent().form;
	}

	@Override
	protected Button getAdd() {
		return getViewComponent().add;
	}

	@Override
	protected Button getCancel() {
		return getViewComponent().cancel;
	}

	@Override
	protected Button getDelete() {
		return getViewComponent().delete;
	}

	@Override
	protected Button getUpdate() {
		return getViewComponent().update;
	}

	@Override
	protected TextField getSearch() {
		return getViewComponent().search;
	}

	@Override
	protected Focusable getFirstFormField() {
		return getViewComponent().nom;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Agent> binder) {
		getViewComponent().bindForm(binder);
	}

	public void addPaysSelecteListener() {
		getViewComponent().pays.setItems(BeanLocator.find(PaysService.class).findAll());
		getViewComponent().pays
		.addSelectionListener(e -> {
			if(e.getValue() != null)
				getViewComponent().entites.setItems(BeanLocator.find(EntiteService.class).findByPays(e.getValue()));
				getViewComponent().master.setItems(BeanLocator.find(AgentService.class).findByPays(e.getValue(), Role.MASTER));
				getViewComponent().agence.setItems(BeanLocator.find(StructureService.class).findByRole(e.getValue(), Role.POINTSERVICE));
		});
	}
}
