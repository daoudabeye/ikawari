package org.mobibank.payme.backend.data.entity;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 
 * @author BEYE
 * 
 * BANQUE, PROVIDER, PAYEUR, AGENCE, POINT_SERVICE
 *
 */
@Entity
@DiscriminatorValue(value = "STRUCTURE")
@NamedQuery(name = "Structure.findAll", query = "SELECT s FROM Structure s")
public class Structure extends Utilisateur {
	
	private String designation;
	
	//Une structure peut avoir plusieurs agents
	//mapping 1:n Agence -> agents
	@OneToMany(mappedBy="structure")
	private Set<Agent> agents;

	//bi-directional many-to-one association to Entite
	@OneToMany(mappedBy = "structure", fetch = FetchType.EAGER)
	private Set<Horaires> horaires;

	//si cette structure represente la banque principale d'un pays
	@OneToOne(mappedBy="mainBanque")
	private Pays banqueFor;

	//si cette structure represente le provider d'un pays
	@OneToOne(mappedBy="localProvider")
	private Pays providerFor;

	//Un ou master à plusieurs points de services ou agences
	//Mapping n:1 Structure(Point service) - master
	@ManyToOne
	@JoinColumn
	private Agent master;

	public Structure() {
		super();
		this.setPassword("paymesys");
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.designation;
	}

	public Set<Agent> getAgents() {
		return agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

	public Set<Horaires> getHoraires() {
		return horaires;
	}

	public void setHoraires(Set<Horaires> horaires) {
		this.horaires = horaires;
	}

	public Pays getBanqueFor() {
		return banqueFor;
	}

	public void setBanqueFor(Pays banqueFor) {
		this.banqueFor = banqueFor;
	}

	public Pays getProviderFor() {
		return providerFor;
	}

	public void setProviderFor(Pays providerFor) {
		this.providerFor = providerFor;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Agent getMaster() {
		return master;
	}

	public void setMaster(Agent master) {
		this.master = master;
	}
}
