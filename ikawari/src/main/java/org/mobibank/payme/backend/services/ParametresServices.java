package org.mobibank.payme.backend.services;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.ParametresRepository;
import org.springframework.stereotype.Service;

@Service
public class ParametresServices {

	public ParametresRepository getRepository() {
		return BeanLocator.find(ParametresRepository.class);
	}
}
