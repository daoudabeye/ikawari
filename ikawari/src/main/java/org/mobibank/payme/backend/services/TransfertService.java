package org.mobibank.payme.backend.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.TransfertRepository;
import org.mobibank.payme.backend.data.entity.Transfert;
import org.mobibank.payme.backend.numerate.StatutTransfert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransfertService implements CrudService<Transfert> {

	@Override
	public TransfertRepository getRepository() {
		return BeanLocator.find(TransfertRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByFolioLikeIgnoreCase(repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Transfert> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			Long mtcn = null;
			try {
				mtcn = Long.valueOf(filter.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByFolioLikeIgnoreCaseOrMtcnLike(repositoryFilter, mtcn, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Transfert> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}
	
	public Page<Transfert> findAnyMatchingAfterDateEnvoi(Optional<String> optionalFilter,
			Optional<LocalDate> optionalFilterDate, Pageable pageable) {
		if (optionalFilter.isPresent()) {
			if (optionalFilterDate.isPresent()) {
				return getRepository().findByMtcnAndDateEnvoiAfter(
						Long.valueOf(optionalFilter.get()), optionalFilterDate.get(), pageable);
			} else {
				return  getRepository().findByMtcn(Long.valueOf(optionalFilter.get()), pageable);
			}
		} else {
			if (optionalFilterDate.isPresent()) {
				return getRepository().findByDateEnvoiAfter(optionalFilterDate.get(), pageable);
			} else {
				return getRepository().findAll(pageable);
			}
		}
	}
	
	public long countAnyMatchingAfterDateEnvoi(Optional<String> optionalFilter, Optional<LocalDate> optionalFilterDate) {
		if (optionalFilter.isPresent() && optionalFilterDate.isPresent()) {
			return getRepository().countByMtcnAndDateEnvoiAfter(Long.valueOf(optionalFilter.get()),
					optionalFilterDate.get());
		} else if (optionalFilter.isPresent()) {
			return getRepository().countByMtcn(Long.valueOf(optionalFilter.get()));
		} else if (optionalFilterDate.isPresent()) {
			return getRepository().countByDateEnvoiAfter(optionalFilterDate.get());
		} else {
			return getRepository().count();
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public Transfert saveOrder(Transfert order) {
//		Customer customer = getCustomerRepository().save(order.getCustomer());
//		order.setCustomer(customer);
//
//		if (order.getHistory() == null) {
//			String comment = "Order placed";
//			order.setHistory(new ArrayList<>());
//			HistoryItem item = new HistoryItem(getUserService().getCurrentUser(), comment);
//			item.setNewState(OrderState.NEW);
//			order.getHistory().add(item);
//		}
//
//		return getOrderRepository().save(order);
		return order;
	}
	
	public Transfert findOrder(Long id) {
		// TODO Auto-generated method stub
		return getRepository().findOne(id);
	}

	public void changeState(Transfert ordre, StatutTransfert statutTransfert) {
		// TODO Auto-generated method stub
		
	}
	
}
