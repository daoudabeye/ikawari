package org.mobibank.payme.backend.services;

import java.util.Optional;

import org.mobibank.payme.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author BEYE
 *
 * @param <T>
 */
public interface CrudService<T extends AbstractEntity> {

	CrudRepository<T, Long> getRepository();

	/**
	 * 
	 * @param entity
	 * @return
	 */
	default T save(T entity) {
		return getRepository().save(entity);
	}
	
	/**
	 * 
	 * @param id
	 */
	default void delete(long id) {
		getRepository().delete(id);
	}

	/**
	 * 
	 * @return
	 */
	default long count() {
		return getRepository().count();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	default T load(long id) {
		return getRepository().findOne(id);
	}

	/**
	 * @param filter
	 * @return
	 */
	long countAnyMatching(Optional<String> filter);

	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<T> find(Pageable pageable);

}
