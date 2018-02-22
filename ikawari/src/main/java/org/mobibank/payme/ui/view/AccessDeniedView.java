package org.mobibank.payme.ui.view;

import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CssLayout;

@SpringComponent
@PrototypeScope
public class AccessDeniedView extends CssLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		// Nothing to do, just show the view
	}

}
