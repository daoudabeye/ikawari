package org.mobibank.backend.data.entity;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import org.mobibank.backend.numerate.GlobalParam;

@Entity
@Table(name = Parametres.TABLE_NAME )
public class Parametres extends AbstractEntity {
	
	public static final String TABLE_NAME = "parametres";
	
	@ElementCollection(fetch = FetchType.EAGER)
	Map<GlobalParam, String> params;
	
	public Parametres() {
		super();
	}
	
	public Parametres(Map<GlobalParam, String> params, Pays pays) {
		super();
		this.params = params;
	}

	public Map<GlobalParam, String> getParams() {
		return params;
	}

	public void setParams(Map<GlobalParam, String> params) {
		this.params = params;
	}
}
