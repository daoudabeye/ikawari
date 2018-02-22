package org.mobibank.payme.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.mobibank.payme.backend.data.entity.Transfert;
import org.mobibank.payme.backend.data.entity.TransfertUser;
import org.mobibank.payme.backend.numerate.PieceIdentite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertUserRepository extends CrudRepository<TransfertUser, Long>{
	
	List<TransfertUser> findByNomOrPrenom(String nom, String prenom);
	
	List<TransfertUser> findByAdresse(String adresse);
	
	List<TransfertUser> findByPays(String pays);
	
	List<TransfertUser> findByEmail(String email);
	
	List<TransfertUser> findByPieceIdentiteAndNumeroPieceIdentite(PieceIdentite piece, String numero);
	
	List<TransfertUser> findByTransfert(Transfert tarnsfert);
	
	List<TransfertUser> findByDateCreation(LocalDateTime datecreation);
	
	List<TransfertUser> findByNumeroPieceIdentite(String numeropieceidendite);
	
	List<TransfertUser> findByTelephone(String telephone);
	
	List<TransfertUser> findByDateNaissance(LocalDate datenaissance);
	
	List<TransfertUser> findAll();
	
	Page<TransfertUser> findBy(Pageable pageable);

	Page<TransfertUser> findByEmailLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(
			String emailLike, String telephoneLike, String nomLike, String prenomLike, String adresseLike, String villeLike, Pageable pageable);
	
	long countByEmailLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrNomLikeIgnoreCaseOrPrenomLikeIgnoreCaseOrAdresseLikeIgnoreCaseOrVilleLikeIgnoreCase(
			String emailLike, String telephoneLike, String nomLike, String prenomLike, String adresseLike, String villeLike);
}
