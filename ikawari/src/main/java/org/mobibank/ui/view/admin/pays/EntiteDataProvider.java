package org.mobibank.ui.view.admin.pays;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Entite;
import org.mobibank.backend.services.EntiteService;
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
public class EntiteDataProvider extends FilterablePageableDataProvider<Entite, Object> {

	private transient EntiteService service;

	protected EntiteService getUserService() {
		if (service == null) {
			service = BeanLocator.find(EntiteService.class);
		}
		return service;
	}

	@Override
	protected Page<Entite> fetchFromBackEnd(Query<Entite, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("designation", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Entite, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

}
