package org.mobibank.payme.backend;

import java.util.Collection;
import java.util.List;

import org.mobibank.payme.backend.data.entity.MyPayme;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.numerate.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPaymeRepository extends JpaRepository<MyPayme, Long>  {


	List<MyPayme> findByModuleAndPays(Module module, Pays pays);
	
	List<MyPayme> findByPays(Pays pays);
	
	List<MyPayme> findAll();
	
	Page<MyPayme> findBy(Pageable pageable);

	Page<MyPayme> findByNameLikeIgnoreCase(String nameLike, Pageable pageable);
	
	long countByNameLikeIgnoreCase(String nameLike);

	Collection<MyPayme> findByPaysAndAutoriseProfile(Pays pays, String role);
}
