package org.mobibank.payme.backend.data.entity;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mobibank.payme.backend.numerate.Jours;

@Entity
@Table( name = Horaires.TABLE_NAME )
@NamedQuery(name="Horaires.findAll", query = "SELECT h FROM Horaires h")
public class Horaires extends AbstractEntity {
	
	public static final String TABLE_NAME = "horaires";

	@Column(name = "jours")
	@Enumerated
	private Jours jours;
	
	@Column(name = "heure_debut")
	private LocalTime heureDebut;
	
	@Column(name = "heure_fin")
	private LocalTime heureFin;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn
	private Structure structure;
	
	public Horaires() {
		super();
	}
	
	
	
	public Horaires(Jours jours, LocalTime heureDebut, LocalTime heureFin) {
		super();
		this.jours = jours;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return jours + " D:"+heureDebut + ", F:"+heureFin;
	}
	public Jours getJours() {
		return jours;
	}

	public void setJours(Jours jours) {
		this.jours = jours;
	}

	public LocalTime getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}

	public LocalTime getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}
}
