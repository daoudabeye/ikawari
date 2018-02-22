package org.mobibank.ui.view.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mobibank.backend.data.entity.Services;
import org.mobibank.backend.numerate.CommissionType;
import org.mobibank.backend.numerate.ServiceParams;
import org.mobibank.backend.services.AppService;
import org.mobibank.ui.RoleSelect;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * 
 * @author BEYE
 *
 */
@SuppressWarnings("serial")
public class ParametreDesign extends CssLayout {
	Label titre = new Label("PARAMETRES SYSTEME");

	RoleSelect profiles = new RoleSelect();
	CssLayout profile = new CssLayout();
	CssLayout profileButton = new CssLayout();
	CssLayout profileWrapper;

	List<CssLayout> profileWraperItems = new ArrayList<>();

	ComboBox<Services> services = new ComboBox<>();
	CssLayout serviceButton = new CssLayout();
	CssLayout service = new CssLayout();
	CssLayout serviceWrapper;
	Services selectedService;
	Map<ServiceParams, String> serviceParam;
	Map<ServiceParams, TextField> serviceFieldMap = new HashMap<>();
	List<CssLayout> serviceWraperItems = new ArrayList<>();

	CssLayout commission = new CssLayout();
	CssLayout commissionButton = new CssLayout();
	ComboBox<Services> commissions = new ComboBox<>();
	CssLayout commissionWrapper;
	Services selectedCommissionService;
	Map<CommissionType, Integer> commissionsParam;
	Map<CommissionType, TextField> commissionsFieldMap = new HashMap<>();
	List<CssLayout> commissionsWraperItems = new ArrayList<>();

	AppService appService;

	public ParametreDesign( AppService appService) {
		setStyleName("form-template");
		setResponsive(true);
		setSizeFull();

		this.appService = appService;

		titre.setStyleName("title");
		titre.setWidth("100%");
		addComponent(titre);

		CssLayout profileContent = new CssLayout(profiles);
		profileContent.addStyleName("input-wrapper");
		profileContent.addStyleName("align-bottom");

		profileWrapper = new CssLayout(profileContent);
		profileWrapper.setStyleName("input-group");

		CssLayout profileButtonWrapper = new CssLayout(profileButton);
		profileButtonWrapper.setStyleName("input-group");

		profile.setStyleName("form-section");
		profile.setCaption("PROFILE");
		profile.addComponent(profileWrapper);
		profile.addComponent(profileButtonWrapper);


		addComponent(profile);

		CssLayout serviceContent = new CssLayout(services);
		serviceContent.addStyleName("input-wrapper");
		serviceContent.addStyleName("align-bottom");

		serviceWrapper = new CssLayout(serviceContent);
		serviceWrapper.setStyleName("input-group");

		Button serviceButton = new Button("Enregistrer Service");
		serviceButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		serviceButton.addClickListener(e -> saveService());

		CssLayout serviceButtonWrapper = new CssLayout(serviceButton);
		serviceButtonWrapper.setStyleName("input-group");

		service.setStyleName("form-section");
		service.setCaption("SERVICES");
		service.addComponent(serviceWrapper);
		service.addComponent(serviceButtonWrapper);

		List<Services> liste = appService.getRepository().findAll();
		services.setItems(liste);
		services.addValueChangeListener(e -> {
			if(e.getValue() != null){
				selectedService = e.getValue();
				setServiceVisibleField(selectedService);
			}
		});

		addComponent(service);

		CssLayout commissionContent = new CssLayout(commissions);
		commissionContent.addStyleName("input-wrapper");
		commissionContent.addStyleName("align-bottom");

		commissionWrapper = new CssLayout(commissionContent);
		commissionWrapper.setStyleName("input-group");

		Button commissionButton = new Button("Enregistrer Commission");
		commissionButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		commissionButton.addClickListener(e -> saveCommissions());

		CssLayout commissionButtonWrapper = new CssLayout(commissionButton);
		commissionButtonWrapper.setStyleName("input-group");

		commission.setStyleName("form-section");
		commission.setCaption("COMMISSIONS");
		commission.addComponent(commissionWrapper);
		commission.addComponent(commissionButtonWrapper);

		commissions.setItems(liste);
		commissions.addValueChangeListener(e -> {
			if(e.getValue() != null){
				this.selectedCommissionService = e.getValue();
				setCommissionsVisibleField(selectedCommissionService);
			}
		});

		addComponent(commission);

	}

	//***************************
	//Service 
	//**************************
	private void setServiceVisibleField(Services value) {
		serviceWraperItems.forEach(e -> serviceWrapper.removeComponent(e));
		serviceWraperItems = new ArrayList<>();
		serviceFieldMap = new HashMap<>();

		serviceParam = selectedService.getParams();
		serviceParam.forEach((k, v) -> {
			serviceWrapper.addComponent(generateServiceParamField(k, v));
		});
	}

	private CssLayout generateServiceParamField(ServiceParams parametre, String value){
		TextField field = new TextField(parametre.name());
		field.setValue(value);
		serviceFieldMap.put(parametre, field);
		CssLayout fieldWrapper = new CssLayout(field);
		fieldWrapper.setStyleName("input-wrapper");
		serviceWraperItems.add(fieldWrapper);
		return fieldWrapper;
	}

	private void saveService(){
		if(selectedService != null && serviceParam != null){
			serviceFieldMap.forEach((k, v) -> {
				serviceParam.replace(k, v.getValue());
			});
			selectedService.setParams(serviceParam);
			appService.update(selectedService);
			Notification.show("Enregistré avec succès");
		}
	}

	//*******************************
	//Commissions params
	//*******************************
	void setCommissionsVisibleField(Services value) {

		//remove visble componant.
		commissionsWraperItems.forEach(e -> commissionWrapper.removeComponent(e));
		commissionsWraperItems = new ArrayList<>();
		commissionsFieldMap = new HashMap<>();

		commissionsParam = selectedCommissionService.getCommissions();
		commissionsParam.forEach((k, v) -> {
			commissionWrapper.addComponent(generateCommissionParamField(k, v));
		});
	}

	private CssLayout generateCommissionParamField(CommissionType k, Integer v) {
		TextField field = new TextField(k.name());
		field.setValue(v.toString());
		commissionsFieldMap.put(k, field);
		CssLayout fieldWrapper = new CssLayout(field);
		fieldWrapper.setStyleName("input-wrapper");
		commissionsWraperItems.add(fieldWrapper);
		return fieldWrapper;
	}

	private void saveCommissions(){
		if(selectedCommissionService != null && commissionsParam != null){
			commissionsFieldMap.forEach((k, v) -> {
				commissionsParam.replace(k, Integer.valueOf(v.getValue()));
			});
			selectedCommissionService.setCommissions(commissionsParam);
			appService.update(selectedCommissionService);
			Notification.show("Enregistré avec succès");
		}
	}

}
