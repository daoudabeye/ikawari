package org.mobibank.payme.ui.view.envoi.ordres;

import javax.annotation.PostConstruct;

import org.mobibank.payme.backend.data.entity.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;

@SpringComponent
@PrototypeScope
public class PayerCombobox extends ComboBox<Agent> {

	private final PayerComboBoxDataProvider dataProvider;

	@Autowired
	public PayerCombobox(PayerComboBoxDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		setEmptySelectionAllowed(false);
		setTextInputAllowed(false);
		setPlaceholder("Agent");
		setItemCaptionGenerator(Agent::getNom);
	}

	@PostConstruct
	private void initDataProvider() {
		setDataProvider(dataProvider);
	}
}
