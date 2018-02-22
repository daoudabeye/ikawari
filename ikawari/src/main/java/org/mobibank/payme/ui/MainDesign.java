package org.mobibank.payme.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class MainDesign extends HorizontalLayout {

	public MainDesign() {
		setSizeFull();
		setResponsive(true);
		setSpacing(false);
		addStyleName("app-shell");
		
		CssLayout navBarContainer = new CssLayout();
		navBarContainer.setHeight("100%");
		navBarContainer.setWidth("200px");
		navBarContainer.addStyleName("navigation-bar-container");
		addComponent(navBarContainer);
		
		CssLayout navBar = new CssLayout();
		navBar.setSizeFull();
		navBar.addStyleName("navigation-bar");
		navBarContainer.addComponent(navBar);
		
		Label logo = new Label("PAYME");
		logo.setWidth("100%");
		logo.setContentMode(ContentMode.TEXT);
		logo.addStyleName("logo");
		navBar.addComponent(logo);
		
		Label activeViewName = new Label("active");
		activeViewName.addStyleName("activeViewName");
		activeViewName.setContentMode(ContentMode.TEXT);
		
		Button menu = new Button("Menu");
		menu.setId("menuButton");
		menu.addStyleName("menu borderless");
		
		CssLayout navigation = new CssLayout();
		navigation.setWidth("100%");
		navigation.setId("menu");
		navigation.addStyleName("navigation");
		
	}
	
	public Button createMenuItem(String caption, VaadinIcons icon, int height) {
		Button item = new Button(caption, icon);
		item.addStyleName("borderless");
		item.setId(caption);
		item.setWidth("100%");
		if(height > 0) item.setHeight(height+"px");
		return item;
	}
}
