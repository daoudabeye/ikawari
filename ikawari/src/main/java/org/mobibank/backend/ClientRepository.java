package org.mobibank.backend;

import org.mobibank.backend.data.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Page<Client> findBy(Pageable pageable);

	Page<Client> findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String nomLike, String prenomLike, String adresseLike, String villeLike,String telephoneLike, Pageable pageable);
	
	long countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String nomLike, String prenomLike, String adresseLike, String villeLike,String telephoneLike);
}
