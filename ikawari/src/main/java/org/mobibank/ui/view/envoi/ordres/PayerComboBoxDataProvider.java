package org.mobibank.ui.view.envoi.ordres;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Agent;
import org.mobibank.backend.services.AgentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;

/**
 * A singleton data provider which knows which products are available.
 */
@SpringComponent
public class PayerComboBoxDataProvider extends PageableDataProvider<Agent, String> {

	private transient AgentService agentService;
	
	protected AgentService getAgentService() {
		if (agentService == null) {
			agentService = BeanLocator.find(AgentService.class);
		}
		return agentService;
	}
	
	@Override
	protected Page<Agent> fetchFromBackEnd(Query<Agent, String> query, Pageable pageable) {
		return getAgentService().findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("nom", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Agent, String> query) {
		return (int) getAgentService().countAnyMatching(query.getFilter());
	}

}
