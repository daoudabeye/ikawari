package org.mobibank.payme.backend.services;

import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.TicketRepository;
import org.mobibank.payme.backend.data.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService implements CrudService<Ticket> {

	@Override
	public TicketRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(TicketRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByClientTelephoneLikeOrClientUsernameLike(repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Ticket> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByClientTelephoneLikeOrClientUsernameLike(repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Ticket> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}

}
