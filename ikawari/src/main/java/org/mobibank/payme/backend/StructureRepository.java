package org.mobibank.payme.backend;

import java.util.Collection;

import org.mobibank.payme.backend.data.entity.Agent;
import org.mobibank.payme.backend.data.entity.Entite;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StructureRepository extends JpaRepository<Structure, Long> {

	Structure findByRole(String role);
	
	Structure findByRoleAndEntite(String role, Entite pays);
	
	Structure findByRoleAndEntitePays(String role, Pays pays);
	
	Page<Structure> findBy(Pageable pageable);

	Page<Structure> findByDesignationLikeIgnoreCase(String desigantionLike, Pageable pageable);
	
	long countByDesignationLikeIgnoreCase(String desigantionLike);

	Collection<Structure> findByEntitePaysAndRole(Pays pays, String role);
	
	Collection<Structure> findByMasterAndRole(Agent master, String role);
	
	Collection<Structure> findByMasterIdAndRole(Long master, String role);
}
