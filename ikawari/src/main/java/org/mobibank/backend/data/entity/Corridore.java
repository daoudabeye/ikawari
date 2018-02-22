package org.mobibank.backend.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = Corridore.TABLE_NAME)
@NamedQuery(name = "Corridore.findAll", query = "SELECT c FROM Corridore c")
public class Corridore extends AbstractEntity {
	
	public static final String TABLE_NAME = "corridore";

	@OneToOne
	@JoinColumn(name = "zone_src")
	private Zone zoneSrc;
	
	@OneToOne
	@JoinColumn(name = "zone_dst")
	private Zone zoneDst;
	
	@OneToOne
	@JoinColumn(name = "pays_src")
	private Pays paysSrc;
	
	@OneToOne
	@JoinColumn(name = "pays_dst")
	private Pays paysDst;
	
	@Column(name = "code_promo")
	private String codePromo;
	
	@OneToMany(mappedBy = "corridore")
	private List<GrilleTransfert> paliers;
	
	public Corridore(){
		super();
	}
	
	public Corridore(Zone zoneSrc, Zone zoneDst, Pays paysSrc, Pays paysDst, String codePromo) {
		super();
		this.zoneSrc = zoneSrc;
		this.zoneDst = zoneDst;
		this.paysSrc = paysSrc;
		this.paysDst = paysDst;
		this.codePromo = codePromo;
	}

	public Zone getZoneSrc() {
		return zoneSrc;
	}

	public void setZoneSrc(Zone zoneSrc) {
		this.zoneSrc = zoneSrc;
	}

	public Zone getZoneDst() {
		return zoneDst;
	}

	public void setZoneDst(Zone zoneDst) {
		this.zoneDst = zoneDst;
	}

	public Pays getPaysSrc() {
		return paysSrc;
	}

	public void setPaysSrc(Pays paysSrc) {
		this.paysSrc = paysSrc;
	}

	public Pays getPaysDst() {
		return paysDst;
	}

	public void setPaysDst(Pays paysDst) {
		this.paysDst = paysDst;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}

	public String getCodePromo() {
		return codePromo;
	}

	public void setCodePromo(String codePromo) {
		this.codePromo = codePromo;
	}

	public List<GrilleTransfert> getPaliers() {
		return paliers;
	}

	public void setPaliers(List<GrilleTransfert> paliers) {
		this.paliers = paliers;
	}

	public String toString(){
		final StringBuilder builder = new StringBuilder();
		builder.append(zoneSrc).append(zoneDst).append("->").append(paysSrc).append(paysDst);
		
		return builder.toString();
	}
}
