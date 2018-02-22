package org.mobibank.payme.ui.view.admin.services;

import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Services;
import org.mobibank.payme.backend.numerate.CommissionType;
import org.mobibank.payme.backend.numerate.Context;
import org.mobibank.payme.backend.numerate.Module;
import org.mobibank.payme.backend.numerate.SchemaCompte;
import org.mobibank.payme.backend.numerate.ServiceParams;
import org.mobibank.payme.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class ServiceViewDesign  extends AbstractCrudViewDesign<Services>{

	protected Grid<Services> list;
	TextField name;
	ComboBox<Module> module;
	TwinColSelect<String> autoriseProfile;
	TwinColSelect<String> profileBeneficiaire;
	TwinColSelect<ServiceParams> params;
	TwinColSelect<CommissionType> commissions;
	TwinColSelect<SchemaCompte> comptes;
	TwinColSelect<Context> context;
	
	public ServiceViewDesign() {

		list = new Grid<Services>(Services.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("name", "module", "fee", "pays");
		addGrid(list);
		
		name = new TextField("Name");
		module = new ComboBox<Module>("Module");
		module.setItems(Module.asList());
		
		autoriseProfile = new TwinColSelect<>("Autorisé");
		autoriseProfile.setItems(Role.getAllRoles());
		
		profileBeneficiaire = new TwinColSelect<>("Bénef");
		profileBeneficiaire.setItems(Role.getAllRoles());
		
//		fee = new CheckBox("Frais");
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
		addField(autoriseProfile);
		addField(profileBeneficiaire);
//		addField(fee);
		addField(params);
		addField(commissions);
		addField(comptes);
		addField(context);
	}
	
	public void bindForm(BeanValidationBinder<Services> binder){
		binder.forField(name)
		.bind(Services::getName, Services::setName);
		binder.forField(module)
		.bind(Services::getModule, Services::setModule);
		binder.forField(autoriseProfile)
		.bind(Services::getAutoriseProfile, Services::setAutoriseProfile);
		binder.forField(profileBeneficiaire)
		.bind(Services::getProfileBeneficiaire, Services::setProfileBeneficiaire);
//		binder.forField(fee)
//		.bind(Services::getFee, Services::setFee);
		binder.forField(params)
		.bind(Services::getParam, Services::setParams);
		binder.forField(commissions)
		.bind(Services::getCommission, Services::setCommissions);
		binder.forField(comptes)
		.bind(Services::getComptes, Services::setComptes);
		binder.forField(context)
		.bind(Services::getContext, Services::setContext);
//		binder.forField(pays)
//		.bind(Services::getPays, Services::setPays);
	}
}
