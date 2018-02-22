package org.mobibank.ui.view.admin.tarrifs;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Change;
import org.mobibank.backend.services.ChangeService;
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
public class DeviseDataProvider extends FilterablePageableDataProvider<Change, Object> {

	/**
	 * 
	 */
	private transient ChangeService service;

	protected ChangeService getUserService() {
		if (service == null) {
			service = BeanLocator.find(ChangeService.class);
		}
		return service;
	}

	@Override
	protected Page<Change> fetchFromBackEnd(Query<Change, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("src", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Change, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
	
}
