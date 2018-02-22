package org.mobibank.ui.view.admin.tarrifs;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.GrilleWallet;
import org.mobibank.backend.services.GrilleWalletService;
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
public class GrilleWalletDataProvider extends FilterablePageableDataProvider<GrilleWallet, Object> {

	/**
	 * 
	 */
	private transient GrilleWalletService service;

	protected GrilleWalletService getUserService() {
		if (service == null) {
			service = BeanLocator.find(GrilleWalletService.class);
		}
		return service;
	}

	@Override
	protected Page<GrilleWallet> fetchFromBackEnd(Query<GrilleWallet, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("pays", SortDirection.ASCENDING));
		return sortOrders;
	}

	@Override
	protected int sizeInBackEnd(Query<GrilleWallet, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}
	
}
