package org.mobibank.backend;

import java.util.Collection;
import java.util.List;

import org.mobibank.backend.data.entity.Ordres;
import org.mobibank.backend.numerate.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdresRepository extends JpaRepository<Ordres, Long>  {


	Collection<Ordres> findByModuleAndAutoriseProfile(Module module, String profile);
	
	List<Ordres> findByModuleAndNational(Module module, Boolean national);
	
	List<Ordres> findAll();
	
	Page<Ordres> findBy(Pageable pageable);

	Page<Ordres> findByNameLikeIgnoreCase(String nameLike, Pageable pageable);
	
	long countByNameLikeIgnoreCase(String nameLike);

}
