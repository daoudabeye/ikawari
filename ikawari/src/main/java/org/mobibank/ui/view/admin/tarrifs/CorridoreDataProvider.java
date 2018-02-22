package org.mobibank.ui.view.admin.tarrifs;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Corridore;
import org.mobibank.backend.services.CorridoreService;
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
public class CorridoreDataProvider extends FilterablePageableDataProvider<Corridore, Object> {

	/**
	 * 
	 */
	private transient CorridoreService service;

	protected CorridoreService getUserService() {
		if (service == null) {
			service = BeanLocator.find(CorridoreService.class);
		}
		return service;
	}

	@Override
	protected Page<Corridore> fetchFromBackEnd(Query<Corridore, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("zoneSrc", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Corridore, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
}
