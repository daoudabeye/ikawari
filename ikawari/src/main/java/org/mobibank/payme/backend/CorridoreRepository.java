package org.mobibank.payme.backend;

import java.util.List;

import org.mobibank.payme.backend.data.entity.Corridore;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorridoreRepository extends JpaRepository<Corridore, Long> {

	List<Corridore> findAll();
	
	Corridore findOneByPaysSrcAndPaysDst(Pays paysSrc, Pays paysDst);
	
	Corridore findOneByZoneSrcAndZoneDst(Zone zoneSrc, Zone zoneDst);

	Page<Corridore> findBy(Pageable pageable);

	Page<Corridore> findByZoneSrcCodeLikeIgnoreCaseOrZoneDstCodeLikeIgnoreCaseOrPaysSrcNomLikeIgnoreCaseOrPaysDstNomLikeIgnoreCaseOrCodePromoLike(String zoneSrcLike,
			String zoneDstLike, String paysSrcLike, String paysDstLike, String codePromoLike, Pageable pageable);
	
	long countByZoneSrcCodeLikeIgnoreCaseOrZoneDstCodeLikeIgnoreCaseOrPaysSrcNomLikeIgnoreCaseOrPaysDstNomLikeIgnoreCaseOrCodePromoLike(String zoneSrcLike,
			String zoneDstLike, String paysSrcLike, String paysDstLike, String codePromoLike);
	
//	Page<Corridore> findByZoneSrcCodeLikeOrZoneDstCodeLikeOrPaysSrcNomLikeIgnoreCaseOrPaysDstNomLikeIgnoreCaseOrCodePromoLike(String zoneSrcLike,
//			String zoneDstLike, String paysSrcLike, String paysDstLike, String codePromoLike, Pageable pageable);
//	
//	long countByZoneSrcCodeLikeOrZoneDstCodeLikeOrPaysSrcNomLikeIgnoreCaseOrPaysDstNomLikeIgnoreCaseOrCodePromoLike(String zoneSrcLike,
//			String zoneDstLike, String paysSrcLike, String paysDstLike, String codePromoLike);
}
