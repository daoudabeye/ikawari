package org.mobibank.ui.view.envoi.ordres;

import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.mobibank.backend.data.entity.Transfert;
import org.mobibank.backend.numerate.StatutTransfert;
import org.mobibank.ui.components.ConfirmPopup;
import org.mobibank.ui.utils.DollarPriceConverter;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
@SpringView(name = "ordres")
public class OrdreEditView extends OrdreEditViewDesign implements View {

	public enum Mode {
		EDIT, REPORT, CONFIRMATION;
	}

	private final OrdreEditPresenter presenter;

	private final DollarPriceConverter montantConverter;

	private BeanValidationBinder<Transfert> binder;

	private Mode mode;

	private boolean hasChanges;

	@Autowired
	public OrdreEditView(DollarPriceConverter montantConverter, OrdreEditPresenter ordreEditPresenter) {
		this.montantConverter = montantConverter;
		this.presenter = ordreEditPresenter;
	}

	@PostConstruct
	public void init() {
		presenter.init(this);

		// We're limiting dueTime to even hours between 07:00 and 17:00
		dueTime.setItems(IntStream.range(7, 17).mapToObj(h -> LocalTime.of(h, 0)));

		// Binder takes care of binding Vaadin fields defined as Java member
		// fields in this class to properties in the Order bean
		binder = new BeanValidationBinder<>(Transfert.class);

		// Almost all fields are required, so we don't want to display
		// indicators
		binder.setRequiredConfigurator(null);

		// Bindings are done in the order the fields appear on the screen as we
		// report validation errors for the first invalid field and it is most
		// intuitive for the user that we start from the top if there are
		// multiple errors.
		binder.bindInstanceFields(this);

		// Must bind sub properties manually until
		// https://github.com/vaadin/framework/issues/9210 is fixed
		binder.bind(fullName, "sender.nom");
		binder.bind(phone, "sender.telephone");
		//		binder.bind(details, "customer.details");

		// Track changes manually as we use setBean and nested binders
		binder.addValueChangeListener(e -> hasChanges = true);

		//		addItems.addClickListener(e -> addEmptyOrderItem());
		cancel.addClickListener(e -> presenter.editBackCancelPressed());
		ok.addClickListener(e -> presenter.okPressed());
	}


	@Override
	public void enter(ViewChangeEvent event) {
		String orderId = event.getParameters();
		if ("".equals(orderId)) {
			presenter.enterView(null);
		} else {
			presenter.enterView(Long.valueOf(orderId));
		}
	}

	public void showNotFound() {
		removeAllComponents();
		addComponent(new Label("Aucun resultat trouvé"));
	}

	public void setOrdre(Transfert ordre) {
		stateLabel.setValue(ordre.getStatut().getDisplayName());
		binder.setBean(ordre);
		//		productInfoContainer.removeAllComponents();

		header.setVisible(ordre.getId() != null);
		if (ordre.getId() == null) {
			dueDate.focus();
		} else {
			ordreId.setValue("#" + ordre.getFolio());
			//			for (OrderItem item : order.getItems()) {
			//				ProductInfo productInfo = createProductInfo(item);
			//				productInfo.setReportMode(mode != Mode.EDIT);
			//				productInfoContainer.addComponent(productInfo);
			//			}
			//			history.setOrder(order);
		}
		hasChanges = false;
	}

	public void setMode(Mode mode) {
		// Allow to style different modes separately
		if (this.mode != null) {
			removeStyleName(this.mode.name().toLowerCase());
		}
		addStyleName(mode.name().toLowerCase());

		this.mode = mode;
		binder.setReadOnly(mode != Mode.EDIT);
//		for (Component c : productInfoContainer) {
//			if (c instanceof ProductInfo) {
//				((ProductInfo) c).setReportMode(mode != Mode.EDIT);
//			}
//		}
//		addItems.setVisible(mode == Mode.EDIT);
//		history.setVisible(mode == Mode.REPORT);
		state.setVisible(mode == Mode.EDIT);

		if (mode == Mode.REPORT) {
			cancel.setCaption("Edit");
			cancel.setIcon(VaadinIcons.EDIT);
			Optional<StatutTransfert> nextState = presenter.getNextHappyPathState(getOrdre().getStatut());
			ok.setCaption("Mark as " + nextState.map(StatutTransfert::getDisplayName).orElse("?"));
			ok.setVisible(nextState.isPresent());
		} else if (mode == Mode.CONFIRMATION) {
			cancel.setCaption("Back");
			cancel.setIcon(VaadinIcons.ANGLE_LEFT);
			ok.setCaption("Place order");
			ok.setVisible(true);
		} else if (mode == Mode.EDIT) {
			cancel.setCaption("Cancel");
			cancel.setIcon(VaadinIcons.CLOSE);
			if (getOrdre() != null && !getOrdre().isNew()) {
				ok.setCaption("Save");
			} else {
				ok.setCaption("Review order");
			}
			ok.setVisible(true);
		} else {
			throw new IllegalArgumentException("Unknown mode " + mode);
		}

	}

	public Mode getMode() {
		return mode;
	}

	protected Transfert getOrdre(){
		return binder.getBean();
	}

	public Stream<HasValue<?>> validate() {
		Stream<HasValue<?>> errorFields = binder.validate().getFieldValidationErrors().stream()
				.map(BindingValidationStatus::getField);

		//		for (Component c : productInfoContainer) {
		//			if (c instanceof ProductInfo) {
		//				ProductInfo productInfo = (ProductInfo) c;
		//				if (!productInfo.isEmpty()) {
		//					errorFields = Stream.concat(errorFields, productInfo.validate());
		//				}
		//			}
		//		}
		return errorFields;
	}
	
	public void onProductInfoChanged() {
		hasChanges = true;
	}
	
	@Override
	public void beforeLeave(ViewBeforeLeaveEvent event) {
		if (!containsUnsavedChanges()) {
			event.navigate();
		} else {
			ConfirmPopup.get().showLeaveViewConfirmDialog(this, event::navigate);
		}
	}
	
	public boolean containsUnsavedChanges() {
		return hasChanges;
	}
}
