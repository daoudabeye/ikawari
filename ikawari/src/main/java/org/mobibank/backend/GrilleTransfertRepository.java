package org.mobibank.backend;

import java.util.List;

import org.mobibank.backend.data.entity.Corridore;
import org.mobibank.backend.data.entity.GrilleTransfert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrilleTransfertRepository extends CrudRepository<GrilleTransfert, Long>{

	List<GrilleTransfert> findByCorridore(Corridore corridore);
	
	List<GrilleTransfert> findByCorridoreId(Long IdCorridore);
	
	List<GrilleTransfert> findAll();
	
	@Query("SELECT p FROM GrilleTransfert p where p.corridore.id=:idCorridore and p.montantMin <=:montant and p.montantMax >=:montant")
	GrilleTransfert findByCorridoreAndMontant(@Param("idCorridore")Long idCorridore, @Param("montant")Double montant);
	
	Page<GrilleTransfert> findBy(Pageable pageable);

	Page<GrilleTransfert> findByCorridoreZoneSrcCodeLikeIgnoreCaseOrCorridoreZoneDstCodeLikeIgnoreCaseOrCorridorePaysSrcNomLikeIgnoreCaseOrCorridorePaysDstNomLikeIgnoreCaseOrCorridoreCodePromoLike(String zoneSrcLike,
			String zoneDstLike, String paysSrcLike, String paysDstLike,String codePromoLike, Pageable pageable);
	
	long countByCorridoreZoneSrcCodeLikeIgnoreCaseOrCorridoreZoneDstCodeLikeIgnoreCaseOrCorridorePaysSrcNomLikeIgnoreCaseOrCorridorePaysDstNomLikeIgnoreCaseOrCorridoreCodePromoLike(String zoneSrcLike,
			String zoneDstLike, String paysSrcLike, String paysDstLike, String codePromoLike);
}
