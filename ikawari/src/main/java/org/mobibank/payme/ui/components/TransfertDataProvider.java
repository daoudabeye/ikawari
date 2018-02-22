package org.mobibank.payme.ui.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.Transfert;
import org.mobibank.payme.backend.services.TransfertService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;

@SuppressWarnings("serial")
@SpringComponent
@PrototypeScope
public class TransfertDataProvider extends FilterablePageableDataProvider<Transfert, Object> {

	/**
	 * 
	 */
	private transient TransfertService transfertService;
	private LocalDate filterDate = LocalDate.now().minusDays(1);
	
	@Override
	protected Page<Transfert> fetchFromBackEnd(Query<Transfert, Object> arg0, Pageable pageable) {
		return getTransfertService().findAnyMatchingAfterDateEnvoi(getOptionalFilter(), getOptionalFilterDate(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("dateEnvoi", SortDirection.ASCENDING));
		sortOrders.add(new QuerySortOrder("dateCreation", SortDirection.ASCENDING));
		sortOrders.add(new QuerySortOrder("datePaiement", SortDirection.ASCENDING));
		// id included only to always get a stable sort order
		sortOrders.add(new QuerySortOrder("id", SortDirection.DESCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Transfert, Object> query) {
		return (int) getTransfertService().countAnyMatchingAfterDateEnvoi(getOptionalFilter(), getOptionalFilterDate());
	}
	
	protected TransfertService getTransfertService() {
		if (transfertService == null) {
			transfertService = BeanLocator.find(TransfertService.class);
		}
		return transfertService;
	}
	
	private Optional<LocalDate> getOptionalFilterDate() {
		if (filterDate == null) {
			return Optional.empty();
		} else {
			return Optional.of(filterDate);
		}
	}
	
	public void setIncludePast(boolean includePast) {
		if (includePast) {
			filterDate = null;
		} else {
			filterDate = LocalDate.now().minusDays(1);
		}
	}

}
