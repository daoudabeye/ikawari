package org.mobibank.payme.ui.view.admin.user;

import org.mobibank.payme.backend.data.entity.Manager;
import org.mobibank.payme.ui.AbstractCrudView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SpringView
public class ManagerView extends AbstractCrudView<Manager> {

	/**
	 * 
	 */
	private final ManagerPresenter presenter;
	private final ManagerViewDesign viewDesign;


	@Autowired
	public ManagerView(ManagerPresenter presenter) {
		this.presenter = presenter;
		viewDesign = new ManagerViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public ManagerViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected ManagerPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Manager> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Manager> grid) {
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
		return getViewComponent().role;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Manager> binder) {
		getViewComponent().bindForm(binder);
	}

}
