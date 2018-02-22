package org.mobibank.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.ServicesRepository;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppService implements CrudService<Services> {

	@Override
	public ServicesRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(ServicesRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Services> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Services> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}

	public Collection<Services> findAll() {
		return getRepository().findAll();
	}

	public void update(Services selectedCommissionService) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Services> find(Pays pays) {
		// TODO Auto-generated method stub
		return getRepository().findAll();
	}

	/**
	 * Recherche des services disponible pour l'utilisateur courant
	 * @return
	 */
	public Collection<Services> findForCurrentUser() {
//		Utilisateur current = BeanLocator.find(UtilisateurService.class).getCurrentUser();
//		Entite entite = current.getEntite();
		return getRepository().findAll();
	}

}
