package org.mobibank.payme.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = Grille.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQuery(name = "Grille.findAll", query = "SELECT g FROM Grille g")
public class Grille extends AbstractEntity {

	public static final String TABLE_NAME = "grille";
	
	@Column(name="montant_min")
	protected double montantMin;

	@Column(name="montant_max")
	protected double montantMax;

	protected double frais;
	
	@ManyToOne
	@JoinColumn
	private Services services;
	
	public Grille() {
		super();
	}
	
	public Grille(double montantMin, double montantMax, double frais, Services services) {
		super();
		this.montantMin = montantMin;
		this.montantMax = montantMax;
		this.frais = frais;
		this.services = services;
	}

	public double getMontantMin() {
		return montantMin;
	}

	public void setMontantMin(double montantMin) {
		this.montantMin = montantMin;
	}

	public double getMontantMax() {
		return montantMax;
	}

	public void setMontantMax(double montantMax) {
		this.montantMax = montantMax;
	}

	public double getFrais() {
		return frais;
	}

	public void setFrais(double frais) {
		this.frais = frais;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
}
