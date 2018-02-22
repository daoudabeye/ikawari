package org.mobibank.backend.services;

import java.util.Collection;
import java.util.Optional;

import org.mobibank.BeanLocator;
import org.mobibank.backend.StructureRepository;
import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.numerate.Devise;
import org.mobibank.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class StructureService implements CrudService<Structure> {

	private final UtilisateurService utilisateurService;

	public StructureService(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@Override
	public StructureRepository getRepository() {
		return BeanLocator.find(StructureRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByDesignationLikeIgnoreCase(repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Structure> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByDesignationLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Structure> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	/**
	 * Find Structure by role inside contry
	 * @param pays 
	 * @param role
	 * @return
	 */
	public Structure findByRole(String role) {
		return getRepository().findByRole(role);
	}

	/**
	 * Find Structure by role inside contry
	 * @param pays 
	 * @param role
	 * @return
	 */
	public Collection<Structure> findByRole(Pays pays, String role) {
		return getRepository().findByEntitePaysAndRole(pays, role);
	}
	
	public Collection<Structure> findForCurentUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Collection<Structure> find(Agent master, String role) {
		return getRepository().findByMasterAndRole(master, role);
	}
	
	public Collection<Structure> find(Long master, String role) {
		return getRepository().findByMasterIdAndRole(master, role);
	}

	/**
	 * Verifie l'existance d'un compte
	 * @param b
	 * @return
	 */
	public boolean exist(String value) {
		return utilisateurService.exist(value);
	}

	/**
	 * Creation d'un compte pour une structure
	 * @param bean
	 * @return
	 * @throws AppFriendlyException
	 */
	@Transactional
	@Override
	public Structure save(Structure bean){
		Utilisateur currentUser = utilisateurService.getCurrentUser();
		
		bean.setCreateur(currentUser);

		//Un point de service peut etre crée par l'admin ou par un master ou un master B2B
		if(Role.POINTSERVICE.equals(bean.getRole())) {
			if(SecurityUtils.isCurrentUserInRole(Role.ADMIN))
				Assert.notNull(bean.getMaster(), "Vous devez selectionner le master du poit de service");

			else if(SecurityUtils.isCurrentUserInRole(Role.MASTER))
				bean.setMaster((Agent) currentUser);

			Assert.notNull(bean, "Le master/partenaire est obligatoire pour la création d'un point de service");
		}

		if(bean.getEntite() == null) {
			bean.setEntite(currentUser.getEntite());
		}
		
		Assert.notNull(bean.getEntite(), "Merci de selectionner une entité.");

		if(!"".equals(bean.getTelephone()))
			bean.setTelephone(bean.getEntite().getPays().getIndicatif() + bean.getTelephone());

		Structure structure = getRepository().save(bean);
		utilisateurService.createWallet(structure, Devise.CFA);

		return structure;
	}

	public Collection<Structure> findServicePoint(Agent agent) {
		// TODO Auto-generated method stub
		return getRepository().findByMasterAndRole(agent, Role.POINTSERVICE);
	}

}
