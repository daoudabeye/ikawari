package org.mobibank.payme.ui.view.admin.tarrifs;

import org.mobibank.payme.backend.data.entity.Corridore;
import org.mobibank.payme.backend.data.entity.Pays;
import org.mobibank.payme.backend.data.entity.Zone;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class CorridoreViewDesign extends AbstractCrudViewDesign<Corridore> {

	/**
	 * 
	 */
	protected Grid<Corridore> list;
	protected ComboBox<Zone> zoneSrc;
	protected ComboBox<Zone> zoneDst;
	protected ComboBox<Pays> paysSrc;
	protected ComboBox<Pays> paysDst;
	protected TextField codePromo;
	
	public CorridoreViewDesign() {
		list = new Grid<Corridore>(Corridore.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("zoneSrc", "zoneDst","paysSrc", "paysDst", "codePromo");
		addGrid(list);

		zoneSrc = new ComboBox<Zone>("Zone Source");
		zoneDst = new ComboBox<Zone>("Zone Destination");
		paysSrc = new ComboBox<Pays>("Pays Source");
		paysDst = new ComboBox<Pays>("Pays Destination");
		codePromo = new TextField("code Promo");
		
		addField(zoneSrc);
		addField(zoneDst);
		addField(paysSrc);
		addField(paysDst);
		addField(codePromo);
	}
	
	public void bindForm(BeanValidationBinder<Corridore> binder){
		binder.forField(zoneSrc)
		.bind(Corridore::getZoneSrc, Corridore::setZoneSrc);
		binder.forField(zoneDst)
		.bind(Corridore::getZoneDst, Corridore::setZoneDst);
		binder.forField(paysSrc)
		.bind(Corridore::getPaysSrc, Corridore::setPaysSrc);
		binder.forField(paysDst)
		.bind(Corridore::getPaysDst, Corridore::setPaysDst);
		binder.forField(codePromo)
		.bind(Corridore::getCodePromo, Corridore::setCodePromo);
	}
}
