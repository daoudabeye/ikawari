package org.mobibank.backend.services;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * A data integraty violation exception containing a message intended to be
 * shown to the end user.
 */
public class UserFriendlyDataException extends DataIntegrityViolationException {
	private static final long serialVersionUID = 1L;

	public UserFriendlyDataException(String message) {
		super(message);
	}

}
