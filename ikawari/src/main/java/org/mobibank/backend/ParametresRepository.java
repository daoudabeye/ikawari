package org.mobibank.backend;

import java.util.List;

import org.mobibank.backend.data.entity.Parametres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametresRepository extends CrudRepository<Parametres, Long> {

	List<Parametres> findAll();
	
}
