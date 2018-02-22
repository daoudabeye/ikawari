package org.mobibank.payme.backend;

import java.util.List;

import org.mobibank.payme.backend.data.entity.Horaires;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorrairesRepository extends CrudRepository<Horaires, Long>{

	List<Horaires> findAll();
	
	Page<Horaires> findBy(Pageable pageable);

	Page<Horaires> findByStructureDesignationLikeIgnoreCase(
			String structureLike, Pageable pageable);
	
	long countByStructureDesignationLikeIgnoreCase(String structureLike);
}
