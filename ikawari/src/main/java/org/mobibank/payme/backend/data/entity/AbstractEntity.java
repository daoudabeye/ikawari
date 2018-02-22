package org.mobibank.payme.backend.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 
 * @author BEYE
 *
 * Class herité par tous les entité pour le mapping ORM
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Identifiant autogenéré
	private Long id;

	@Version
	private int version;
	
	protected LocalDateTime dateCreation;
	
	public AbstractEntity() {
		this.dateCreation = LocalDateTime.now();
	}

	public boolean isNew() {
		return id == null;
	}

	public Long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}
	
	@Override
	public int hashCode() {
		if (id == null) {
			return super.hashCode();
		}

		return 31 + id.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (id == null) {
			// New entities are only equal if the instance if the same
			// 
			return super.equals(other);
		}

		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractEntity)) {
			return false;
		}
		return id.equals(((AbstractEntity) other).id);
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

}
