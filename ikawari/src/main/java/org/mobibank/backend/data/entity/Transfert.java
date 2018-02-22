package org.mobibank.backend.data.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.mobibank.backend.numerate.Devise;
import org.mobibank.backend.numerate.StatutTransfert;
import org.mobibank.backend.numerate.Taxe;
import org.mobibank.backend.numerate.TcpStatut;
import org.mobibank.backend.numerate.TypeTransfert;

@Entity
@Table( name = Transfert.TABLE_NAME )
@NamedQuery(name = "Transfert.findAll", query = "SELECT t FROM Transfert t")
public class Transfert extends AbstractEntity {
	
	public static final String TABLE_NAME = "transfert";

	@Column(unique = true)
	private Long mtcn;

	private String folio;

	private Double montant;

	private Double frais;

	private Double taxe;

	//commissions pour le distributeur au paiement
	private Double commission;

	@Column(name = "type_taxe")
	private Taxe typeTaxe;

	@ManyToOne
	@JoinColumn(name = "origine")
	private Pays origine;

	@ManyToOne
	@JoinColumn(name = "destination")
	private Pays destination;

	@OneToOne
	@JoinColumn(name = "expediteur")
	private TransfertUser sender;

	@OneToOne
	@JoinColumn(name = "beneficiaire")
	private TransfertUser beneficiaire;

	@Enumerated
	@Column(name = "type_transfert")
	private TypeTransfert typeTransfert;

	//Devise d'envoi, le paiement est esffectué dans la devise locale
	@Enumerated
	private Devise devise;

	@Enumerated
	private StatutTransfert statut;

	//Api partenaire
	@Enumerated
	@Column(name = "tcp_statut")
	private TcpStatut tcpStatut;

	//Reference de la validation
	@Column(name = "validation_code")
	private Long validationCode;

	@Column(name = "date_envoi")
	private LocalDate dateEnvoi;

	@Column(name = "heure_envoi")
	private LocalTime heureEnvoi;

	@Column(name = "date_paiement")
	private LocalDateTime datePaiement;

	//Agent ayant payer la transaction
	@ManyToOne
	@JoinColumn(name = "agent_expediteur")
	private Agent agentExpediteur;

	//Agent ayant payer la transaction
	@ManyToOne
	@JoinColumn(name = "agent_payeur")
	private Agent agentPayeur;
	
	@ManyToOne
	@JoinColumn
	private Structure pointService;

	//Historique des modifications
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderColumn(name = "id")
	private List<History> history;

	public Transfert() {
		super();
	}

	public Transfert(Long mtcn, String folio, Double montant, Double frais, Double taxe, Taxe typeTaxe,
			TransfertUser sender, TransfertUser beneficiaire, TypeTransfert typeTransfert, StatutTransfert statut,
			TcpStatut tcpStatut, Long validationCode) {
		super();
		this.mtcn = mtcn;
		this.folio = folio;
		this.montant = montant;
		this.frais = frais;
		this.taxe = taxe;
		this.typeTaxe = typeTaxe;
		this.sender = sender;
		this.beneficiaire = beneficiaire;
		this.typeTransfert = typeTransfert;
		this.statut = statut;
		this.tcpStatut = tcpStatut;
		this.validationCode = validationCode;
		this.dateEnvoi = LocalDate.now();
	}

	public Transfert( Double montant, TransfertUser sender, TransfertUser beneficiaire, TypeTransfert typeTransfert) {
		super();
		this.montant = montant;
		this.sender = sender;
		this.beneficiaire = beneficiaire;
		this.typeTransfert = typeTransfert;
		this.statut = StatutTransfert.TRANSMIS;
		this.tcpStatut = TcpStatut.SEN;
		this.validationCode = Long.valueOf(0);
		this.dateEnvoi = LocalDate.now();
	}

	public Long getMtcn() {
		return mtcn;
	}

	public void setMtcn(Long mtcn) {
		this.mtcn = mtcn;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Double getFrais() {
		return frais;
	}

	public void setFrais(Double frais) {
		this.frais = frais;
	}

	public Double getTaxe() {
		return taxe;
	}

	public void setTaxe(Double taxe) {
		this.taxe = taxe;
	}

	public Taxe getTypeTaxe() {
		return typeTaxe;
	}

	public void setTypeTaxe(Taxe typeTaxe) {
		this.typeTaxe = typeTaxe;
	}

	public TransfertUser getSender() {
		return sender;
	}

	public void setSender(TransfertUser sender) {
		this.sender = sender;
	}

	public TransfertUser getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(TransfertUser beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public TypeTransfert getTypeTransfert() {
		return typeTransfert;
	}

	public void setTypeTransfert(TypeTransfert typeTransfert) {
		this.typeTransfert = typeTransfert;
	}

	public StatutTransfert getStatut() {
		return statut;
	}

	public void setStatut(StatutTransfert statut) {
		this.statut = statut;
	}

	public TcpStatut getTcpStatut() {
		return tcpStatut;
	}

	public void setTcpStatut(TcpStatut tcpStatut) {
		this.tcpStatut = tcpStatut;
	}

	public Long getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(Long validationCode) {
		this.validationCode = validationCode;
	}

	public LocalDate getDateEnvoi() {
		return dateEnvoi;
	}

	public void setDateEnvoi(LocalDate dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public Devise getDevise() {
		if(devise == null) return Devise.CFA;
		return devise;
	}

	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Pays getOrigine() {
		return origine;
	}

	public void setOrigine(Pays origine) {
		this.origine = origine;
	}

	public Pays getDestination() {
		return destination;
	}

	public void setDestination(Pays destination) {
		this.destination = destination;
	}

	public LocalTime getHeureEnvoi() {
		return heureEnvoi;
	}

	public void setHeureEnvoi(LocalTime heureEnvoi) {
		this.heureEnvoi = heureEnvoi;
	}
	public LocalDateTime getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(LocalDateTime datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Agent getAgentExpediteur() {
		return agentExpediteur;
	}

	public void setAgentExpediteur(Agent agentExpediteur) {
		this.agentExpediteur = agentExpediteur;
	}

	public Agent getAgentPayeur() {
		return agentPayeur;
	}

	public void setAgentPayeur(Agent agentPayeur) {
		this.agentPayeur = agentPayeur;
	}
	
	public Structure getPointService() {
		return pointService;
	}

	public void setPointService(Structure pointService) {
		this.pointService = pointService;
	}

	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

}
