package org.mobibank.payme.ui.view.admin.services;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.backend.services.OrdresService;
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
public class OrdresDataProvider extends FilterablePageableDataProvider<Ordres, Object> {

	/**
	 * 
	 */
	private transient OrdresService service;

	protected OrdresService getUserService() {
		if (service == null) {
			service = BeanLocator.find(OrdresService.class);
		}
		return service;
	}

	@Override
	protected Page<Ordres> fetchFromBackEnd(Query<Ordres, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Ordres, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

}
