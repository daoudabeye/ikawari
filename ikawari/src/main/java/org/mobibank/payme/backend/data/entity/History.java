package org.mobibank.payme.backend.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.mobibank.payme.backend.numerate.StatutCode;

@Entity
@Table( name = History.TABLE_NAME )
public class History extends AbstractEntity {
	
	public static final String TABLE_NAME = "history";

	private StatutCode newState;

	@NotEmpty
	@Size(max = 255)
	private String message;

	@NotNull
	private LocalDateTime timestamp;
	
	@OneToOne
	private Utilisateur createdBy;

	protected History() {
		// Empty constructor is needed by Spring Data / JPA
	}

	public History(Utilisateur createdBy, String message) {
		this.createdBy = createdBy;
		this.message = message;
		timestamp = LocalDateTime.now();
	}

	public StatutCode getNewState() {
		return newState;
	}

	public void setNewState(StatutCode newState) {
		this.newState = newState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Utilisateur getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Utilisateur createdBy) {
		this.createdBy = createdBy;
	}

}
