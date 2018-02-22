package org.mobibank.payme.ui.view.admin.services;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.backend.services.AppService;
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
public class ServiceDataProvider extends FilterablePageableDataProvider<Services, Object> {

	/**
	 * 
	 */
	private transient AppService service;

	protected AppService getUserService() {
		if (service == null) {
			service = BeanLocator.find(AppService.class);
		}
		return service;
	}

	@Override
	protected Page<Services> fetchFromBackEnd(Query<Services, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Services, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

}
