package org.mobibank.payme.ui.view.admin.user;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.Compte;
import org.mobibank.payme.backend.services.CompteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
@PrototypeScope
public class CompteDataProvider extends FilterablePageableDataProvider<Compte, Object> {

	/**
	 * 
	 */
	private transient CompteService service;
	
	protected CompteService getService() {
		if (service == null) {
			service = BeanLocator.find(CompteService.class);
		}
		return service;
	}
	
	@Override
	protected Page<Compte> fetchFromBackEnd(Query<Compte, Object> arg0, Pageable pageable) {
		return getService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("id", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Compte, Object> query) {
		return (int) getService().countAnyMatching(getOptionalFilter());
	}

	
}
