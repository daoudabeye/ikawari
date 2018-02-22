package org.mobibank.ui.view.admin.tarrifs;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.GrilleWallet;
import org.mobibank.backend.services.AppService;
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
public class GrilleWalletView extends AbstractCrudView<GrilleWallet> {

	/**
	 * 
	 */
	private final GrilleWalletPresenter presenter;
	private final GrilleWalletViewDesign viewDesign;


	@Autowired
	public GrilleWalletView(GrilleWalletPresenter presenter) {
		this.presenter = presenter;
		viewDesign = new GrilleWalletViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public GrilleWalletViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected GrilleWalletPresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<GrilleWallet> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<GrilleWallet> grid) {
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
		return getViewComponent().montantMax;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<GrilleWallet> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setListedItems() {
		getViewComponent().pays.setItems(BeanLocator.find(PaysService.class).findAll());
		getViewComponent().pays.addSelectionListener(e -> {
			if(e.getValue() != null)
			getViewComponent().services.setItems(BeanLocator.find(AppService.class).find(e.getValue()));
		});
	}

}
