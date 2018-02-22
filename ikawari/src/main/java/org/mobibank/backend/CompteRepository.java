package org.mobibank.backend;

import java.util.Collection;

import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.data.entity.Utilisateur;
import org.mobibank.backend.numerate.TypeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CompteRepository extends CrudRepository<Compte, Long> {
	
	Page<Compte> findBy(Pageable pageable);

	Page<Compte> findByUtilisateurUsernameLike(String usernameLike, Pageable pageable);
	
	long countByUtilisateurUsernameLike(String usernameLike);

	Collection<Compte> findByUtilisateur(Utilisateur utilisateur);

	Compte findFirst1ByUtilisateurAndType(Utilisateur utilisateur, TypeCompte type);
}
