package org.mobibank.payme.backend;

import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
	Utilisateur findByEmail(String email);
	
	Utilisateur findByTelephone(String telephone);
	
	Utilisateur findByUsername(String username);
	
	Utilisateur findByUsernameOrTelephone(String username, String telephone);

	Page<Utilisateur> findBy(Pageable pageable);

	Page<Utilisateur> findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String telephoneLike, Pageable pageable);

	long countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCase(String emailLike, String usernameLike, String telephoneLike);
}
