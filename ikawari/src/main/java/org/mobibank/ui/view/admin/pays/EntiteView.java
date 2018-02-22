package org.mobibank.ui.view.admin.pays;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.services.EntiteService;
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
public class EntiteView extends AbstractCrudView<Entite> {

	/**
	 * 
	 */
	private final EntitePresenter presenter;
	private final EntiteViewDesign viewDesign;


	@Autowired
	public EntiteView(EntitePresenter presenter) {
		this.presenter = presenter;
		viewDesign = new EntiteViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public EntiteViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected EntitePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Entite> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Entite> grid) {
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
	public void bindFormFields(BeanValidationBinder<Entite> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setPaysList() {
		PaysService paysService = BeanLocator.find(PaysService.class);
		getViewComponent().pays.setItems(paysService.findAll());
	}

	public void setEnitesList() {
		EntiteService service = BeanLocator.find(EntiteService.class);
		getViewComponent().parent.setItems(service.findAll());
		
	}
}
