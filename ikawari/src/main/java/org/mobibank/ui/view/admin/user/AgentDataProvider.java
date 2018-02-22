package org.mobibank.ui.view.admin.user;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.services.AgentService;
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
public class AgentDataProvider extends FilterablePageableDataProvider<Agent, Object> {

	/**
	 * 
	 */
	private transient AgentService service;

	protected AgentService getUserService() {
		if (service == null) {
			service = BeanLocator.find(AgentService.class);
		}
		return service;
	}

	@Override
	protected Page<Agent> fetchFromBackEnd(Query<Agent, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("username", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Agent, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
}
