package org.mobibank.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.CorridoreRepository;
import org.mobibank.backend.data.entity.Corridore;
import org.mobibank.backend.data.entity.GrilleTransfert;
import org.mobibank.backend.data.entity.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CorridoreService implements CrudService<Corridore> {

	@Override
	public CorridoreRepository getRepository() {
		return BeanLocator.find(CorridoreRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByZoneSrcCodeLikeIgnoreCaseOrZoneDstCodeLikeIgnoreCaseOrPaysSrcNomLikeIgnoreCaseOrPaysDstNomLikeIgnoreCaseOrCodePromoLike(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Corridore> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByZoneSrcCodeLikeIgnoreCaseOrZoneDstCodeLikeIgnoreCaseOrPaysSrcNomLikeIgnoreCaseOrPaysDstNomLikeIgnoreCaseOrCodePromoLike(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Corridore> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	public Collection<Corridore> findAll() {
		// TODO Auto-generated method stub
		return getRepository().findAll();
	}
	
	/**
	 * Calcule les frais d'envoi d'un pays vers un autre
	 * @param paysSrc est le pays origine du transferts
	 * @param paysDst est le pays de dst de la transactioin
	 * @param montant le montant de la transaction
	 * @return les frais d'envoi en fonction de la grille
	 */
	public Double computeFrais(Pays paysSrc, Pays paysDst, Double montant) {
		if(paysSrc == null || paysDst == null)
			return 0.0;
		Corridore corridore = getRepository().findOneByPaysSrcAndPaysDst(paysSrc, paysDst);
		if(corridore == null){
			corridore = getRepository().findOneByZoneSrcAndZoneDst(paysSrc.getZone(), paysDst.getZone());
		}
		
		Assert.notNull(corridore, "Ce corridore n'est pas ouvert, merci de contacter le service client.");
		
		GrilleTransfert palier = BeanLocator.find(GrilleTransfertService.class).getRepository().findByCorridoreAndMontant(corridore.getId(), montant);
		Assert.notNull(palier, "Aucun frais défini pour ce montant.");
		
		return palier.getFrais();
	}
	
}
