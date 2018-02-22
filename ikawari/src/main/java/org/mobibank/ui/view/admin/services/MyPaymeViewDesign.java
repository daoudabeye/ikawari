package org.mobibank.ui.view.admin.services;

import org.mobibank.backend.data.Role;
import org.mobibank.backend.data.entity.MyPayme;
import org.mobibank.backend.data.entity.Pays;
import org.mobibank.backend.numerate.CommissionType;
import org.mobibank.backend.numerate.Context;
import org.mobibank.backend.numerate.Module;
import org.mobibank.backend.numerate.SchemaCompte;
import org.mobibank.backend.numerate.ServiceParams;
import org.mobibank.ui.AbstractCrudViewDesign;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

public class MyPaymeViewDesign  extends AbstractCrudViewDesign<MyPayme>{

	protected Grid<MyPayme> list;
	TextField name;
	ComboBox<Module> module;
	TwinColSelect<String> autoriseProfile;
	TwinColSelect<String> profileBeneficiaire;
	TwinColSelect<ServiceParams> params;
	TwinColSelect<CommissionType> commissions;
	TwinColSelect<SchemaCompte> comptes;
	TwinColSelect<Context> context;
	CheckBox fee;
	ComboBox<Pays> pays;
	
	public MyPaymeViewDesign() {

		list = new Grid<MyPayme>(MyPayme.class);
		list.setSizeFull();
		list.setColumnResizeMode(ColumnResizeMode.ANIMATED);
		list.setColumns("name", "module", "fee", "pays");
		addGrid(list);
		
		name = new TextField("Name");
		module = new ComboBox<Module>("Module");
		module.setItems(Module.asListOperation());
		
		pays = new ComboBox<>("Pays");
		
		autoriseProfile = new TwinColSelect<>("Autorisé");
		autoriseProfile.setItems(Role.getAllRoles());
		
		profileBeneficiaire = new TwinColSelect<>("Bénef");
		profileBeneficiaire.setItems(Role.getAllRoles());
		
		fee = new CheckBox("Frais");
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
		addField(pays);
		addField(fee);
		addField(autoriseProfile);
		addField(profileBeneficiaire);
		addField(params);
		addField(commissions);
		addField(comptes);
		addField(context);
	}
	
	public void bindForm(BeanValidationBinder<MyPayme> binder){
		binder.forField(name)
		.bind(MyPayme::getName, MyPayme::setName);
		binder.forField(module)
		.bind(MyPayme::getModule, MyPayme::setModule);
		binder.forField(autoriseProfile)
		.bind(MyPayme::getAutoriseProfile, MyPayme::setAutoriseProfile);
		binder.forField(profileBeneficiaire)
		.bind(MyPayme::getProfileBeneficiaire, MyPayme::setProfileBeneficiaire);
		binder.forField(fee)
		.bind(MyPayme::getFee, MyPayme::setFee);
		binder.forField(params)
		.bind(MyPayme::getParam, MyPayme::setParams);
		binder.forField(commissions)
		.bind(MyPayme::getCommission, MyPayme::setCommissions);
		binder.forField(comptes)
		.bind(MyPayme::getComptes, MyPayme::setComptes);
		binder.forField(context)
		.bind(MyPayme::getContext, MyPayme::setContext);
		binder.forField(pays)
		.bind(MyPayme::getPays, MyPayme::setPays);
	}
}
