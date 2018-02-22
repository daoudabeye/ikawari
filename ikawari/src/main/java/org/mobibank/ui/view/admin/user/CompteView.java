package org.mobibank.ui.view.admin.user;

import org.mobibank.backend.data.entity.Compte;
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
public class CompteView extends AbstractCrudView<Compte> {

	/**
	 * 
	 */
	private final ComptePresenter presenter;
	private final CompteViewDesign viewDesign;
	
	@Autowired
	public CompteView(ComptePresenter presenter) {
		this.presenter = presenter;
		this.viewDesign = new CompteViewDesign();
	}
	
	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}
	
	@Override
	protected ComptePresenter getPresenter() {
		return presenter;
	}
	
	@Override
	public CompteViewDesign getViewComponent() {
		return viewDesign;
	}


	@Override
	protected Grid<Compte> getGrid() {
		// TODO Auto-generated method stub
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Compte> grid) {
		getViewComponent().list = grid;
	}

	@Override
	protected Component getForm() {
		return getViewComponent().form;
	}

	@Override
	protected Button getAdd() {
		// TODO Auto-generated method stub
		return getViewComponent().add;
	}

	@Override
	protected Button getCancel() {
		// TODO Auto-generated method stub
		return getViewComponent().cancel;
	}

	@Override
	protected Button getDelete() {
		// TODO Auto-generated method stub
		return getViewComponent().delete;
	}

	@Override
	protected Button getUpdate() {
		// TODO Auto-generated method stub
		return getViewComponent().update;
	}

	@Override
	protected TextField getSearch() {
		// TODO Auto-generated method stub
		return getViewComponent().search;
	}

	@Override
	protected Focusable getFirstFormField() {
		// TODO Auto-generated method stub
		return getViewComponent().type;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Compte> binder) {
		// TODO Auto-generated method stub
		getViewComponent().bindForm(binder);
	}

}
