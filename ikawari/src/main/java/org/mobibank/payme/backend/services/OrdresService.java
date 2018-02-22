package org.mobibank.payme.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.OrdresRepository;
import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.numerate.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrdresService implements CrudService<Ordres> {

	@Override
	public OrdresRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(OrdresRepository.class);
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
	public Page<Ordres> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Ordres> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}

	/**
	 * Recherche des services disponible pour l'utilisateur courant
	 * @return
	 */
	public Collection<Ordres> findForCurrentUser(Module module) {
		Utilisateur current = BeanLocator.find(UtilisateurService.class).getCurrentUser();
		return getRepository().findByModuleAndAutoriseProfile(module, current.getRole());
	}

}
