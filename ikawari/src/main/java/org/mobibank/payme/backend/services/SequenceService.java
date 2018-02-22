package org.mobibank.payme.backend.services;

import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.SequenceRepository;
import org.mobibank.payme.backend.data.entity.Sequences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SequenceService implements CrudService<Sequences> {

	@Override
	public SequenceRepository getRepository() {
		return BeanLocator.find(SequenceRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByServiceNameLikeIgnoreCase(repositoryFilter);
		} else {
			return getRepository().count();
		}

	}

	@Override
	public Page<Sequences> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByServiceNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Sequences> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}

}
