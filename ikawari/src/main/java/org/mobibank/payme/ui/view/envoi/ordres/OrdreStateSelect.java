package org.mobibank.payme.ui.view.envoi.ordres;

import org.mobibank.payme.HasLogger;
import org.mobibank.payme.backend.numerate.StatutTransfert;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;

@SpringComponent
@ViewScope
public class OrdreStateSelect extends ComboBox<StatutTransfert> implements HasLogger{

	public OrdreStateSelect() {
		setEmptySelectionAllowed(false);
		setTextInputAllowed(false);
		setItems(StatutTransfert.values());
		setItemCaptionGenerator(StatutTransfert::getDisplayName);
	}
}
