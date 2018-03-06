package org.mobibank.ui;

import org.mobibank.HasLogger;
import org.vaadin.inputmask.InputMask;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author BEYE
 *
 */
@SuppressWarnings("serial")
@SpringView
public class OtpAthenticationView extends ResponsiveLayout implements View, HasLogger {

	public static final String SEPARATOR = "-";
	
	private boolean authenticated = false;
	
	TextField code;
	Button check;

	public OtpAthenticationView() {
		setSizeFull();
		
		Panel box = new Panel();
		box.addStyleNames("card","card-3");
		
		FormLayout form = new FormLayout();
		form.setMargin(true);
		box.setContent(form);
		
		Label titre = new Label("Authentification");
		titre.setIcon(VaadinIcons.LOCK);
		titre.addStyleName(ValoTheme.LABEL_H2);
		form.addComponent(titre);
		
		Label resume = new Label("Merci de saisir le code d'authentification.");
		form.addComponent(resume);
		
		code = new TextField("PIN :");
		InputMask.addTo(code, "999-999");
		form.addComponent(code);
		
		check = new Button("Valider", VaadinIcons.CHECK);
		check.setClickShortcut(KeyCode.ENTER);
		form.addComponent(check);
		
		ResponsiveRow row = addRow()
		.withAlignment(Alignment.MIDDLE_CENTER)
		.withComponents(box);
		row.setSizeFull();
		
	}
	
	public void addOtpLitener(Button.ClickListener listener) {
		check.addClickListener(listener);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public String getInput() {
		return code.getValue().replaceAll(SEPARATOR, "");
	}
	
}
