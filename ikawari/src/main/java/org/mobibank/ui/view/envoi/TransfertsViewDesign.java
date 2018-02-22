package org.mobibank.ui.view.envoi;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow.MarginSize;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class TransfertsViewDesign extends VerticalLayout {

	/**
	 * 
	 */
	protected Panel searchPanel;
	protected Button searchButton;
	protected Button newOrder;
	protected TextField searchField;
	protected CheckBox includePast;
	
	public TransfertsViewDesign() {
		addStyleName("storefront");
		setSpacing(false);
		setMargin(false);
		setSizeFull();
		
		searchField = new TextField();
		searchField.setPlaceholder("Recherche");
		searchField.setId("searchField");
		
		searchButton = new Button(VaadinIcons.SEARCH);
		searchButton.setId("searchButton");
		
		CssLayout searchLayout = new CssLayout(searchField, searchButton);
		searchLayout.addStyleName("list-filters");
		
		newOrder = new Button("Nouveau",VaadinIcons.PLUS);
		newOrder.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		newOrder.setId("newOrder");
		
		includePast = new CheckBox("Transactions passées", false);
		includePast.addStyleName(ValoTheme.CHECKBOX_LARGE);
		
		ResponsiveLayout responsiveLayout = new ResponsiveLayout();
		responsiveLayout.addRow()
		.withComponents(searchLayout, newOrder, includePast)
		.withMargin(MarginSize.SMALL);
		
		searchPanel = new Panel(responsiveLayout);
		searchPanel.setId("searchPanel");
		searchPanel.addStyleName("borderless");
		
		addComponent(searchPanel);
	}
}
