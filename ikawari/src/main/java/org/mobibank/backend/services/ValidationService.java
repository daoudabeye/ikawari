package org.mobibank.backend.services;

import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.ValidationRepository;
import org.mobibank.backend.data.entity.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ValidationService implements CrudService<Validation> {

	@Override
	public ValidationRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(ValidationRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByEntiteDesignationLike(repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Validation> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByEntiteDesignationLike(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Validation> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}
	
}
