package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Client;
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
public class ClientView extends AbstractCrudView<Client> {

	/**
	 * 
	 */
	private final ClientPresenter presenter;
	private final ClientViewDesign viewDesign;


	@Autowired
	public ClientView(ClientPresenter presenter) {
		this.presenter = presenter;
		viewDesign = new ClientViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public ClientViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected ClientPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Client> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Client> grid) {
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
	public void bindFormFields(BeanValidationBinder<Client> binder) {
		getViewComponent().bindForm(binder);
	}
	
}
