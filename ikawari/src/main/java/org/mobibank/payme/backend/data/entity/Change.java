package org.mobibank.payme.backend.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.mobibank.payme.backend.numerate.Devise;

@Entity
@Table(name = Change.TABLE_NAME)
@NamedQuery(name="Change.findAll", query = "SELECT c FROM Change c")
public class Change extends AbstractEntity {

	public static final String TABLE_NAME = "devise";
	
	@Enumerated
	private Devise src;
	
	@Enumerated
	private Devise dst;
	
	private Double value;
	
	private LocalDate updateDate;
	
	public Change() {
		super();
		this.updateDate = LocalDate.now();
	}
	
	public Change(Devise from, Devise to, LocalDate updateDate, Double value) {
		super();
		this.src = from;
		this.dst = to;
		this.value = value;
		this.updateDate = updateDate;
	}

	

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public String getStringValue() {
		return value != null ? value+"" : "0";
	}

	public void setValue(String value) {
		if(value != null) this.value = Double.valueOf(value);
	}

	public Devise getSrc() {
		return src;
	}

	public void setSrc(Devise src) {
		this.src = src;
	}

	public Devise getDst() {
		return dst;
	}

	public void setDst(Devise dst) {
		this.dst = dst;
	}
}
