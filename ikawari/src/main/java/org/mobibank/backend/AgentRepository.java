package org.mobibank.backend;

import java.util.Collection;

import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.data.entity.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent findByEmail(String email);

	Agent findByTelephone(String telephone);

	Agent findByUsername(String username);
	
	Agent findFirst1ByUsernameOrTelephone(String username, String telephone);

	Page<Agent> findBy(Pageable pageable);

	Page<Agent> findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String nomLike, String prenomLike, String adresseLike, String villeLike,String telephoneLike, Pageable pageable);

	long countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String nomLike, String prenomLike, String adresseLike, String villeLike,String telephoneLike);

	Collection<Agent> findByEntitePays(Pays value);

	Collection<Agent> findByEntitePaysAndRole(Pays value, String role);
	
	Collection<Agent> findByEntitePaysAndIsPayeur(Pays pays, boolean isPayeur);

}
