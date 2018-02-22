package org.mobibank.ui.view.admin.services;

import org.mobibank.backend.data.entity.Services;
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
public class ServiceView extends AbstractCrudView<Services> {

	/**
	 * 
	 */
	private final ServicePresenter presenter;
	private final ServiceViewDesign viewDesign;


	@Autowired
	public ServiceView(ServicePresenter presenter) {
		this.presenter = presenter;
		viewDesign = new ServiceViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public ServiceViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected ServicePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Services> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Services> grid) {
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
		return getViewComponent().name;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Services> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setPaysList() {
//		PaysService service = BeanLocator.find(PaysService.class);
//		getViewComponent().pays.setItems(service.findAll());
	}

}
