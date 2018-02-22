package org.mobibank.ui.view.dashboard;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.mobibank.BeanLocator;
import org.mobibank.backend.TransfertRepository;
import org.mobibank.backend.data.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.jarektoro.responsivelayout.ResponsiveRow.SpacingSize;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

/**
 * The dashboard view showing statistics about sales and deliveries.
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
@SuppressWarnings("serial")
@SpringView(name = DashboardView.VIEW_NAME)
@Secured(Role.ADMIN)
@MenuCaption("Dashboard")
@MenuIcon(VaadinIcons.CHART)
public class DashboardView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "Dashboard";
	
	private final BoardLabel transfertsLabel = new BoardLabel("TRANSFERTS", "0", "new");
	private final BoardLabel cancelLabel = new BoardLabel("ANNULATION", "0", "na");
	private final BoardLabel walletLabel = new BoardLabel("POTE-FEUILLE", "0", "today");
	private final BoardLabel walletCancellationLabel = new BoardLabel("ANNULATION", "0", "tomorrow");
	
	@Autowired
	public DashboardView() {
	}

	@PostConstruct
	public void init() {
		setSizeFull();
		setMargin(false);
		setSpacing(false);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		
		transfertsLabel.setContent(""+BeanLocator.find(TransfertRepository.class).countByDateEnvoiAfter(LocalDate.now()));
		
		ResponsiveLayout responsiveLayout = new ResponsiveLayout();
		ResponsiveRow row = responsiveLayout.addRow()
		.withStyleName("board-row-group")
//		.withComponents(new BoardBox(todayLabel), notAvailableBox, new BoardBox(newLabel),
//				new BoardBox(tomorrowLabel))
		.withHorizontalSpacing(false)
		.withMargin(true)
		.withAlignment(Alignment.MIDDLE_CENTER)
		.withVerticalSpacing(SpacingSize.SMALL, true);
		
		row.addColumn().withDisplayRules(12, 6, 3, 3)
		.withComponent(new BoardBox(transfertsLabel));
		
		row.addColumn().withDisplayRules(12, 6, 3, 3)
		.withComponent(new BoardBox(cancelLabel));
		
		row.addColumn().withDisplayRules(12, 6, 3, 3)
		.withComponent(new BoardBox(walletLabel));
		
		row.addColumn().withDisplayRules(12, 6, 3, 3)
		.withComponent(new BoardBox(walletCancellationLabel));
		
		addComponent(responsiveLayout);
		//addComponent(barChart());
		
	}	

}
