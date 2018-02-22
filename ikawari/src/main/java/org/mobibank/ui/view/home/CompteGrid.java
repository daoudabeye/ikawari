package org.mobibank.ui.view.home;

import java.util.Collection;

import org.mobibank.backend.data.entity.Compte;

import com.vaadin.ui.Grid;

public class CompteGrid extends Grid<Compte> {
	
	public CompteGrid(Collection<Compte> comptes) {
		super(Compte.class);
		setSizeFull();
		
		setColumns("type", "solde", "devise", "statut");
		this.setItems(comptes);
	}

}
