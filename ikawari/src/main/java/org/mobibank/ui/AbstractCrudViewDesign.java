package org.mobibank.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class AbstractCrudViewDesign<T> extends VerticalLayout{
	
	/**
	 * 
	 */
	public TextField search;
	public Button add;
	public Button update;
	public Button cancel;
	public Button delete;
	public VerticalLayout form;
	
	private CssLayout fieldContainer;
	private CssLayout gridLayout;

	public AbstractCrudViewDesign() {
		setSizeFull();
		addStyleName("crud-template");
		setResponsive(true);
		setMargin(false);
		setSpacing(false);
		
		HorizontalLayout topBar = new HorizontalLayout();
		topBar.addStyleName("top-bar");
		topBar.setWidth("100%");
		topBar.setMargin(false);
		topBar.setSpacing(false);
		topBar.setHeight(50, Unit.PIXELS);
		
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setWidth("100%");
		searchLayout.setHeightUndefined();
		searchLayout.setMargin(false);
		searchLayout.setSpacing(false);
		topBar.addComponent(searchLayout);
		topBar.setExpandRatio(searchLayout, 1);
		topBar.setComponentAlignment(searchLayout, Alignment.MIDDLE_LEFT);
		
		search = new TextField();
		search.addStyleName("small");
		search.addStyleName("inline-icon");
		search.addStyleName("search");
		search.setIcon(VaadinIcons.SEARCH);
		search.setWidth("100%");
		search.setHeightUndefined();
		search.setMaxLength(-1);
		search.setPlaceholder("Search");
		search.setValueChangeMode(ValueChangeMode.LAZY);
		search.setValueChangeTimeout(400);
		searchLayout.addComponent(search);
		searchLayout.setComponentAlignment(search, Alignment.TOP_LEFT);
		
		add = new Button("add",VaadinIcons.PLUS);
		add.addStyleName("borderless");
		topBar.addComponent(add);
		topBar.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
		
		addComponent(topBar);
		setComponentAlignment(topBar, Alignment.TOP_LEFT);
		
		CssLayout content = new CssLayout();
		content.addStyleName("content");
		content.setSizeFull();
		
		gridLayout = new CssLayout();
		gridLayout.addStyleName("list");
		gridLayout.setSizeFull();
		content.addComponent(gridLayout);
		
		form = new VerticalLayout();
		form.addStyleName("inspect");
		form.setSizeFull();
		form.setMargin(false);
		form.setSpacing(false);
		content.addComponent(form);
		
		fieldContainer = new CssLayout();
		fieldContainer.addStyleName("edit");
		fieldContainer.setSizeFull();
		form.addComponent(fieldContainer);
		form.setExpandRatio(fieldContainer, 1);
		form.setComponentAlignment(fieldContainer, Alignment.TOP_LEFT);
		
		HorizontalLayout buttonContainer = new HorizontalLayout();
		buttonContainer.addStyleName("buttons");
		buttonContainer.addStyleName("border-top");
		buttonContainer.setWidth("100%");
		buttonContainer.setHeight(50, Unit.PIXELS);
		buttonContainer.setMargin(false);
		buttonContainer.setSpacing(false);
		form.addComponent(buttonContainer);
		form.setComponentAlignment(buttonContainer, Alignment.TOP_LEFT);
		
		update = new Button("Update");
		update.addStyleName("small primary");
		buttonContainer.addComponent(update);
		buttonContainer.setComponentAlignment(update, Alignment.MIDDLE_LEFT);
		
		cancel = new Button("Cancel");
		cancel.addStyleName("small");
		buttonContainer.addComponent(cancel);
		buttonContainer.setComponentAlignment(cancel, Alignment.MIDDLE_LEFT);
		
		delete = new Button("Delete");
		delete.addStyleName("small danger");
		buttonContainer.addComponent(delete);
		buttonContainer.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
		
		addComponent(content);
		setExpandRatio(content, 1);
		setComponentAlignment(content, Alignment.TOP_LEFT);
	}
	
	public void addGrid(Grid<T> grid){
		grid.setSizeFull();
		grid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		gridLayout.addComponent(grid);
	}
	
	public void addField(Component field){
		field.setWidth("100%");
		field.addStyleName("small");
		
		CssLayout section = new CssLayout();
		section.addStyleName("section half");
		section.setWidth("100%");
		section.addComponent(field);
		
		fieldContainer.addComponent(section);
	}
}
