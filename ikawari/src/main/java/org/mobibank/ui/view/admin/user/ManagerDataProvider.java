package org.mobibank.ui.view.admin.user;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Manager;
import org.mobibank.backend.services.ManagerService;
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
public class ManagerDataProvider extends FilterablePageableDataProvider<Manager, Object> {

	/**
	 * 
	 */
	private transient ManagerService service;

	protected ManagerService getUserService() {
		if (service == null) {
			service = BeanLocator.find(ManagerService.class);
		}
		return service;
	}

	@Override
	protected Page<Manager> fetchFromBackEnd(Query<Manager, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("username", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Manager, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
}
