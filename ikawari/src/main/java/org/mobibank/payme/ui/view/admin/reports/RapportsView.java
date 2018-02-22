package org.mobibank.payme.ui.view.admin.reports;

import javax.annotation.PostConstruct;

import org.mobibank.payme.backend.data.Role;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;

@SpringView
@Secured({Role.ADMIN, Role.BANQUE})
public class RapportsView extends TabSheet implements View {
	public static final String VIEW_NAME = "Rapports";

	private Panel batchPanel = new Panel("Batch");
	private Panel bceaoPanel = new Panel("Rapports BCEAO");

	@PostConstruct
	public void init(){
		setSizeFull();

		addTab(batchPanel, batchPanel.getCaption());
		batchPanel.setSizeFull();
		batchPanel.setContent(new Batch());

		addTab(bceaoPanel, bceaoPanel.getCaption());
		bceaoPanel.setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {
			
	}

}
