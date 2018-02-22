package org.mobibank.payme.backend.data.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.mobibank.payme.backend.numerate.CommissionType;
import org.mobibank.payme.backend.numerate.Context;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.numerate.SchemaCompte;
import org.mobibank.payme.backend.numerate.ServiceParams;

@Entity
@Table( name= Services.TABLE_NAME )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQuery(name="Services.findAll", query = "SELECT s FROM Services s")
public class Services extends AbstractEntity {
	
	public static final String TABLE_NAME = "services";

	@NotNull
	private String name;

	@Enumerated
	private Module module;

	//Profile autorisé à utiliser le service
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> autoriseProfile;

	//Profile autorisé à beneficier de ce service
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> profileBeneficiaire;

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<ServiceParams, String> params;

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<CommissionType, Integer> commissions;

	//Liste exhaustive des comptes impacté lors des opération liées à ce service
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<SchemaCompte> comptes;

	//Liste exhaustive des elements du context
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Context> context;

	//Sequence d'operation en fonction du scheme comptable
	@OneToMany(mappedBy = "service")
	private Set<Sequences> sequences;

	@OneToMany
	private Set<Grille> grille;

	public Services() {
		super();
	}

	public Services(String name, Module module, Map<ServiceParams, String> params, Map<CommissionType, Integer> commissions,
			Pays pays, Boolean fee) {
		super();
		this.name = name.toUpperCase();
		this.module = module;
		this.params = params;
		this.commissions = commissions;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Map<ServiceParams, String> getParams() {
		return params;
	}
	
	public Set<ServiceParams> getParam(){
		Set<ServiceParams> sp = new HashSet<>();
		if(this.params != null)
			params.forEach((k, v) -> sp.add(k));
		return sp;
	}

	public void setParams(Map<ServiceParams, String> params) {
		this.params = params;
	}
	
	public void setParams(Set<ServiceParams> param){
		if(param != null){
			params = new HashMap<>();
			param.forEach(v -> params.put(v, ""));
		}
			
	}

	public Map<CommissionType, Integer> getCommissions() {
		return commissions;
	}
	
	public Set<CommissionType> getCommission(){
		Set<CommissionType> c = new HashSet<>();
		if(commissions != null)
			commissions.forEach((k, v) -> c.add(k));
		return c;
	}

	public void setCommissions(Map<CommissionType, Integer> commissions) {
		this.commissions = commissions;
	}
	
	public void setCommissions(Set<CommissionType> commission){
		if(commission != null){
			commissions = new HashMap<>();
			commission.forEach(v -> commissions.put(v, 0));
		}
	}

	public Set<Sequences> getSequences() {
		return sequences;
	}

	public void setSequences(Set<Sequences> sequences) {
		this.sequences = sequences;
	}

	public Set<SchemaCompte> getComptes() {
		return comptes;
	}

	public void setComptes(Set<SchemaCompte> comptes) {
		this.comptes = comptes;
	}

	public Set<Context> getContext() {
		return context;
	}

	public void setContext(Set<Context> context) {
		this.context = context;
	}

	public Set<Grille> getGrille() {
		return grille;
	}

	public void setGrille(Set<Grille> grille) {
		this.grille = grille;
	}

	public Set<String> getAutoriseProfile() {
		return autoriseProfile;
	}

	public void setAutoriseProfile(Set<String> autoriseProfile) {
		this.autoriseProfile = autoriseProfile;
	}

	public Set<String> getProfileBeneficiaire() {
		return profileBeneficiaire;
	}

	public void setProfileBeneficiaire(Set<String> profileBeneficiaire) {
		this.profileBeneficiaire = profileBeneficiaire;
	}
}
