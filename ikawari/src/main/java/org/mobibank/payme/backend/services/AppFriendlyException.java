package org.mobibank.payme.backend.services;

import org.mobibank.payme.HasLogger;

public class AppFriendlyException extends Exception implements HasLogger{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppFriendlyException(String message) {
		super(message);
		getLogger().error(message);
	}
	
	public AppFriendlyException(String message, Throwable e) {
		super(message, e);
	}
}
