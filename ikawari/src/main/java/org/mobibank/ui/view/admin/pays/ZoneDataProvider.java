package org.mobibank.ui.view.admin.pays;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Zone;
import org.mobibank.backend.services.ZoneService;
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
public class ZoneDataProvider extends FilterablePageableDataProvider<Zone, Object> {

	/**
	 * 
	 */
	private transient ZoneService zoneService;
	
	protected ZoneService getUserService() {
		if (zoneService == null) {
			zoneService = BeanLocator.find(ZoneService.class);
		}
		return zoneService;
	}
	
	@Override
	protected Page<Zone> fetchFromBackEnd(Query<Zone, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("code", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Zone, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

}
