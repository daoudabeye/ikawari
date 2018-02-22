package org.mobibank.payme.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.ZoneRepository;
import org.mobibank.payme.backend.data.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ZoneService implements CrudService<Zone> {

	@Override
	public ZoneRepository getRepository() {
		return BeanLocator.find(ZoneRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByDesignationLikeIgnoreCaseOrCodeLikeIgnoreCase(repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Zone> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByDesignationLikeIgnoreCaseOrCodeLikeIgnoreCase(repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Zone> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}
	
	public Collection<Zone> findAll(){
		return getRepository().findAll();
	}

}
