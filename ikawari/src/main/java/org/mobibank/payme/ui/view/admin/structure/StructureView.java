package org.mobibank.payme.ui.view.admin.structure;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Structure;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.services.AgentService;
import org.mobibank.payme.backend.services.EntiteService;
import org.mobibank.payme.backend.services.PaysService;
import org.mobibank.payme.ui.AbstractCrudView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

@SpringView
public class StructureView extends AbstractCrudView<Structure> {

	/**
	 * 
	 */

	private final StructurePresenter presenter;

	private final StructureViewDesign structureViewDesign;
	
	private boolean passwordRequired;
	/**
	 * Custom validator to be able to decide dynamically whether the password
	 * field is required or not (empty value when updating the user is
	 * interpreted as 'do not change the password').
	 */
	private Validator<String> passwordValidator = new Validator<String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		BeanValidator passwordBeanValidator = new BeanValidator(Utilisateur.class, "password");

		@Override
		public ValidationResult apply(String value, ValueContext context) {
			if (!passwordRequired && value.isEmpty()) {
				// No password required and field is empty
				// OK regardless of other restrictions as the empty value will
				// not be used
				return ValidationResult.ok();
			} else {
				return passwordBeanValidator.apply(value, context);
			}
		}
	};

	@Autowired
	public StructureView(StructurePresenter presenter) {
		this.presenter = presenter;
		structureViewDesign = new StructureViewDesign();
	}

	@Override
	public void init() {
		super.init();
		presenter.init(this);
		getGrid().setColumns("username", "designation", "role", "entite");
	}

	@Override
	public StructureViewDesign getViewComponent() {
		return structureViewDesign;
	}

	@Override
	protected StructurePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected Grid<Structure> getGrid() {
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<Structure> grid) {
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
		return getViewComponent().identifiant;
	}

	@Override
	public void bindFormFields(BeanValidationBinder<Structure> binder) {
		getViewComponent().bindForm(binder);
		binder.forField(getViewComponent().password).withValidator(passwordValidator).bind(bean -> "",
				(bean, value) -> {
					if (value.isEmpty()) {
						// If nothing is entered in the password field, do
						// nothing
					} else {
						bean.setPassword(presenter.encodePassword(value));
					}
				});
		binder.bindInstanceFields(getViewComponent());
	}
	
	public void setPasswordRequired(boolean passwordRequired) {
		this.passwordRequired = passwordRequired;
		getViewComponent().password.setRequiredIndicatorVisible(passwordRequired);
	}

	public void addPaysSelecteListener() {
		getViewComponent().pays.setItems(BeanLocator.find(PaysService.class).findAll());
		getViewComponent().pays
		.addSelectionListener(e -> {
			if(e.getValue() != null)
				getViewComponent().entites.setItems(BeanLocator.find(EntiteService.class).findByPays(e.getValue()));
				getViewComponent().master.setItems(BeanLocator.find(AgentService.class).findByPays(e.getValue(), Role.MASTER));
		});
	}

}
