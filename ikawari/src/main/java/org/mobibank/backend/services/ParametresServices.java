package org.mobibank.backend.services;

import org.mobibank.BeanLocator;
import org.mobibank.backend.ParametresRepository;
import org.springframework.stereotype.Service;

@Service
public class ParametresServices {

	public ParametresRepository getRepository() {
		return BeanLocator.find(ParametresRepository.class);
	}
}
