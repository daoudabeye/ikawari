package org.mobibank.payme.ui.view.admin.batch;

import org.mobibank.payme.HasLogger;
import org.mobibank.payme.backend.data.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class TransactionItemProcessor implements ItemProcessor<Transaction, Transaction>, HasLogger {

	@Override
	public Transaction process(Transaction transaction) throws Exception {
		// TODO Traitement de l'information
		getLogger().info("Batch généré....");
		return transaction;
	}

}
