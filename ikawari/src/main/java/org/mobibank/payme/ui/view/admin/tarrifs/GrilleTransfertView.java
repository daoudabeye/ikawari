package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.GrilleTransfert;
import org.mobibank.payme.backend.services.AppService;
import org.mobibank.payme.backend.services.CorridoreService;
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
public class GrilleTransfertView extends AbstractCrudView<GrilleTransfert> {

	/**
	 * 
	 */
	private final GrilleTransfertPresenter presenter;
	private final GrilleTransfertViewDesign viewDesign;


	@Autowired
	public GrilleTransfertView(GrilleTransfertPresenter presenter) {
		this.presenter = presenter;
		viewDesign = new GrilleTransfertViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public GrilleTransfertViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected GrilleTransfertPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<GrilleTransfert> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<GrilleTransfert> grid) {
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
		return getViewComponent().montantMin;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<GrilleTransfert> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setListedItems() {
		getViewComponent().corridore.setItems(BeanLocator.find(CorridoreService.class).findAll());
		getViewComponent().services.setItems(BeanLocator.find(AppService.class).findAll());
	}
	
}
