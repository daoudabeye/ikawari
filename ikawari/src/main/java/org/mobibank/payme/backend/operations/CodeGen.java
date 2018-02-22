package org.mobibank.payme.backend.operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.data.entity.Ticket;
import org.mobibank.payme.backend.numerate.StatutCode;
import org.mobibank.payme.backend.services.OperationService;

public class CodeGen extends Operation {
	
	Boolean self;
	String beneficiaire;

	public CodeGen(OperationService operationService, MyPayme services, String numero, Double montant,
			Boolean self, String benef) {
		super(services);
		
		this.operationService = operationService;
		this.numeroClient = numero;
		this.montant = montant;
		this.self = self;
		this.beneficiaire = benef;
		statut = StatutCode.EFFECTUER;
		
	}
	
	public void after() {
		BigDecimal valeur = BigDecimal.valueOf(montant);
		String token = entite.getPrefixe();
		
		Ticket ticket = new Ticket(token, valeur, LocalDateTime.now().plusHours(24),
				self, beneficiaire);
//		ticket.setUtilisateur(agent);
		operationService.save(ticket);
		this.note = this.note +" Token : "+token + "<br />";
	}

}
