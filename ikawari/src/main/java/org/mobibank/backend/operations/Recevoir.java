package org.mobibank.backend.operations;

import org.mobibank.backend.data.entity.Ordres;
import org.mobibank.backend.numerate.Module;
import org.mobibank.backend.services.OperationService;

public class Recevoir extends Transferts {

	public Recevoir(OperationService operationService, Ordres service, Long mtcn) {
		super(service);
		this.operationService = operationService;
		this.module = Module.RECEVOIR;
		this.mtcn = mtcn;
	}

}
