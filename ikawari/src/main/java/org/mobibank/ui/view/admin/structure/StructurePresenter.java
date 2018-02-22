package org.mobibank.ui.view.admin.structure;

import java.io.Serializable;

import org.mobibank.backend.data.entity.Structure;
import org.mobibank.backend.services.StructureService;
import org.mobibank.backend.services.UtilisateurService;
import org.mobibank.ui.AbstractCrudPresenter;
import org.mobibank.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class StructurePresenter extends AbstractCrudPresenter<Structure, StructureService, StructureView>
		implements Serializable {
	
	/**
	 * 
	 */
	UtilisateurService utilisateurService;

	@Autowired
	public StructurePresenter(StructureDataProvider userDataProvider, NavigationManager navigationManager,
			StructureService service, UtilisateurService utilisateurService) {
		super(navigationManager, service, userDataProvider);
		this.utilisateurService = utilisateurService;
	}
	
	public String encodePassword(String value) {
		return utilisateurService.encodePassword(value);
	}

	@Override
	protected void editItem(Structure item) {
		super.editItem(item);
		getView().addPaysSelecteListener();
		getView().setPasswordRequired(item.isNew());
	}
	
}