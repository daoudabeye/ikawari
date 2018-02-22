package org.mobibank.payme.backend.operations;

import java.util.Set;

import org.mobibank.payme.backend.data.entity.Compte;
import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.data.entity.Sequences;
import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.backend.data.entity.Transaction;
import org.mobibank.payme.backend.data.entity.Validation;
import org.mobibank.payme.backend.numerate.ValidationLevel;
import org.mobibank.payme.backend.numerate.ValidationStatut;
import org.mobibank.payme.backend.numerate.ValidationType;
import org.springframework.util.Assert;

/**
 * Gestion du cycle de vie d'un opération
 * initialisation - execution - confirmation
 * 
 * @author BEYE
 *
 */
public abstract class Operation extends AbstractOperation {


	protected Compte compteAttenteRetrait;
	protected MyPayme myPaymeService;

	Double addition = 0.0;
	int i = 1;
	
	public Operation(MyPayme paymeService) {
		this.service = (Services) paymeService;
		this.myPaymeService = paymeService;
	}
	
	public void init(){

		agent = numeroAgent.isEmpty() ? operationService.findByUsername(agentUsername): 
			operationService.findUtilisateur(numeroAgent);
		Assert.notNull(agent, "Identifiant agent incorrect");

		entite = agent.getEntite();
		Assert.notNull(entite, "Vous devez etre rattaché à une entité pour effectuer des opérations.");
		
		pays = entite.getPays();
		prefixe = entite.getPrefixe();

		if(numeroClient.isEmpty()){
			if(!numeroClient.startsWith(pays.getIndicatif()))
				numeroClient = pays.getIndicatif() + numeroClient;
			client = operationService.findUtilisateur(numeroClient);
			Assert.notNull(client, "Le numero du client est incorrect !");
		}

		reference = "op";

		if(myPaymeService.hasFee())
			frais = getFrais();

		initSchemaCompte();
		initContext();

		serviceParamCheck();

		note = "Module" + " effectuée avec succès <br />"
				+ " Montant : "+montant+" <br />"
				+ " Frais : "+frais+" <br />"
				+ " agent : "+agent+" <br />"
				+ " Bénéficiaire : "+client+" <br />"
				+ " Réference de la transaction : "+ reference + "<br />"
				+ " Date de valeur : "+ dateValeur + "<br />"
				+ " Statut : "+ statut + "<br />";
	}

	@Override
	public Double getFrais() {
		// TODO Auto-generated method stub
		return operationService.getFrais(service, montant, pays);
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
				transaction.setSequence(i);
				operationService.saveTransaction(transaction);
				addition = 0.0;
				i++;
			});
		}

		after();
	}

	/**
	 * Cette methode est appelé juste après l'execution de l'operation.
	 */
	public void after() {
		if(validationStatut.equals(ValidationStatut.INSTANCE)){

			String description = "module" + ", montant : "+montant;
			Validation validationRequest = new Validation(ValidationType.OPERATION, null, null,
					null, agent.getId(), null, ValidationLevel.MEDIUM, ValidationStatut.INSTANCE, description);
			validationRequest.setEntite(entite);
			validationRequest.setReference(reference);
			operationService.saveValidationRequest(validationRequest);
		}

	}

	public void annuler(String refernce) {

	}
}
