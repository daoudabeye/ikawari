package org.mobibank.payme.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.mobibank.payme.backend.numerate.Devise;

@Entity
@Table( name = Pays.TABLE_NAME )
@NamedQuery( name="Pays.findAll", query = "SELECT p FROM Pays p")
public class Pays extends AbstractEntity {
	public static final String TABLE_NAME = "pays";

	@Column(name = "nom", unique = true)
	private String nom;

	@Column(name = "code_iso", unique = true)
	private String codeIso;
	
	private String indicatif;
	
	//@Min(value = 0)
	private int numberSize = 0;
	
	private String ipAdresse;

	private Boolean disponible;
	
	@Enumerated
	private Devise devise;
	
	//@Min(value = 0)
	private int taf = 0;

	@ManyToOne
	@JoinColumn
	private Zone zone;
	
	//bi-directional many-to-one association to Entite
//	@OneToMany(mappedBy="pays")
//	private List<Entite> entites;
//	
//	@OneToMany(mappedBy = "pays")
//	private List<Services> services;
//	
	@OneToOne
	@JoinColumn(name="localProvider")
	private Structure localProvider;
	
	@OneToOne
	@JoinColumn(name="mainBanque")
	private Structure mainBanque;

	public Pays() {
		super();
	}

	public Pays(String nom, String codeIso, String indicatif, Zone zone, Boolean disponible, 
			String ipAdresse, Integer taf, Devise devise) {
		super();
		this.nom = nom;
		this.codeIso = codeIso;
		this.zone = zone;
		this.disponible = disponible;
		this.ipAdresse = ipAdresse;
		this.taf = taf;
		this.indicatif = indicatif;
		this.devise = devise;
	}
	
	public Pays(String nom, String codeIso) {
		super();
		this.nom = nom;
		this.codeIso = codeIso;
	}
	
	public String toString(){
		return this.nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodeIso() {
		return codeIso;
	}

	public void setCodeIso(String codeIso) {
		this.codeIso = codeIso;
	}

	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}
//	public List<Entite> getEntites() {
//		return entites;
//	}
//	public void setEntites(List<Entite> entites) {
//		this.entites = entites;
//	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public String getIndicatif() {
		return indicatif;
	}
	public void setIndicatif(String indicatif) {
		this.indicatif = indicatif;
	}
//	public List<Services> getServices() {
//		return services;
//	}
//	public void setServices(List<Services> services) {
//		this.services = services;
//	}
	public int getTaf() {
		return taf;
	}
	public void setTaf(int taf) {
		this.taf = taf;
	}
	public String getIpAdresse() {
		return ipAdresse;
	}
	public void setIpAdresse(String ipAdresse) {
		this.ipAdresse = ipAdresse;
	}

	public Devise getDevise() {
		return devise;
	}
	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public int getNumberSize() {
		return numberSize;
	}

	public void setNumberSize(int numberSize) {
		this.numberSize = numberSize;
	}

	public Structure getLocalProvider() {
		return localProvider;
	}

	public void setLocalProvider(Structure localProvider) {
		this.localProvider = localProvider;
	}

	public Structure getMainBanque() {
		return mainBanque;
	}

	public void setMainBanque(Structure mainBanque) {
		this.mainBanque = mainBanque;
	}

}
