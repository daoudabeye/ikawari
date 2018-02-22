package org.mobibank.payme.ui;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;

@SpringView
@MenuCaption("Config")  // Set the Caption for the NavigationButton
@MenuIcon(VaadinIcons.COG) // Set the Icon for the NavigationButton
public class ConfigPanel extends Panel implements View {

}
