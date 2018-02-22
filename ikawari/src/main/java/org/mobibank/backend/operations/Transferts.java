package org.mobibank.backend.operations;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;

import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Ordres;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Sequences;
import org.mobibank.backend.data.entity.Services;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.data.entity.Transaction;
import org.mobibank.backend.data.entity.Transfert;
import org.mobibank.backend.data.entity.TransfertUser;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.numerate.Devise;
import org.mobibank.backend.numerate.Module;
import org.mobibank.backend.numerate.StatutTransfert;
import org.mobibank.backend.numerate.Taxe;
import org.mobibank.backend.numerate.TcpStatut;
import org.mobibank.backend.numerate.TypeCompte;
import org.mobibank.backend.numerate.TypeTransfert;
import org.mobibank.backend.services.ServiceUtils;
import org.mobibank.security.otp.TimeBasedOneTimePasswordGenerator;
import org.springframework.util.Assert;

/**
 * 
 * @author BEYE
 * 
 * Cycle d'un transfert d'argent
 *
 */
public abstract class Transferts extends AbstractOperation {

	protected Ordres ordre;
	Transfert transfert;
	TransfertUser sender, beneficiary;
	TypeTransfert typeTransfert;
	Long mtcn;
	String folio;

	Pays destination;

	Devise devise;
	Double addition = 0.0;
	
	Structure pointService;
	
	private TimeBasedOneTimePasswordGenerator totp;
	
	public Transferts(Ordres odreService) {
		// TODO Auto-generated constructor stub
		this.service = (Services)odreService;
		this.ordre = odreService;
	}

	//Initialisation de l'opération
	public void init(){
		agent = operationService.findByUsername(agentUsername);
		Assert.notNull(agent, "agent username incorrect.");
		
		
		entite = agent.getEntite();
		Assert.notNull(entite, "Vous devez appartenir à une entité pour faire des opérations");

		pays = entite.getPays();
		Assert.notNull(pays, "Vous devez etre localisé dans une Pays pour faire des opérations");

		if(Module.ENVOIS.equals(module)){
			
			try {
				mtcn = Long.valueOf(generateOCN());
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(ordre.getNational()) 
				destination = pays;
			else
				Assert.notNull(destination, "Vous devez selectionner le pays de destination");
			
			beneficiary.setPays(destination + "");
			sender.setPays(pays + "");
			
			folio = entite.getPrefixe() + mtcn;
			transfert = new Transfert(mtcn, folio, montant, frais,
					taxe, Taxe.TAF, sender, beneficiary, typeTransfert, 
					StatutTransfert.TRANSMIS, TcpStatut.SEN, Long.valueOf(0));
			
			transfert.setDevise(devise == null ? Devise.CFA : devise);
			transfert.setAgentExpediteur((Agent) agent);
			transfert.setOrigine(pays);
			transfert.setDestination(destination);
			transfert.setPointService(pointService);
			
			payeur = payeur == null ? destination.getLocalProvider() : payeur;
			Assert.notNull(payeur, "Etablissement selectionné incorrect.");
			transfert.setAgentPayeur((Agent) payeur);
			
			comptePrincipalPayeur = operationService.findWallet(payeur, TypeCompte.PRINCIPALE);
			Assert.notNull(comptePrincipalPayeur, "Etablissement selectionné incorrect.");
		}else{
			transfert = operationService.findTransfert(mtcn);
			Assert.notNull(transfert, "Code mtcn incorrect");
			
			this.montant = transfert.getMontant();
			payeur = (Utilisateur) transfert.getAgentPayeur();
			Assert.notNull(payeur, "Provider Local incorrect");
			
			comptePrincipalPayeur = operationService.findWallet(payeur, TypeCompte.PRINCIPALE);
			Assert.notNull(comptePrincipalPayeur, "Etablissement selectionné incorrect.");
		}
		
		reference = ServiceUtils.randomString(10).toUpperCase();
		
		initSchemaCompte();
		initContext();
		
		frais = getFrais();
	}
	
	private int generateOCN() throws NoSuchAlgorithmException, InvalidKeyException {
		totp = new TimeBasedOneTimePasswordGenerator(30, TimeUnit.SECONDS, 8);
		KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
	    keyGenerator.init(512);

	    Key secretKey = keyGenerator.generateKey();
	    
	    Date now = new Date();
	    int ocn = totp.generateOneTimePassword(secretKey, now);

		return ocn;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		init();
		
		Set<Sequences> sequence = operationService.findSequence(service);
		if(sequence != null){
			sequence.forEach(s -> {
				
				Assert.notNull(s.getContext(), "Vous devez définir le context pour :" + s);
				s.getContext().forEach(c -> addition += contextMap.get(c).doubleValue() );
				Transaction transaction = new Transaction(reference, s.getSens(),
						service.getModule(), statut, addition, s.getDescription(),
						dateValeur, Long.valueOf(0), getCompte(s.getCompte()));
				operationService.saveTransaction(transaction);
				
				addition = 0.0;
			});
		}
		
		transfert.setHeureEnvoi(LocalTime.now());
		operationService.save(transfert);
		
		after();
	}
	
	@Override
	public Double getFrais() {
		return operationService.getFrais(pays, destination, montant);
	}

	public void after() {
	}

//	/**
//	 * Versement des commissions
//	 */
//	void generateCommission(){
//		service.getCommissions().forEach((k, v) -> {
//			Compte compte = getCompte(k);
//			if(v > 0 && compte != null){
//				String notec = k.name() +": "+v;
//				Double commission = (frais * v)/100;
//				Transaction transaction = new Transaction(reference, TypeTransaction.CREDIT, Module.COMMISSION,
//						statut, commission, notec, dateValeur,
//						validationStatut, Long.valueOf(0),
//						compte);
//				operationService.saveTransaction(transaction);
//			}
//		});
//	}

	public void annuler(){
		
	}

	public Long getMtcn() {
		return mtcn;
	}

	public String getFolio() {
		return folio;
	}
	
	public Transfert getTransfert(){
		return transfert;
	}
	
	public Utilisateur getAgent(){
		return agent;
	}
}
