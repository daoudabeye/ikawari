package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.ui.AbstractCrudView;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
@SpringView(name= OrdresView.VIEW_NAME)
public class OrdresView extends AbstractCrudView<Ordres> {
	public static final String VIEW_NAME = "paymefs";
	/**
	 * 
	 */
	private final OrdresPresenter presenter;
	private final OrdresViewDesign viewDesign;


	@Autowired
	public OrdresView(OrdresPresenter presenter) {
		this.presenter = presenter;
		viewDesign = new OrdresViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public OrdresViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected OrdresPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Ordres> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Ordres> grid) {
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
	public void bindFormFields(BeanValidationBinder<Ordres> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setPaysList() {
//		PaysService service = BeanLocator.find(PaysService.class);
//		getViewComponent().pays.setItems(service.findAll());
	}

}
