package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.Sequences;
import org.mobibank.payme.backend.services.AppService;
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
public class SequenceView extends AbstractCrudView<Sequences>{

	/**
	 * 
	 */

	private final SequencePresenter presenter;

	private final SequenceViewDesign sequenceViewDesign;

	@Autowired
	public SequenceView(SequencePresenter presenter) {
		this.presenter = presenter;
		this.sequenceViewDesign = new SequenceViewDesign(); 
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}

	@Override
	protected SequencePresenter getPresenter() {
		return presenter;
	}

	@Override
	public SequenceViewDesign getViewComponent() {
		return sequenceViewDesign;
	}

	@Override
	protected Grid<Sequences> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Sequences> grid) {
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
		return getViewComponent().sequence;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Sequences> binder) {
		getViewComponent().bindForm(binder);
	}

	public void setServiceList() {
		AppService service = BeanLocator.find(AppService.class);
		getViewComponent().service.setItems(service.findAll());

		getViewComponent().service.addSelectionListener(e -> {
			if(e.getValue() != null)
				getViewComponent().compte.setItems(e.getValue().getComptes());
				getViewComponent().context.setItems(e.getValue().getContext());
		});
	}
}
