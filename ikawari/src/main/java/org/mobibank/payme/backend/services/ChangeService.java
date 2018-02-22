package org.mobibank.payme.backend.services;

import java.time.LocalDate;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.ChangeRepository;
import org.mobibank.payme.backend.data.entity.Change;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChangeService implements CrudService<Change> {

	@Override
	public ChangeRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(ChangeRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countBySrcLikeOrDstLike(repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Change> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findBySrcLikeOrDstLike(repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Change> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}
	
	@Override
	public Change save(Change entity) {
		// TODO Auto-generated method stub
		entity.setUpdateDate(LocalDate.now());
		return CrudService.super.save(entity);
	}

}
