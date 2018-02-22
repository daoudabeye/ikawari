package org.mobibank.payme.backend.services;

import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.TransfertUserRepository;
import org.mobibank.payme.backend.data.entity.TransfertUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransfertUserService implements CrudService<TransfertUser> {

	@Override
	public TransfertUserRepository getRepository() {
		return BeanLocator.find(TransfertUserRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByEmailLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<TransfertUser> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByEmailLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<TransfertUser> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	
}
