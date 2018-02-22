package org.mobibank.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.EntiteRepository;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.data.entity.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EntiteService implements CrudService<Entite> {

	@Override
	public EntiteRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(EntiteRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByDesignationLikeIgnoreCaseOrPrefixeLikeIgnoreCaseOrTypeLikeIgnoreCaseOrPaysNomLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Entite> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByDesignationLikeIgnoreCaseOrPrefixeLikeIgnoreCaseOrTypeLikeIgnoreCaseOrPaysNomLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Entite> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	public Collection<Entite> findAll() {
		// TODO Auto-generated method stub
		return getRepository().findAll();
	}
	
	public Collection<Entite> findByPays(Pays pays){
		return getRepository().findByPays(pays);
	}

}
