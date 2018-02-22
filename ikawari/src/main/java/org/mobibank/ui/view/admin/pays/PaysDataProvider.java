package org.mobibank.ui.view.admin.pays;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.services.PaysService;
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
public class PaysDataProvider extends FilterablePageableDataProvider<Pays, Object> {

	/**
	 * 
	 */
	private transient PaysService paysService;
	
	protected PaysService getUserService() {
		if (paysService == null) {
			paysService = BeanLocator.find(PaysService.class);
		}
		return paysService;
	}
	
	@Override
	protected Page<Pays> fetchFromBackEnd(Query<Pays, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("nom", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<Pays, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

}
