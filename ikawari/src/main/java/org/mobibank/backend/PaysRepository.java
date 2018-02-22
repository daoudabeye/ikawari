package org.mobibank.backend;

import java.util.List;

import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.data.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysRepository extends CrudRepository<Pays, Long> {

	List<Pays> findAll();
	
	Pays findByNomAndCodeIso(String name, String iso);
	
	Pays findByNom(String nom);
	
	Pays findByCodeIso(String codeIs);
	
	List<Pays> findByZone(Zone zone);
	
	Page<Pays> findBy(Pageable pageable);

	Page<Pays> findByNomLikeIgnoreCaseOrCodeIsoLikeIgnoreCaseOrIndicatifLike(String nomLike, String codeIsoLike, String indicatifLike, Pageable pageable);
	
	long countByNomLikeIgnoreCaseOrCodeIsoLikeIgnoreCaseOrIndicatifLike(String nomLike, String codeIsoLike, String indicatifLike);
	
}
