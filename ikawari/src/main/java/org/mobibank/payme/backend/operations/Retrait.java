package org.mobibank.payme.backend.operations;

import java.time.LocalDateTime;

import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.data.entity.Ticket;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.numerate.StatutCode;
import org.mobibank.payme.backend.numerate.StatutTicket;
import org.mobibank.payme.backend.services.OperationService;
import org.springframework.util.Assert;

public class Retrait extends Operation {

	Ticket ticket;

	public Retrait(OperationService operationService, MyPayme services, String username, String token) {
		super(services);
		
		this.operationService =  operationService;
		
		this.agentUsername = username;
		module = Module.RETRAIT;
		statut = StatutCode.EFFECTUER;
		this.ticket = operationService.findTicket(token);
		Assert.notNull(ticket, "Aucun ticket valide pour ce code !");
		Assert.isTrue(ticket.getExpiration().compareTo(LocalDateTime.now()) > 0, "Le ticket de retrait n'est plus valide!");
		Assert.isTrue(ticket.getStatut().equals(StatutTicket.VALIDE), "Ticket invalide ou annuler.");
		this.montant = ticket.getValeur().doubleValue();
	}
	
	@Override
	public void after() {
		// TODO Auto-generated method stub
		ticket.setStatut(StatutTicket.ENCAISSER);
		operationService.save(ticket);
	}

}
