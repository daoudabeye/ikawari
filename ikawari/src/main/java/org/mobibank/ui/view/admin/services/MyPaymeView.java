package org.mobibank.ui.view.admin.services;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.MyPayme;
import org.mobibank.backend.services.PaysService;
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
public class MyPaymeView extends AbstractCrudView<MyPayme> {

	/**
	 * 
	 */
	private final MyPaymePresenter presenter;
	private final MyPaymeViewDesign viewDesign;


	@Autowired
	public MyPaymeView(MyPaymePresenter presenter) {
		this.presenter = presenter;
		viewDesign = new MyPaymeViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public MyPaymeViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected MyPaymePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<MyPayme> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<MyPayme> grid) {
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
	public void bindFormFields(BeanValidationBinder<MyPayme> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setPaysList() {
		PaysService service = BeanLocator.find(PaysService.class);
		getViewComponent().pays.setItems(service.findAll());
	}

}
