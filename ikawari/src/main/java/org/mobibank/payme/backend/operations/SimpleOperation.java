package org.mobibank.payme.backend.operations;

import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.numerate.StatutCode;
import org.mobibank.payme.backend.services.OperationService;
import org.mobibank.payme.security.SecurityUtils;

public class SimpleOperation extends Operation {

	public SimpleOperation(OperationService operationService, MyPayme services, String numeroAgent,
			String numeroClient, Double montant) {
		super(services);
		
		this.operationService = operationService;
		this.numeroAgent = numeroAgent;
		this.numeroClient = numeroClient;
		this.montant = montant;
		this.statut = StatutCode.EFFECTUER;
	}
	
	public SimpleOperation(OperationService operationService, MyPayme services,
			String numeroClient, Double montant) {
		super(services);
		this.operationService = operationService;
		this.agentUsername = SecurityUtils.getUsername();
		this.numeroClient = numeroClient;
		this.montant = montant;
		this.statut = StatutCode.EFFECTUER;
	}

}
