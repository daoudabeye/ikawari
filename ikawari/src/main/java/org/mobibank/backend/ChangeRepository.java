package org.mobibank.backend;

import java.util.Collection;

import org.mobibank.backend.data.entity.Change;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ChangeRepository extends CrudRepository<Change, Long>{
	
	Collection<Change> findAll();
	
	Page<Change> findBy(Pageable pageable);

	Page<Change> findBySrcLikeOrDstLike(String srcLike, String dstLike, Pageable pageable);
//	
	long countBySrcLikeOrDstLike(String emailLike, String usernameLike);
}
