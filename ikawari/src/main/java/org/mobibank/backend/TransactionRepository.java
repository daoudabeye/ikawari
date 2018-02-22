package org.mobibank.backend;

import java.util.Date;
import java.util.List;

import org.mobibank.backend.data.entity.Compte;
import org.mobibank.backend.data.entity.Transaction;
import org.mobibank.backend.numerate.Module;
import org.mobibank.backend.numerate.StatutCode;
import org.mobibank.backend.numerate.TypeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	List<Transaction> findAll();
	
	@Modifying
	@Query("UPDATE Transaction t set t.reference=:reference where t.id=:id")
	int updateReference(@Param("id")Long id, @Param("reference")String reference);
	
	@Modifying
	@Query("UPDATE Transaction t set t.statut=:statut where t.id=:id")
	int updateStatut(@Param("id")Long id, @Param("statut")StatutCode statutCode);
	
	Transaction findByCompteAndReference(TypeCompte compte, String reference);
	
	Transaction findOneByCompteAndReferenceAndStatut(Compte compte, String reference, StatutCode statut);
	
	List<Transaction> findByOperation(Module operation);
	
	List<Transaction> findByReference(String refrence);
	
	List<Transaction> findByCompteOrderByIdDesc(Compte compte);
	
	List<Transaction> findByCompte(Compte compte);
	
	@Query(value ="SELECT count(t) FROM Transaction t where t.dateCreation BETWEEN :startDate AND :endDate")
	Long count(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value ="SELECT count(t) FROM Transaction t where t.compte=:compte AND t.dateCreation BETWEEN :startDate AND :endDate")
	Long count(@Param("compte")TypeCompte compte, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value ="SELECT count(t) FROM Transaction t where t.operation=:typeOperation AND t.dateCreation BETWEEN :startDate AND :endDate")
	Long count(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("typeOperation") Module typeOperation);
	
	@Query(value ="SELECT sum(t.montant) FROM Transaction t where t.dateCreation BETWEEN :startDate AND :endDate")
	Double somme(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value ="SELECT sum(t.montant) FROM Transaction t where t.compte=:compte AND t.dateCreation BETWEEN :startDate AND :endDate")
	Double somme(@Param("compte")TypeCompte compte, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value ="SELECT sum(t.montant) FROM Transaction t where t.operation=:typeOperation AND t.dateCreation BETWEEN :startDate AND :endDate")
	Double somme(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("typeOperation") Module typeOperation);
	
	@Query(value ="SELECT sum(t.montant) FROM Transaction t where t.compte=:compte AND t.operation=:typeOperation AND t.dateCreation BETWEEN :startDate AND :endDate")
	Double somme(@Param("compte")TypeCompte compte, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("typeOperation") Module typeOperation);

	Page<Transaction> findBy(Pageable pageable);

	Page<Transaction> findByReferenceLikeIgnoreCaseOrCompteUtilisateurUsernameLike(String referenceLike, String usernameLike, Pageable pageable);
	
	long countByReferenceLikeIgnoreCaseOrCompteUtilisateurUsernameLike(String referenceLike, String usernameLike);
}
