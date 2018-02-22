package org.mobibank.payme.ui.view.admin.pays;

import org.mobibank.payme.backend.data.entity.Zone;
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
public class ZoneView extends AbstractCrudView<Zone> {

	/**
	 * 
	 */

	private final ZonePresenter presenter;

	private final ZoneViewDesign zoneDesign;

	@Autowired
	public ZoneView(ZonePresenter zonePresenter) {
		this.presenter = zonePresenter;
		zoneDesign = new ZoneViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
//		getGrid().setColumns("code", "designation", "pays");
	}

	@Override
	public ZoneViewDesign getViewComponent() {
		return zoneDesign;
	}

	@Override
	protected ZonePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Zone> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Zone> grid) {
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
		return getViewComponent().designation;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Zone> binder) {
		getViewComponent().bindForm(binder);
	}

}
