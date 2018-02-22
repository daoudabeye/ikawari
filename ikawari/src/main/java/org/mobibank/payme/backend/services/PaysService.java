package org.mobibank.payme.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.PaysRepository;
import org.mobibank.payme.backend.data.entity.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 
 * @author BEYE
 * 
 */
@Service
public class PaysService implements CrudService<Pays> {

	@Override
	public PaysRepository getRepository() {
		return BeanLocator.find(PaysRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByNomLikeIgnoreCaseOrCodeIsoLikeIgnoreCaseOrIndicatifLike(repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Pays> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByNomLikeIgnoreCaseOrCodeIsoLikeIgnoreCaseOrIndicatifLike(repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Pays> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	public Collection<Pays> findAll() {
		// TODO Auto-generated method stub
		return getRepository().findAll();
	}
	
	public Collection<Pays> findAvailableDst() {
		// TODO Auto-generated method stub
		Collection<Pays> pays = getRepository().findAll();
		pays.remove(BeanLocator.find(UtilisateurService.class).getCurrentUser().getEntite().getPays());
		return pays;
	}

}
