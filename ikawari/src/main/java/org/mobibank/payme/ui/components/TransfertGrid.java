package org.mobibank.payme.ui.components;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.mobibank.payme.backend.data.entity.Transfert;
import org.mobibank.payme.backend.data.entity.TransfertUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;

@SuppressWarnings("serial")
@SpringComponent
@PrototypeScope
public class TransfertGrid extends Grid<Transfert>{
	@Autowired
	TransfertDataProvider dataProvider;

	@PostConstruct
	protected void init() {
		setDataProvider(dataProvider);
	}

	public TransfertGrid() {
		addStyleName("orders-grid");
		setSizeFull();
		removeHeaderRow(0);

		// Add stylenames to rows
		setStyleGenerator(TransfertGrid::getRowStyle);

		// date column
		Column<Transfert, String> dueColumn = addColumn(
				transfert -> twoRowCell(getTimeHeader(transfert.getDateEnvoi()), String.valueOf(transfert.getHeureEnvoi())),
				new HtmlRenderer());
		dueColumn.setSortProperty("dateEnvoi", "heureEnvoi");
		dueColumn.setStyleGenerator(order -> "due");

		// Summary column
		Column<Transfert, String> summaryColumn = addColumn(transfert -> {
			TransfertUser sender = transfert.getSender();
			return twoRowCell(transfert.getFolio(), getOrderSummary(transfert), true);
		}, new HtmlRenderer()).setExpandRatio(1).setSortProperty("sender.getNom").setMinimumWidthFromContent(false);
		summaryColumn.setStyleGenerator(order -> "summary");
	}
	
	public void filterGrid(String searchTerm, boolean includePast) {
		dataProvider.setFilter(searchTerm);
		dataProvider.setIncludePast(includePast);
	}

	/**
	 * Makes date into a more readable form; "Today", "Mon 7", "12 Jun"
	 * 
	 * @param dueDate
	 *            The date to make into a string
	 * @return A formatted string depending on how far in the future the date
	 *         is.
	 */
	private static String getTimeHeader(LocalDate dueDate) {
		LocalDate today = LocalDate.now();
		if (dueDate.isEqual(today)) {
			return "Today";
		} else {
			// Show weekday for upcoming days
			LocalDate todayNextWeek = today.plusDays(7);
			if (dueDate.isAfter(today) && dueDate.isBefore(todayNextWeek)) {
				// "Mon 7"
				return dueDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.FRANCE) + " "
				+ dueDate.getDayOfMonth();
			} else {
				// In the past or more than a week in the future
				return dueDate.getDayOfMonth() + " " + dueDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRANCE);
			}
		}
	}

	private static String getRowStyle(Transfert transfert) {
		String style = transfert.getStatut().name().toLowerCase();

		long days = LocalDate.now().until(transfert.getDateCreation(), ChronoUnit.DAYS);
		if (days == 0) {
			style += " today";
		} else if (days == 1) {
			style += " tomorrow";
		}

		return style;
	}

	private static String twoRowCell(String header, String content) {
		return "<div class=\"header\">" + HtmlUtils.htmlEscape(header) + "</div><div class=\"content\">"
				+ HtmlUtils.htmlEscape(content) + "</div>";
	}
	
	private static String twoRowCell(String header, String content, boolean htmlcontent) {
		return "<div class=\"header\">" + HtmlUtils.htmlEscape(header) + "</div><div class=\"content\">"
				+ content + "</div>";
	}

	private static String getOrderSummary(Transfert transfert) {
		StringBuilder builder = new StringBuilder();
		builder.append("<b>Pays d'envoi </b>: ").append(transfert.getOrigine())
		.append("<br/> <b> Expediteur </b>: ").append(transfert.getSender())
		.append(" <b>Bénéficiaire : </b>").append(transfert.getBeneficiaire()).append("<br/> <b>Agent </b>:").append(transfert.getAgentExpediteur());
		return builder.toString();
	}
}
