package org.mobibank.payme.ui.view.envoi.ordres;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mobibank.payme.HasLogger;
import org.mobibank.payme.backend.data.entity.Transfert;
import org.mobibank.payme.backend.numerate.StatutTransfert;
import org.mobibank.payme.backend.services.AgentService;
import org.mobibank.payme.backend.services.TransfertService;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.mobibank.payme.ui.view.envoi.TransfertsView;
import org.mobibank.payme.ui.view.envoi.ordres.OrdreEditView.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.vaadin.spring.events.EventBus.ViewEventBus;

import com.vaadin.data.HasValue;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@SpringComponent
@ViewScope
public class OrdreEditPresenter implements Serializable, HasLogger{

	private OrdreEditView view;

	private final TransfertService orderService;

	private final AgentService agentService;

	private final NavigationManager navigationManager;

	private final ViewEventBus viewEventBus;

	private static final List<StatutTransfert> happyPath = Arrays.asList(StatutTransfert.NOUVEAU, 
			StatutTransfert.PAYER, StatutTransfert.ANNULER, StatutTransfert.TRANSMIS);

	@Autowired
	public OrdreEditPresenter(TransfertService orderService, AgentService agentService,
			NavigationManager navigationManager, ViewEventBus viewEventBus) {
		super();
		this.orderService = orderService;
		this.agentService = agentService;
		this.navigationManager = navigationManager;
		this.viewEventBus = viewEventBus;
		viewEventBus.subscribe(this);
	}

	void init(OrdreEditView view) {
		this.view = view;
	}

	/**
	 * Called when the user enters the view.
	 */
	public void enterView(Long id) {
		Transfert ordre = null;
		if (id == null) {

		} else {
			ordre = orderService.findOrder(id);
			if (ordre == null) {
				view.showNotFound();
				return;
			}
		}
		refreshView(ordre);
	}

	private void refreshView(Transfert ordre) {
		view.setOrdre(ordre);
		if (ordre.getId() == null) {
			view.setMode(Mode.EDIT);
		} else {
			view.setMode(Mode.REPORT);
		}
	}

	public void editBackCancelPressed() {
		if (view.getMode() == Mode.REPORT) {
			// Edit order
			view.setMode(Mode.EDIT);
		} else if (view.getMode() == Mode.CONFIRMATION) {
			// Back to edit
			view.setMode(Mode.EDIT);
		} else if (view.getMode() == Mode.EDIT) {
			// Cancel edit
			Long id = view.getOrdre().getId();
			if (id == null) {
				navigationManager.navigateTo(TransfertsView.class);
			} else {
				enterView(id);
			}
		}
	}

	public void okPressed() {
		if (view.getMode() == Mode.REPORT) {
			// Set next state
			Transfert ordre = view.getOrdre();
			Optional<StatutTransfert> nextState = getNextHappyPathState(ordre.getStatut());
			if (!nextState.isPresent()) {
				throw new IllegalStateException(
						"The next state button should never be enabled when the state does not follow the happy path");
			}
			orderService.changeState(ordre, nextState.get());
			refresh(ordre.getId());
		} else if (view.getMode() == Mode.CONFIRMATION) {
			Transfert ordre = saveOrdre();
			if (ordre != null) {
				// Navigate to edit view so URL is updated correctly
				navigationManager.updateViewParameter("" + ordre.getId());
				enterView(ordre.getId());
			}
		} else if (view.getMode() == Mode.EDIT) {
			Optional<HasValue<?>> firstErrorField = view.validate().findFirst();
			if (firstErrorField.isPresent()) {
				((Focusable) firstErrorField.get()).focus();
				return;
			}
			// New order should still show a confirmation page
			Transfert order = view.getOrdre();
			if (order.getId() == null) {
				view.setMode(Mode.CONFIRMATION);
			} else {
				order = saveOrdre();
				if (order != null) {
					refresh(order.getId());
				}
			}
		}
	}

	private void refresh(Long id) {
		Transfert ordre = orderService.findOrder(id);
		if (ordre == null) {
			view.showNotFound();
			return;
		}
		refreshView(ordre);

	}

	public Optional<StatutTransfert> getNextHappyPathState(StatutTransfert currentStatut) {
		final int currentIndex = happyPath.indexOf(currentStatut);
		if (currentIndex == -1 || currentIndex == happyPath.size() - 1) {
			return Optional.empty();
		}
		return Optional.of(happyPath.get(currentIndex + 1));
	}

	private Transfert saveOrdre() {
		try {
			Transfert order = view.getOrdre();
			return orderService.save(order);
//		} catch (ValidationException e) {
//			// Should not get here if validation is setup properly
//			Notification.show("Please check the contents of the fields: " + e.getMessage(), Type.ERROR_MESSAGE);
//			getLogger().error("Validation error during order save", e);
//			return null;
		} catch (OptimisticLockingFailureException e) {
			// Somebody else probably edited the data at the same time
			Notification.show("Somebody else might have updated the data. Please refresh and try again.",
					Type.ERROR_MESSAGE);
			getLogger().debug("Optimistic locking error while saving order", e);
			return null;
		} catch (Exception e) {
			// Something went wrong, no idea what
			Notification.show("An unexpected error occurred while saving. Please refresh and try again.",
					Type.ERROR_MESSAGE);
			getLogger().error("Unable to save order", e);
			return null;
		}
	}

}
