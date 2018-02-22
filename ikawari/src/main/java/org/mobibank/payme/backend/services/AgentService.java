package org.mobibank.payme.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.AgentRepository;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Agent;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.numerate.Devise;
import org.mobibank.payme.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 
 * @author BEYE
 * 
 * AGENT CRUD SERVICE
 *
 */
@Service
public class AgentService implements CrudService<Agent> {
	
	public static final String password = "paymesys";

	@Autowired UtilisateurService utilisateurService;
	@Autowired AgentService agentService;

	@Override
	public AgentRepository getRepository() {
		return BeanLocator.find(AgentRepository.class);
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
	public Page<Agent> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(
					repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Agent> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	public Collection<Agent> findByPays(Pays value, String role) {
		// TODO Auto-generated method stub
		return getRepository().findByEntitePaysAndRole(value, role);
	}
	
	@Transactional
	@Override
	public Agent save(Agent bean) {
		Utilisateur currentUser = utilisateurService.getCurrentUser();
		bean.setCreateur(currentUser);
		
		if(bean.getEntite() == null)
			bean.setEntite(currentUser.getEntite());
		Assert.notNull(bean.getEntite(), "Merci de selectionner une entité.");
		
		if(SecurityUtils.isCurrentUserInRole(Role.MASTER))
			bean.setMaster(agentService.getRepository().findOne(currentUser.getId()));
		
//		else if(SecurityUtils.isCurrentUserInRole(Role.AGENCE))
//			bean.setMaster((Agent) agentService.getRepository().findOne(currentUser.getCreateur().getId()));
		
		Agent agent = getRepository().save(bean);
		utilisateurService.createWallet(agent, Devise.CFA);
		
		return agent;
	}

	public Collection<Agent> findLocalPayeurs() {
		Pays pays = utilisateurService.getCurrentUser().getEntite().getPays();
		return getRepository().findByEntitePaysAndIsPayeur(pays, true);
	}
	
	public Collection<Agent> findPayeurs(Pays pays) {
		return getRepository().findByEntitePaysAndIsPayeur(pays, true);
	}

}
