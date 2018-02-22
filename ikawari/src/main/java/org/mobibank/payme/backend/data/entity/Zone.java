package org.mobibank.payme.backend.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = Zone.TABLE_NAME)
@NamedQuery(name = "Zone.findAll", query = "SELECT z FROM Zone z")
public class Zone extends AbstractEntity{
	public static final String TABLE_NAME = "zone";

	@Column(unique = true)
	private String designation;
	
	@Column(unique = true)
	private String code;
	
	@OneToMany(mappedBy="zone")
	private List<Pays> pays;
	
	public Zone() {
		super();
	}
	
	public Zone(String designation, String code) {
		super();
		this.designation = designation;
		this.code = code;
	}
	
	public void update(Zone z) {
		this.designation = z.getDesignation();
		this.code = z.getCode();
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Pays> getPays() {
		return pays;
	}

	public void setPays(List<Pays> pays) {
		this.pays = pays;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String code = this.code == null ? "" : "(" + this.code + ")";
		return this.designation + code;
	}

}
