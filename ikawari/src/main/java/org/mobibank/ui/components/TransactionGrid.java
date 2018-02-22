package org.mobibank.ui.components;

import java.text.DecimalFormat;

import org.mobibank.backend.data.entity.Transaction;
import org.mobibank.backend.numerate.StatutCode;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.HtmlRenderer;

@SuppressWarnings("serial")
public class TransactionGrid extends Grid<Transaction> {
	public TransactionGrid() {
//		setSizeFull();
		setWidth("100%");

		addColumn(Transaction::getReference).setCaption("Refence");
		addColumn(Transaction::getType).setCaption("SENS");
		addColumn(Transaction::getOperation).setCaption("Opération");

		final DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
		addColumn(transaction -> decimalFormat.format(transaction.getMontant()))
		.setCaption("Montant").setComparator((t1, t2) -> {
			return t1.getMontant().compareTo(t2.getMontant());
		}).setStyleGenerator(product -> "align-right");

		addColumn(Transaction::getDateValeur).setCaption("Date Valeur");
		
		// Add an traffic light icon in front of availability
		addColumn(this::htmlFormatAvailability, new HtmlRenderer())
		.setCaption("Statut").setComparator((p1, p2) -> {
			return p1.getStatut().toString()
					.compareTo(p2.getStatut().toString());
		});
		
	}
	
	public Transaction getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Transaction transaction) {
        getDataCommunicator().refresh(transaction);
    }
	
	private String htmlFormatAvailability(Transaction transaction) {
		StatutCode statut = transaction.getStatut();
		String text = statut.toString();

		String color = "";
		switch (statut) {
		case EFFECTUER:
			color = "#2dd085";
			break;
		case INSTANCE:
		case CONFIRMATION:
			color = "#ffc66e";
			break;
		case ANNULER:
			color = "#f54993";
			break;
		default:
			break;
		}

		String iconCode = "<span class=\"v-icon\" style=\"font-family: "
				+ VaadinIcons.CIRCLE.getFontFamily() + ";color:" + color
				+ "\">&#x"
				+ Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint())
				+ ";</span>";

		return iconCode + " " + text;
	}
}
