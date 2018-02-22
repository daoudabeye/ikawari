package org.mobibank.payme.ui.view.home;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.ui.components.DeviseGrid;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("apptheme")
public class TauxDeviseUI extends UI {
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout vLayout = new VerticalLayout();
		
		vLayout.addComponent(BeanLocator.find(DeviseGrid.class));
		
		vLayout.addComponent(new Button("Fermer", event -> {// Java 8
            // Close the popup
            JavaScript.eval("close()");
            
            // Detach the UI from the session
            getUI().close();
        }));
		
		setContent(vLayout);
	}

}
