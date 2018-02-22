package org.mobibank.payme.ui.view.admin.pays;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.services.StructureService;
import org.mobibank.payme.backend.services.ZoneService;
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
public class PaysView extends AbstractCrudView<Pays> {

	/**
	 * 
	 */
	private final PaysPresenter presenter;
	private final PaysViewDesign paysViewDesign;
	private final ZoneService zoneService;
	
	
	@Autowired
	public PaysView(PaysPresenter presenter, ZoneService zoneService) {
		super();
		this.presenter = presenter;
		this.paysViewDesign = new PaysViewDesign();
		this.zoneService = zoneService;
	}
	
	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	protected PaysPresenter getPresenter() {
		return presenter;
	}
	
	@Override
	public PaysViewDesign getViewComponent() {
		return paysViewDesign;
	}

	@Override
	protected Grid<Pays> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Pays> grid) {
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
		return getViewComponent().nom;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Pays> binder) {
		getViewComponent().bindForm(binder);
	}
	
	public void setZones(Pays pays){
		getViewComponent().zone.setItems(zoneService.findAll());
	}

	public void setProvidersList() {
		StructureService service = BeanLocator.find(StructureService.class);
		getViewComponent().localProvider.setItems(service.findByRole(Role.PROVIDER));
	}

	public void setBanquesList() {
		StructureService service = BeanLocator.find(StructureService.class);
		getViewComponent().mainBanque.setItems(service.findByRole(Role.BANQUE));
	}
	
}
