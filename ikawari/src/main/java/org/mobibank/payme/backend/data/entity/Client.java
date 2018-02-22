package org.mobibank.payme.backend.data.entity;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
//import javax.persistence.Table;

@Entity
//@Table( name = Client.TABLE_NAME )
@DiscriminatorValue(value = "CLIENT")
public class Client extends PersonnePhysique {
	
//	public static final String TABLE_NAME = "client";
	
	@OneToMany
	private Set<Ticket> tickets;
	
	public Client() {
		super();
	}
	
	public Client(String username, String name, String password, String role) {
		super(username, password, role);
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
}
