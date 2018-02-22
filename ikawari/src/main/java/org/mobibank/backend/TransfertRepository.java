package org.mobibank.backend;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.mobibank.backend.data.entity.Transfert;
import org.mobibank.backend.numerate.StatutCode;
import org.mobibank.backend.numerate.TcpStatut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertRepository extends CrudRepository<Transfert, Long> {

	Transfert findByMtcn(Long mtcn);
	
	Transfert findByFolio(String folio);
	
	Transfert findOneByMtcnOrFolio(Long mtcn, String folio);
	
	List<Transfert> findAll();
	
	@Modifying
	@Query("UPDATE Transfert t set t.statut=:statut where t.id=:id")
	int updateStatut(@Param("id")Long id, @Param("statut")StatutCode statut);

	@Modifying
	@Query("UPDATE Transfert t set t.tcpStatut=:tcpStatut where t.id=:id")
	int updateTcpStatut(@Param("id")Long id, @Param("tcpStatut")TcpStatut statut);
	
	@Modifying
	@Query("UPDATE Transfert t set t.validationCode=:validationCode where t.id=:id")
	int updateValidationCode(@Param("id")Long id, @Param("validationCode")Long validationCode);
	
	@Query(value ="SELECT count(t) FROM Transfert t where t.dateCreation BETWEEN :startDate AND :endDate")
	Long count(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value ="SELECT sum(t.montant) FROM Transfert t where t.dateCreation BETWEEN :startDate AND :endDate")
	Double somme(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	Page<Transfert> findBy(Pageable pageable);

	Page<Transfert> findByFolioLikeIgnoreCaseOrMtcnLike(String folioLike, Long mtcnLike, Pageable pageable);
	
	long countByFolioLikeIgnoreCase(String folioLike);

	Page<Transfert> findByMtcnAndDateEnvoiAfter(Long mtcn, LocalDate localDate, Pageable pageable);

	Page<Transfert> findByMtcn(Long mtcn, Pageable pageable);

	Page<Transfert> findByDateCreationAfter(LocalDate localDate, Pageable pageable);

	Page<Transfert> findAll(Pageable pageable);

	long countByMtcnAndDateEnvoiAfter(Long string, LocalDate localDate);

	long countByMtcn(Long mtcn);

	long countByDateCreationAfter(LocalDate localDate);

	long countByDateEnvoiAfter(LocalDate localDate);

	Page<Transfert> findByDateEnvoiAfter(LocalDate localDate, Pageable pageable);
}
