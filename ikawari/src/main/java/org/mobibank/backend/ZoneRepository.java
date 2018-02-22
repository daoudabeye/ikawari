package org.mobibank.backend;

import java.util.List;

import org.mobibank.backend.data.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ZoneRepository extends CrudRepository<Zone, Long>{

	List<Zone> findAll();
	
	Zone findOneByDesignation(String designation);
	
	Zone findOneByCode(String code);
	
	@Query("SELECT z FROM Zone z WHERE z.designation LIKE :key OR z.code LIKE :key")
	List<Zone> find(@Param("key")String key);
	
	@Modifying
	@Query("Update Zone z set z.designation=:designation, z.code=:code WHERE z.id=:id")
	int update(@Param("id")Long id, @Param("designation")String designation, @Param("code")String code);
	
	Page<Zone> findBy(Pageable pageable);

	Page<Zone> findByDesignationLikeIgnoreCaseOrCodeLikeIgnoreCase(String designationLike, String codeLike, Pageable pageable);
	
	long countByDesignationLikeIgnoreCaseOrCodeLikeIgnoreCase(String designationLike, String codeLike);
}
