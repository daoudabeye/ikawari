package org.mobibank.backend;

import java.util.List;

import org.mobibank.backend.data.entity.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services, Long> {

//	Services findByPaysAndModule(Pays pays, Module service);

//	List<Services> findByModuleAndPays(Module module, Pays pays);
	
//	List<Services> findByPays(Pays pays);
	
	List<Services> findAll();
	
	Page<Services> findBy(Pageable pageable);

	Page<Services> findByNameLikeIgnoreCase(String nameLike, Pageable pageable);
	
	long countByNameLikeIgnoreCase(String nameLike);

//	Collection<Services> findByPaysAndAutoriseProfile(Pays pays, String role);
}
