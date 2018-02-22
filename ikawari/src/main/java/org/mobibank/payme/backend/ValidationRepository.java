package org.mobibank.payme.backend;

import org.mobibank.payme.backend.data.entity.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository extends JpaRepository<Validation, Long> {

	Page<Validation> findBy(Pageable pageable);

	Page<Validation> findByEntiteDesignationLike(String entiteLike, Pageable pageable);
	
	long countByEntiteDesignationLike(String entiteLike);
}
