package org.mobibank.payme.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.MyPaymeRepository;
import org.mobibank.payme.backend.data.entity.Entite;
import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyPaymeService implements CrudService<MyPayme> {

	@Override
	public MyPaymeRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(MyPaymeRepository.class);
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
	public Page<MyPayme> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<MyPayme> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}

	public Collection<MyPayme> find(Pays pays) {
		// TODO Auto-generated method stub
		return getRepository().findByPays(pays);
	}

	/**
	 * Recherche des services disponible pour l'utilisateur courant
	 * @return
	 */
	public Collection<MyPayme> findForCurrentUser() {
		Utilisateur current = BeanLocator.find(UtilisateurService.class).getCurrentUser();
		Entite entite = current.getEntite();
		return getRepository().findByPaysAndAutoriseProfile(entite.getPays(), current.getRole());
	}

}
