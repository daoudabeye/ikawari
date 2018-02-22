package org.mobibank.ui.view.admin.services;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.MyPayme;
import org.mobibank.backend.services.MyPaymeService;
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
public class MyPaymeDataProvider extends FilterablePageableDataProvider<MyPayme, Object> {

	/**
	 * 
	 */
	private transient MyPaymeService service;

	protected MyPaymeService getUserService() {
		if (service == null) {
			service = BeanLocator.find(MyPaymeService.class);
		}
		return service;
	}

	@Override
	protected Page<MyPayme> fetchFromBackEnd(Query<MyPayme, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<MyPayme, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

}
