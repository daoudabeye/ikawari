package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.Change;
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
public class DeviseView extends AbstractCrudView<Change> {

	/**
	 * 
	 */
	private final DevisePresenter presenter;
	private final DeviseViewDesign viewDesign;


	@Autowired
	public DeviseView(DevisePresenter presenter) {
		this.presenter = presenter;
		viewDesign = new DeviseViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public DeviseViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected DevisePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Change> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Change> grid) {
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
		return getViewComponent().src;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Change> binder) {
		getViewComponent().bindForm(binder);
	}

}
