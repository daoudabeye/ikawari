package org.mobibank.backend.services;

import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.GrilleTransfertRepository;
import org.mobibank.backend.data.entity.GrilleTransfert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GrilleTransfertService implements CrudService<GrilleTransfert> {

	@Override
	public GrilleTransfertRepository getRepository() {
		return BeanLocator.find(GrilleTransfertRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByCorridoreZoneSrcCodeLikeIgnoreCaseOrCorridoreZoneDstCodeLikeIgnoreCaseOrCorridorePaysSrcNomLikeIgnoreCaseOrCorridorePaysDstNomLikeIgnoreCaseOrCorridoreCodePromoLike(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<GrilleTransfert> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByCorridoreZoneSrcCodeLikeIgnoreCaseOrCorridoreZoneDstCodeLikeIgnoreCaseOrCorridorePaysSrcNomLikeIgnoreCaseOrCorridorePaysDstNomLikeIgnoreCaseOrCorridoreCodePromoLike(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<GrilleTransfert> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}
	
}
