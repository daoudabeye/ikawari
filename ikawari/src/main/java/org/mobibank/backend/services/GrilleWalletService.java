package org.mobibank.backend.services;

import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.GrilleWalletRepository;
import org.mobibank.backend.data.entity.GrilleWallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GrilleWalletService implements CrudService<GrilleWallet> {

	@Override
	public GrilleWalletRepository getRepository() {
		return BeanLocator.find(GrilleWalletRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByPaysNomLikeOrServicesNameLike(repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<GrilleWallet> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByPaysNomLikeOrServicesNameLike(repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<GrilleWallet> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

}
