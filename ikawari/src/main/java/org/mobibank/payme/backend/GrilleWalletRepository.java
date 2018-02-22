package org.mobibank.payme.backend;

import java.util.List;

import org.mobibank.payme.backend.data.entity.GrilleWallet;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GrilleWalletRepository extends CrudRepository<GrilleWallet, Long>{

	List<GrilleWallet> findAll();
	
	@Query("SELECT g FROM GrilleWallet g where g.services =:services and g.pays=:pays and g.montantMin<=:montant and g.montantMax>=:montant")
	GrilleWallet findByTypeOperation(@Param("services")Services services, @Param("montant") double montant, @Param("pays") Pays pays);
	
	Page<GrilleWallet> findBy(Pageable pageable);

	Page<GrilleWallet> findByPaysNomLikeOrServicesNameLike(String paysLike, String serviceNameLike, Pageable pageable);
	
	long countByPaysNomLikeOrServicesNameLike(String paysLike, String serviceNameLike);
}
