package org.mobibank.payme.backend.data.entity;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mobibank.payme.backend.numerate.ValidationLevel;
import org.mobibank.payme.backend.numerate.ValidationStatut;
import org.mobibank.payme.backend.numerate.ValidationType;

/**
 * Back-end entity table classe pour la gestion des validations .
 */
@Entity
@Table(name = Validation.TABLE_NAME)
@NamedQuery(name = "Validation.findAll", query = "SELECT v FROM Validation v")
public class Validation extends AbstractEntity {

	public static final String TABLE_NAME = "validation";

	//type de validation
	@Enumerated
	private ValidationType type;

	//Id de l'occurence
	@Column(name = "instance_id")
	private Long instanceId;
	
	private String reference;

	//Liste de attributs <nom, valeur>
	@ElementCollection(fetch = FetchType.EAGER)
	Map<String, String> attribut;

	//Liste de attributs <nom, valeur>
	@ElementCollection(fetch = FetchType.EAGER)
	Map<String, String> oldAttribut;
	
	@Column(name = "creer_par")
	private Long creerPar;
	
	@Column(name = "valider_par")
	private Long validerPar;
	
	@Column(name="date_validation")
	private LocalDateTime dateValidation;
	
	@Enumerated
	@Column(name = "validation_level")
	private ValidationLevel validationLevel;
	
	@Enumerated
	@Column(name = "validation_statut")
	private ValidationStatut validationStatut;
	
	private String description;
	
	@ManyToOne
	@JoinColumn
	private Entite entite;
	
	public Validation(){
		super();
	}

	public Validation(ValidationType type, Long instanceId, Map<String, String> attribut,
			Map<String, String> oldAttribut, Long creerPar, Long validerPar,
			ValidationLevel validationLevel, ValidationStatut statut, String description) {
		super();
		this.type = type;
		this.instanceId = instanceId;
		this.attribut = attribut;
		this.oldAttribut = oldAttribut;
		this.creerPar = creerPar;
		this.validerPar = validerPar;
		this.dateValidation = null;
		this.validationLevel = validationLevel;
		this.validationStatut = statut;
		this.description = description;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public Map<String, String> getAttribut() {
		return attribut;
	}

	public void setAttribut(Map<String, String> attribut) {
		this.attribut = attribut;
	}

	public Map<String, String> getOldAttribut() {
		return oldAttribut;
	}

	public void setOldAttribut(Map<String, String> oldAttribut) {
		this.oldAttribut = oldAttribut;
	}

	public Long getCreerPar() {
		return creerPar;
	}

	public void setCreerPar(Long creerPar) {
		this.creerPar = creerPar;
	}

	public Long getValiderPar() {
		return validerPar;
	}

	public void setValiderPar(Long validerPar) {
		this.validerPar = validerPar;
	}

	public LocalDateTime getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(LocalDateTime dateValidation) {
		this.dateValidation = dateValidation;
	}

	public ValidationLevel getValidationLevel() {
		return validationLevel;
	}

	public void setValidationLevel(ValidationLevel validationLevel) {
		this.validationLevel = validationLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ValidationType getType() {
		return type;
	}

	public void setType(ValidationType type) {
		this.type = type;
	}

	public ValidationStatut getValidationStatut() {
		return validationStatut;
	}

	public void setValidationStatut(ValidationStatut validationStatut) {
		this.validationStatut = validationStatut;
	}

	public Entite getEntite() {
		return entite;
	}

	public void setEntite(Entite entite) {
		this.entite = entite;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
