package org.mobibank.backend.operations;

import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Ordres;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.data.entity.TransfertUser;
import org.mobibank.backend.numerate.Devise;
import org.mobibank.backend.numerate.Module;
import org.mobibank.backend.numerate.TypeTransfert;
import org.mobibank.backend.services.OperationService;

public class Envois extends Transferts {

	public Envois(OperationService operationService, Ordres service, TypeTransfert transfertType, Double montant,
			Pays destination, String username, TransfertUser beneficiaire,
			TransfertUser expediteur, Agent payeur, Structure pointService, Devise devise) {
		super(service);
		
		this.operationService = operationService;
		this.typeTransfert = transfertType;
		this.montant = montant;
		this.destination = destination;
		this.agentUsername = username;
		this.beneficiary = beneficiaire;
		this.sender = expediteur;
		this.module = Module.ENVOIS;
		this.devise = devise;
		this.payeur = payeur;
		this.pointService = pointService;
	}
	
}
