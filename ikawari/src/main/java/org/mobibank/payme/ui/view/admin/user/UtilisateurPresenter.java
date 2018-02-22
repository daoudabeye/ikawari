package org.mobibank.payme.ui.view.admin.user;

import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.services.UtilisateurService;
import org.mobibank.payme.ui.AbstractCrudPresenter;
import org.mobibank.payme.ui.navigation.NavigationManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

@SpringComponent
@ViewScope
public class UtilisateurPresenter extends AbstractCrudPresenter<Utilisateur, UtilisateurService, UtilisateurView> {

	/**
	 * 
	 */

	@Autowired
	public UtilisateurPresenter(UtilisateurDataProvider dataProvider, NavigationManager navigationManager,
			UtilisateurService service) {
		super(navigationManager, service, dataProvider);
	}
	
	public String encodePassword(String value) {
		return getService().encodePassword(value);
	}
	
	@Override
	protected void editItem(Utilisateur item) {
		super.editItem(item);
		getView().setPasswordRequired(item.isNew());
	}
	
}
