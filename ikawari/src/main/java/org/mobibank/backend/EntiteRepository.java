package org.mobibank.backend;

import java.util.Collection;
import java.util.List;

import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.data.entity.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntiteRepository extends CrudRepository<Entite, Long>{
	
	List<Entite> findAll();
	
	Page<Entite> findBy(Pageable pageable);

	Page<Entite> findByDesignationLikeIgnoreCaseOrPrefixeLikeIgnoreCaseOrTypeLikeIgnoreCaseOrPaysNomLikeIgnoreCase(
			String adresseLike, String desigantionLike, String prefixeLike, String telephoneLike, String typeLike, String zoneLike, String paysLike, Pageable pageable);
	
	long countByDesignationLikeIgnoreCaseOrPrefixeLikeIgnoreCaseOrTypeLikeIgnoreCaseOrPaysNomLikeIgnoreCase(
			String adresseLike, String desigantionLike, String prefixeLike, String telephoneLike, String typeLike, String zoneLike, String paysLike);

	Collection<Entite> findByPays(Pays pays);
}
