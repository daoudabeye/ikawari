package org.mobibank.payme.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.mobibank.payme.backend.numerate.CommissionType;

@Embeddable
public class Commission {

	private CommissionType type;
	
	@Column(name = "quote_part")
	private Integer quotePart;
	
	public Commission() {
		// TODO Auto-generated constructor stub
	}

	public Commission(CommissionType type, Integer quotePart) {
		super();
		this.type = type;
		this.quotePart = quotePart;
	}

	public CommissionType getType() {
		return type;
	}

	public void setType(CommissionType type) {
		this.type = type;
	}

	public Integer getQuotePart() {
		return quotePart;
	}

	public void setQuotePart(Integer quotePart) {
		this.quotePart = quotePart;
	}
}
