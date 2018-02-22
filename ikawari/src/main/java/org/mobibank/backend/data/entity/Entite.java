package org.mobibank.backend.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = Entite.TABLE_NAME )
@NamedQuery(name = "Entite.findAll", query = "SELECT e FROM Entite e")
public class Entite extends AbstractEntity {
	
	public static final String TABLE_NAME = "entite";

	@Column(unique = true)
	private String designation;

	private String type;
	
	private int niveau = 0;
	
	private String prefixe;

	//bi-directional many-to-one association to Entite
	@ManyToOne
	@JoinColumn(name="entite_superieur_id")
	private Entite parent;

	//bi-directional many-to-one association to Entite
	@OneToMany(mappedBy="parent", fetch = FetchType.EAGER)
	private List<Entite> sousEntites;

//	//bi-directional many-to-one association to Entite
//	@OneToMany(mappedBy = "entite", fetch = FetchType.EAGER)
//	private List<Horaires> horaires;

	//bi-directional many-to-one association to Entite
	@OneToMany(mappedBy = "entite")
	private List<Utilisateur> utilisateurs;

	//bi-directional many-to-one association to Pay
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	@NotNull
	private Pays pays;

	public Entite() {
		super();
	}

	public Entite(String designation, String type, Integer niveau) {
		super();
		this.designation = designation;
		this.type = type;
		this.niveau = niveau;
	}

	
	public Entite(String designation, String type, String adresse, String telephone, String zone, Integer niveau,
			String prefixe, Entite parent, Pays pays) {
		super();
		this.designation = designation;
		this.type = type;
		this.niveau = niveau;
		this.prefixe = prefixe;
		this.parent = parent;
		this.pays = pays;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.designation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public Entite getParent() {
		return parent;
	}

	public void setParent(Entite parent) {
		this.parent = parent;
	}

	public List<Entite> getSousEntites() {
		return sousEntites;
	}

	public void setSousEntites(List<Entite> sousEntites) {
		this.sousEntites = sousEntites;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}
	
	public String getPrefixe() {
		return prefixe;
	}

	public void setPrefixe(String prefixe) {
		this.prefixe = prefixe != null ? prefixe.toUpperCase() : prefixe;
	}

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
}
