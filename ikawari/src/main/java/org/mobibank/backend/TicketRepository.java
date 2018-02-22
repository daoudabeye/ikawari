package org.mobibank.backend;

import java.util.List;

import org.mobibank.backend.data.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

	List<Ticket> findAll();
	
	Ticket findOneByToken(String token);
	
	Page<Ticket> findBy(Pageable pageable);

	Page<Ticket> findByClientTelephoneLikeOrClientUsernameLike(String clientTelLike, String clientUsernameLike, Pageable pageable);
	
	long countByClientTelephoneLikeOrClientUsernameLike(String clientTelLike, String clientUsernameLike);
}
