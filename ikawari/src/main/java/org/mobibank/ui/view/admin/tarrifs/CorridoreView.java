package org.mobibank.ui.view.admin.tarrifs;

import java.util.Collection;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Corridore;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Zone;
import org.mobibank.backend.services.PaysService;
import org.mobibank.backend.services.ZoneService;
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
public class CorridoreView extends AbstractCrudView<Corridore> {

	/**
	 * 
	 */
	private final CorridorePresenter presenter;
	private final CorridoreViewDesign viewDesign;


	@Autowired
	public CorridoreView(CorridorePresenter presenter) {
		this.presenter = presenter;
		viewDesign = new CorridoreViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	public CorridoreViewDesign getViewComponent() {
		return viewDesign;
	}

	@Override
	protected CorridorePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Corridore> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Corridore> grid) {
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
		return getViewComponent().zoneSrc;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Corridore> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setItemList() {
		
		Collection<Zone> zones = BeanLocator.find(ZoneService.class).findAll();
		getViewComponent().zoneSrc.setItems(zones);
		getViewComponent().zoneDst.setItems(zones);
		
		Collection<Pays> pays = BeanLocator.find(PaysService.class).findAll();
		getViewComponent().paysSrc.setItems(pays);
		getViewComponent().paysDst.setItems(pays);
	}
}
