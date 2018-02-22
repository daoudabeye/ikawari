package org.mobibank.payme.backend;

import java.util.List;

import org.mobibank.payme.backend.data.entity.Parametres;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametresRepository extends CrudRepository<Parametres, Long> {

	List<Parametres> findAll();
	
}
