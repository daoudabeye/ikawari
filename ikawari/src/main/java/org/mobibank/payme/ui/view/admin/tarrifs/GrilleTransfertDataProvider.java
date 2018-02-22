package org.mobibank.payme.ui.view.admin.tarrifs;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.data.entity.GrilleTransfert;
import org.mobibank.payme.backend.services.GrilleTransfertService;
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
public class GrilleTransfertDataProvider extends FilterablePageableDataProvider<GrilleTransfert, Object> {

	/**
	 * 
	 */
	private transient GrilleTransfertService service;

	protected GrilleTransfertService getUserService() {
		if (service == null) {
			service = BeanLocator.find(GrilleTransfertService.class);
		}
		return service;
	}

	@Override
	protected Page<GrilleTransfert> fetchFromBackEnd(Query<GrilleTransfert, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("corridore", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<GrilleTransfert, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
	
}
