package org.mobibank.payme.backend.services;

import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.ClientRepository;
import org.mobibank.payme.backend.data.entity.Client;
import org.mobibank.payme.backend.numerate.Devise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ClientService implements CrudService<Client> {

	@Autowired UtilisateurService utilisateurService;
	
	@Override
	public ClientRepository getRepository() {
		return BeanLocator.find(ClientRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Client> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Client> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	@Transactional
	@Override
	public Client save(Client bean) {
		bean.setCreateur(utilisateurService.getCurrentUser());
		
		if(bean.getEntite() == null)
			bean.setEntite(utilisateurService.getCurrentUser().getEntite());
		Assert.notNull(bean.getEntite(), "Merci de selectionner une entité.");
		
		Client client = getRepository().save(bean);
		utilisateurService.createWallet(client, Devise.CFA);
		return client;
	}
	
}
