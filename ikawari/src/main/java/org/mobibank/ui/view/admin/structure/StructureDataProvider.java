package org.mobibank.ui.view.admin.structure;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.BeanLocator;
import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.services.StructureService;
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
public class StructureDataProvider extends FilterablePageableDataProvider<Structure, Object> {

	/**
	 * 
	 */
	private transient StructureService structureService;

	@Override
	protected Page<Structure> fetchFromBackEnd(Query<Structure, Object> query, Pageable pageable) {
		return getUserService().findAnyMatching(getOptionalFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Structure, Object> query) {
		return (int) getUserService().countAnyMatching(getOptionalFilter());
	}

	protected StructureService getUserService() {
		if (structureService == null) {
			structureService = BeanLocator.find(StructureService.class);
		}
		return structureService;
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("designation", SortDirection.ASCENDING));
		return sortOrders;
	}

}