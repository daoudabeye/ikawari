package org.mobibank.ui.view.admin.user;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Client;
import org.mobibank.backend.services.ClientService;
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
public class ClientDataProvider extends FilterablePageableDataProvider<Client, Object> {

	/**
	 * 
	 */
	private transient ClientService service;

	protected ClientService getUserService() {
		if (service == null) {
			service = BeanLocator.find(ClientService.class);
		}
		return service;
	}

	@Override
	protected Page<Client> fetchFromBackEnd(Query<Client, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("nom", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Client, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
	
}
