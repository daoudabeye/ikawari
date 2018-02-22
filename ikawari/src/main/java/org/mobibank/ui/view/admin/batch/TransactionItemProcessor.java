package org.mobibank.ui.view.admin.batch;

import org.mobibank.HasLogger;
import org.mobibank.backend.data.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction>, HasLogger {

	@Override
	public Transaction process(Transaction transaction) throws Exception {
		// TODO Traitement de l'information
		getLogger().info("Batch généré....");
		return transaction;
	}

}
