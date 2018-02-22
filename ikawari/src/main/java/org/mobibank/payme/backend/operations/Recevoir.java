package org.mobibank.payme.backend.operations;

import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.services.OperationService;

public class Recevoir extends Transferts {

	public Recevoir(OperationService operationService, Ordres service, Long mtcn) {
		super(service);
		this.operationService = operationService;
		this.module = Module.RECEVOIR;
		this.mtcn = mtcn;
	}

}
