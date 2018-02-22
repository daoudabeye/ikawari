package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Ordres;
import org.mobibank.payme.backend.numerate.CommissionType;
import org.mobibank.payme.backend.numerate.Context;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.numerate.SchemaCompte;
import org.mobibank.payme.backend.numerate.ServiceParams;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class OrdresViewDesign  extends AbstractCrudViewDesign<Ordres>{

	protected Grid<Ordres> list;
	TextField name;
	ComboBox<Module> module;
	TwinColSelect<String> autoriseProfile;
	TwinColSelect<String> profileBeneficiaire;
	TwinColSelect<ServiceParams> params;
	TwinColSelect<CommissionType> commissions;
	TwinColSelect<SchemaCompte> comptes;
	TwinColSelect<Context> context;
	CheckBox national;
	
	public OrdresViewDesign() {

		list = new Grid<Ordres>(Ordres.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("name", "module", "national");
		addGrid(list);
		
		name = new TextField("Name");
		module = new ComboBox<Module>("Module");
		module.setItems(Module.asListOrders());
		
		autoriseProfile = new TwinColSelect<>("Autorisé");
		autoriseProfile.setItems(Role.getAllRoles());
		
		profileBeneficiaire = new TwinColSelect<>("Bénef");
		profileBeneficiaire.setItems(Role.getAllRoles());
		
		national = new CheckBox("Nationnal");
		params = new TwinColSelect<>("Params");
		params.setItems(ServiceParams.asList());
		
		commissions = new TwinColSelect<>("Commissions");
		commissions.setItems(CommissionType.asList());
		
		comptes = new TwinColSelect<>("Comptes");
		comptes.setItems(SchemaCompte.asList());
		
		context = new TwinColSelect<>("Context");
		context.setItems(Context.asList());

		
		addField(name);
		addField(module);
		addField(national);
		addField(national);
		addField(autoriseProfile);
		addField(profileBeneficiaire);
		addField(params);
		addField(commissions);
		addField(comptes);
		addField(context);
	}
	
	public void bindForm(BeanValidationBinder<Ordres> binder){
		binder.forField(name)
		.bind(Ordres::getName, Ordres::setName);
		binder.forField(module)
		.bind(Ordres::getModule, Ordres::setModule);
		binder.forField(autoriseProfile)
		.bind(Ordres::getAutoriseProfile, Ordres::setAutoriseProfile);
		binder.forField(profileBeneficiaire)
		.bind(Ordres::getProfileBeneficiaire, Ordres::setProfileBeneficiaire);
		binder.forField(params)
		.bind(Ordres::getParam, Ordres::setParams);
		binder.forField(commissions)
		.bind(Ordres::getCommission, Ordres::setCommissions);
		binder.forField(comptes)
		.bind(Ordres::getComptes, Ordres::setComptes);
		binder.forField(context)
		.bind(Ordres::getContext, Ordres::setContext);
		binder.forField(national)
		.bind(Ordres::getNational, Ordres::setNational);
	}
}
