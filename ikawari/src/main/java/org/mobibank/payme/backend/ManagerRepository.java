package org.mobibank.payme.backend;

import java.util.Collection;

import org.mobibank.payme.backend.data.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Long>{

	Collection<Manager> findAll();
	
	Manager findByTelephone(String telephone);
	
	Manager findByEmail(String email);

	Manager findByUsername(String username);

	Page<Manager> findBy(Pageable pageable);

	Page<Manager> findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String nomLike, String prenomLike, String adresseLike, String villeLike,String telephoneLike, Pageable pageable);
	
	long countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(String emailLike,
			String usernameLike, String roleLike, String nomLike, String prenomLike, String adresseLike, String villeLike,String telephoneLike);

}
