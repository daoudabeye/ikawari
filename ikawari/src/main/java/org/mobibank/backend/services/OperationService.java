package org.mobibank.backend.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.mobibank.BeanLocator;
import org.mobibank.backend.TransactionRepository;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.data.entity.GrilleWallet;
import org.mobibank.backend.data.entity.MyPayme;
import org.mobibank.backend.data.entity.Ordres;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Sequences;
import org.mobibank.backend.data.entity.Services;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.data.entity.Ticket;
import org.mobibank.backend.data.entity.Transaction;
import org.mobibank.backend.data.entity.Transfert;
import org.mobibank.backend.data.entity.TransfertUser;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.data.entity.Validation;
import org.mobibank.backend.numerate.Devise;
import org.mobibank.backend.numerate.StatutCode;
import org.mobibank.backend.numerate.TypeCompte;
import org.mobibank.backend.numerate.TypeTransfert;
import org.mobibank.backend.operations.Envois;
import org.mobibank.backend.operations.Operation;
import org.mobibank.backend.operations.SimpleOperation;
import org.mobibank.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class OperationService {
	
	@Autowired StructureService structureService;
	@Autowired CompteService compteService;
	@Autowired UtilisateurService utilisateurService;
	@Autowired AgentService agentService;
	@Autowired TransactionRepository transactionRepository;
	@Autowired TicketService ticketService;
	@Autowired TransfertService transfertService;

	public Compte findWallet(Utilisateur agent, TypeCompte principale) {
		return compteService.getRepository().findFirst1ByUtilisateurAndType(agent, principale);
	}

	public Agent findAgent(String username, String telephone) {
		return agentService.getRepository().findFirst1ByUsernameOrTelephone(username, telephone);
	}

	public Double getFrais(Services service, Double montant, Pays pays) {
		GrilleWalletService grilleWalletService = BeanLocator.find(GrilleWalletService.class);
		GrilleWallet grille = grilleWalletService.getRepository().findByTypeOperation(service, montant, pays);
		Assert.notNull(grille, "Aucun frais correspondant, operation impossible.");
		Double frais = grille.getFrais();
		return frais;
	}
	
	public Double getFrais(Pays paysSrc, Pays paysDst, Double montant) {
		CorridoreService corridoreService = BeanLocator.find(CorridoreService.class);
		return corridoreService.computeFrais(paysSrc, paysDst, montant);
	}

	public Set<Sequences> findSequence(Services service) {
		SequenceService sequenceService = BeanLocator.find(SequenceService.class);
		return sequenceService.getRepository().findByService(service);
	}

	public Transaction saveTransaction(Transaction transaction) {
		if(transaction.getStatut().equals(StatutCode.EFFECTUER))
			compteService.updateSolde(transaction);
		return transactionRepository.save(transaction);
	}

	public void saveValidationRequest(Validation validationRequest) {
		BeanLocator.find(ValidationService.class).save(validationRequest);
	}

	public Ticket save(Ticket ticket) {
		return ticketService.save(ticket);
	}

	public Ticket findTicket(String token) {
		return ticketService.getRepository().findOneByToken(token);
	}

	public Transfert findTransfert(Long mtcn) {
		return transfertService.getRepository().findByMtcn(mtcn);
	}

	public Transfert save(Transfert transfert) {
		TransfertUserService transfertUserService = BeanLocator.find(TransfertUserService.class);
		
		TransfertUser benef = transfert.getBeneficiaire();
		Assert.notNull(benef, "Veuillez spécifiez le beneficiare");
		benef = transfertUserService.save(benef);

		TransfertUser sender = transfert.getSender();
		Assert.notNull(sender, "Veuillez spécifiez l'expediteur");
		sender = transfertUserService.save(sender);

		transfert.setBeneficiaire(benef);
		transfert.setSender(sender);
		return transfertService.save(transfert);
	}

	/**
	 * Recherche par numero ou username
	 * @param numeroAgent
	 * @return
	 */
	public Utilisateur findUtilisateur(String key) {
		return utilisateurService.find(key);
	}
	
	public Utilisateur findByNumber(String number) {
		return utilisateurService.findByNumber(number);
	}
	
	public Utilisateur findByUsername(String username) {
		return utilisateurService.findByUsername(username);
	}
	
	/**
	 * Opération sur wallet
	 * @param service
	 * @param beneficiaire
	 * @param montant
	 * @return
	 */
	public Operation execute(MyPayme service, String beneficiaire, Double montant) {
		SimpleOperation depot = new SimpleOperation(this, service, beneficiaire, montant);
		depot.execute();
		return depot;
	}
	
	@Transactional
	public Envois envois(Ordres service, TypeTransfert transfertType, Double montant,
			Pays paysDst, TransfertUser beneficiaire, TransfertUser expediteur, Agent payeur, Structure pointService,
			Devise devise){
		String agentusername = SecurityUtils.getUsername();
		Envois envois = new Envois(this, service, transfertType, montant, paysDst,
				agentusername, beneficiaire, expediteur, payeur, pointService, devise);
		
		envois.execute();

		return envois;
	}
	

	public Collection<Transaction> findCurrentUserHistorique(TypeCompte type) {
		Utilisateur current = utilisateurService.getCurrentUser();
		Compte compte = compteService.findOne(current, type);
		if(compte == null)
			return new HashSet<Transaction>();
		return transactionRepository.findByCompte(compte);
	}

	public Collection<Transaction> findHistorique(Compte compte) {
		// TODO Auto-generated method stub
		if(compte == null)
			return new HashSet<Transaction>();
		return transactionRepository.findByCompte(compte);
	}

}
