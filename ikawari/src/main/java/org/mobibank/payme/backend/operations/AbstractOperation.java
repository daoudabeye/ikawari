package org.mobibank.payme.backend.operations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mobibank.payme.backend.data.entity.Agent;
import org.mobibank.payme.backend.data.entity.Compte;
import org.mobibank.payme.backend.data.entity.Entite;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.numerate.CommissionType;
import org.mobibank.payme.backend.numerate.Context;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.numerate.SchemaCompte;
import org.mobibank.payme.backend.numerate.StatutCode;
import org.mobibank.payme.backend.numerate.TypeCompte;
import org.mobibank.payme.backend.numerate.ValidationStatut;
import org.mobibank.payme.backend.services.OperationService;
import org.springframework.util.Assert;

/**
 * 
 * @author BEYE
 *
 */

public abstract class AbstractOperation implements Command {

	protected Pays pays;
	protected Entite entite;
	protected Module module;
	protected String prefixe;
	protected Services service;
	protected Double montant, frais = 0.0, taxe = 0.0;

	protected Map<Context, Double> contextMap = new HashMap<>();

	protected OperationService operationService;
	protected LocalDate dateValeur = LocalDate.now();

	protected Compte comptePrincipalClient, comptePrincipalAgent, compteFrais, compteAttenteRetrait, compteRetenusMarketing, comptePrincipalPayeur,
	compteRevenusAgent, compteRevenusMaster, compteRevenusProvider, comptePrincipalMaster, compteRevenusBanque, compteTaxe, comptePivot;
	protected Utilisateur client;
	protected Utilisateur agent, master;
	protected Utilisateur banque, provider, payeur;

	protected String reference, note, feeNote;

	protected StatutCode statut = StatutCode.EFFECTUER;
	protected ValidationStatut validationStatut = ValidationStatut.VALIDER;
	protected String numeroAgent = "", agentUsername = "", numeroClient = "";


	/**
	 * Verrifier les parametres de l'utilisateur
	 * @param profile
	 * 		le profile de l'utilisateur
	 */
	void profileParamCheck(String profile){

	}

	/**
	 * Verrifie les parametres du service.
	 */
	void serviceParamCheck(){
		this.service.getParams().forEach((k, v) -> {
			switch (k) {
			case LIMTT:
				limit(v);
				break;
			case SEUIL_VALIDATION:
				seuil_validation(v);
				break;

			default:
				break;
			}
		});
	}

	/**
	 * Initialisation des comptes selon le schema comptable
	 */
	public void initSchemaCompte(){
		Set<SchemaCompte> schemaCompte = service.getComptes();
		if(schemaCompte != null){
			schemaCompte.forEach(c ->{
				Agent ag;

				switch (c) {
				case ATTENTE:
					banque = banque == null ? (Utilisateur)pays.getMainBanque(): banque;
					compteAttenteRetrait = operationService.findWallet(banque, TypeCompte.ATTENTE);
					Assert.notNull(compteAttenteRetrait, "Compte d'attente retrait indisponible.");
					break;
				case P_AGENT :
					comptePrincipalAgent = operationService.findWallet(agent, TypeCompte.PRINCIPALE);
					Assert.notNull(comptePrincipalAgent, "Impossible d'éffectuer cette operation, l'agent ne dispose pas d'un compte de principal.");
					break;
				case C_AGENT:
					compteRevenusAgent = operationService.findWallet(agent, TypeCompte.COMMISSION);
					Assert.notNull(compteRevenusAgent, "Impossible d'éffectuer cette operation, l'agent ne dispose pas d'un compte de revenus.");
					break;
				case C_BANQUE:
					banque = banque == null ? (Utilisateur)pays.getMainBanque(): banque;
					compteRevenusBanque = operationService.findWallet(banque, TypeCompte.COMMISSION);
					Assert.notNull(compteRevenusBanque, "Compte d'attente retrait indisponible.");
					break;
				case TAXE:
					banque = banque == null ? (Utilisateur)pays.getMainBanque(): banque;
					compteTaxe = operationService.findWallet(banque, TypeCompte.TAXES);
					Assert.notNull(compteTaxe, "Compte taxe indisponible.");
					break;
				case P_MASTER :
					//On recherche l'agent
					ag = operationService.findAgent(agentUsername, numeroAgent);//recherche par username
					if(ag != null) master = ag.getMaster();
					//si le compte agent est null c'est dire que l'opération est effectuée par un profile superieur 
					//c'est le compte provider qui est fournis en tant que master
					if(master == null) master = (Utilisateur)pays.getLocalProvider();
					comptePrincipalMaster = operationService.findWallet(master, TypeCompte.PRINCIPALE);
					Assert.notNull(comptePrincipalMaster, "Impossible d'éffectuer cette operation, compte principal master indisponible.");
					break;
				case C_MASTER:
					ag = operationService.findAgent(agentUsername, numeroAgent);//recherche par username ou téléphone
					if(ag != null) master = ag.getMaster();
					//si le compte agent est null c'est dire que l'opération est effectuée par un profile superieur 
					//c'est le compte provider qui est fournis en tant que master
					if(master == null) master = (Utilisateur)pays.getLocalProvider();
					compteRevenusMaster = operationService.findWallet(master, TypeCompte.COMMISSION);
					Assert.notNull(compteRevenusMaster, "Impossible d'éffectuer cette operation, compte revenus master indisponible.");
					break;
				case C_PROVIDER:
					provider = provider == null ? (Utilisateur)pays.getLocalProvider() : provider;
					compteRevenusProvider = operationService.findWallet(provider, TypeCompte.COMMISSION);
					Assert.notNull(compteRevenusProvider, "Impossible d'éffectuer cette operation, compte revenus provider indisponible.");
					break;
				case P_BENEF :
					client = operationService.findByNumber(numeroClient);
					comptePrincipalClient = operationService.findWallet(client, TypeCompte.PRINCIPALE);
					Assert.notNull(comptePrincipalClient, "Impossible d'éffectuer cette operation, compte principal BENEFICIARE indisponible.");
					break;
				case MARKETING:
					provider = provider == null ? (Utilisateur)pays.getLocalProvider() : provider;
					compteRetenusMarketing = operationService.findWallet(provider, TypeCompte.MARKTING);
					Assert.notNull(compteRetenusMarketing, "Impossible d'éffectuer cette operation, compte revenus distributeur indisponible.");
					break;
				case P_PAYER:
					Assert.notNull(payeur, "Etablissement selectionné incorrect.");
					comptePrincipalPayeur = operationService.findWallet(payeur, TypeCompte.PRINCIPALE);
					Assert.notNull(comptePrincipalPayeur, "Etablissement selectionné incorrect.");
					break;
				case PIVOT:
					banque = banque == null ? (Utilisateur)pays.getMainBanque(): banque;
//					Assert.isNull(banque.getTelephone().equals(numeroClient), "Le numero saisi n'est pas celui de la banque principale");
					comptePivot = operationService.findWallet(banque, TypeCompte.PIVOT);
					Assert.notNull(comptePivot, "Compte pivot banque incorrect.");
					break;

				default:
					Assert.isTrue(false, "Compte non défini :" + c);
					System.err.println("Compte non défini :" + c);
					break;
				}
			});
		}
	}

	/**
	 * Définition des valeurs du contexte selon le schema comptable
	 */
	public void initContext(){
		Set<Context> context = service.getContext();
		if(context != null){
			context.forEach(c -> {
				switch (c) {
				case PRINCIPAL:
					contextMap.put(c, montant);
					break;
				case FRAIS:
					Assert.notNull(frais, "Aucun frais défini pour ce service.");
					contextMap.put(c, frais);
				case TAXE :
					taxe = pays.getTaf() > 0 ? (frais * pays.getTaf()) /100 : 0;
					contextMap.put(c, taxe);
					break;
				case C_DIST :
					Assert.notNull(service.getCommissions(), "Vous devez définir les commissions dans le service :" + c);
					Integer quote = service.getCommissions().get(CommissionType.DISTRIBUTEUR).intValue();
					Assert.notNull(quote, "La commission '"+c+"' doit etre défini dans le service.");
					Double commission = quote > 0 ? (frais * quote) / 100 : 0;
					contextMap.put(c, commission);
					break;
				case C_DIST_R :
					Assert.notNull(service.getCommissions(), "Vous devez définir les commissions dans le service :" + c);
					Integer quoteCDR = service.getCommissions().get(CommissionType.DISTRIBUTEUR_R).intValue();
					Assert.notNull(quoteCDR, "La commission '"+c+"' doit etre défini dans le service.");
					Double commissionDR = quoteCDR > 0 ? (frais * quoteCDR) / 100 : 0;
					contextMap.put(c, commissionDR);
					break;
				case C_MASTER :
					Assert.notNull(service.getCommissions(), "Vous devez définir les commissions dans le service :" + c);
					Integer quoteM = service.getCommissions().get(CommissionType.MASTER).intValue();
					Assert.notNull(quoteM, "La commission '"+c+"' doit etre défini dans le service.");
					Double commissionM = quoteM > 0 ? (frais * quoteM) / 100 : 0;
					contextMap.put(c, commissionM);
					break;
				case C_PRO :
					Assert.notNull(service.getCommissions(), "Vous devez définir les commissions dans le service :" + c);
					Integer quoteP = service.getCommissions().get(CommissionType.PROVIDER).intValue();
					Assert.notNull(quoteP, "La commission '"+c+"' doit etre défini dans le service.");
					Double commissionP = quoteP > 0 ? (frais * quoteP) / 100 : 0;
					contextMap.put(c, commissionP);
					break;
				case R_MARKETING:
					Assert.notNull(service.getCommissions(), "Vous devez définir les commissions dans le service :" + c);
					Integer mrk = service.getCommissions().get(CommissionType.MARKETING).intValue();
					Assert.notNull(mrk, "La commission '"+c+"' doit etre défini dans le service.");
					Double commissionMrk = mrk > 0 ? (frais * mrk) / 100 : 0;
					contextMap.put(c, commissionMrk);
					break;
				case C_BANK:
					Assert.notNull(service.getCommissions(), "Vous devez définir les commissions dans le service :" + c);
					Integer bk = service.getCommissions().get(CommissionType.MARKETING).intValue();
					Assert.notNull(bk, "La commission '"+c+"' doit etre défini dans le service.");
					Double commissionbk = bk > 0 ? (frais * bk) / 100 : 0;
					contextMap.put(c, commissionbk);
					break;

				default:
					Assert.isTrue(false, "Element du contexte non défini :" + c);
					break;
				}
			});
		}
	}

	public Double getFrais() {
		return 0.0;
	}

	/**
	 * Renvoi le compte réquis pour chaque type de compet défini dans le schema
	 * comptable.
	 * @param k le Type de commission {@link CommissionType}
	 * @return le Compte
	 */
	//	Compte getCompte(CommissionType k) {
	//		switch (k) {
	//		case AGENT:
	//		case DISTRIBUTEUR:
	//			return compteRevenusAgent;
	//		case BANQUE:
	//			return compteRevenusBanque;
	//		case MARKETING:
	//			return compteRetenusMarketing;
	//		case MASTER:
	//			return compteRevenusMaster;
	//		case PROVIDER:
	//			return compteRevenusProvider;
	//		default:
	//			Assert.isTrue(false, "Commissions inconnue: "+k);
	//			break;
	//		}
	//		return null;
	//	}

	Compte getCompte(SchemaCompte k) {
		switch (k) {
		case ATTENTE:
			return compteAttenteRetrait;
		case C_BANQUE:
			return compteRevenusBanque;
		case C_AGENT:
			return compteRevenusAgent;
		case C_MASTER:
			return compteRevenusMaster;
		case C_PROVIDER:
			return compteRevenusProvider;
		case MARKETING:
			return compteRetenusMarketing;
		case P_BENEF:
			return comptePrincipalClient;
		case P_AGENT:
			return comptePrincipalAgent;
		case P_MASTER:
			return comptePrincipalMaster;
		case TAXE:
			return compteTaxe;
		case P_PAYER:
			return comptePrincipalPayeur;
		case PIVOT:
			return comptePivot;
		default:
			Assert.isTrue(false, "Commissions inconnue: "+k);
			break;
		}
		return null;
	}

	void solde_max(Compte compte, String soldeMax){
		if(!soldeMax.isEmpty() && !soldeMax.equals("0")){
			Double max = Double.valueOf(soldeMax);
			Assert.isTrue(compte.getSolde().compareTo(BigDecimal.valueOf(max)) <= 0 , "Impossible d'éffectuer cette opération, "
					+ "le solde max est de :"+max+" "+ pays.getDevise() +".");
		}
	}

	void solde_min(Compte compte, String soldeMin){
		if(!soldeMin.isEmpty()){
			Double min = Double.valueOf(soldeMin);
			Assert.isTrue(compte.getSolde().compareTo(BigDecimal.valueOf(min)) >= 0 , "Impossible d'éffectuer cette opération, "
					+ "le solde min est de :"+min+" "+ pays.getDevise() +".");
		}
	}

	void seuil_validation(String seuil){
		if(!seuil.isEmpty() && !seuil.equals("0")){
			Double valeur = Double.valueOf(seuil);
			if(this.montant >= valeur) {
				validationStatut = ValidationStatut.INSTANCE;
				this.statut = StatutCode.INSTANCE;
			}
		}
	}

	void limit(String limit){
		if(!limit.isEmpty() && !limit.equals("0")){
			Double valeur = Double.valueOf(limit);
			Assert.isTrue(this.montant <= valeur, "Le montant limit de cette opération est fixé à "+limit+" "+ pays.getDevise() +".");
		}
	}

	public String getNote() {
		return note;
	}
}
