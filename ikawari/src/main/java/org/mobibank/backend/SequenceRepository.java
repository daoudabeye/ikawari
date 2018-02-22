package org.mobibank.backend;

import java.util.List;
import java.util.Set;

import org.mobibank.backend.data.entity.Sequences;
import org.mobibank.backend.data.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends CrudRepository<Sequences, Long> {

	List<Sequences> findAll();
	
	Set<Sequences> findByService(Services services);
	
	Page<Sequences> findBy(Pageable pageable);

	Page<Sequences> findByServiceNameLikeIgnoreCase(String serviceLike, Pageable pageable);
	
	long countByServiceNameLikeIgnoreCase(String serviceLike);
}
