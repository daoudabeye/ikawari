package org.mobibank.payme.backend.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.CompteRepository;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Compte;
import org.mobibank.payme.backend.data.entity.Transaction;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.numerate.Devise;
import org.mobibank.payme.backend.numerate.TypeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CompteService implements CrudService<Compte> {

	@Override
	public CompteRepository getRepository() {
		return BeanLocator.find(CompteRepository.class);
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByUtilisateurUsernameLike(repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public Page<Compte> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByUtilisateurUsernameLike(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<Compte> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	public void create(Utilisateur utilisateur, Devise devise){
		switch (utilisateur.getRole()) {

		case Role.DISTRIBUTEUR:
		case Role.MASTER:
		case Role.POINTSERVICE:
			createPrincipaleRevenu( utilisateur, devise );
			break;

		case Role.BANQUE:
			createBanqueComptes( utilisateur, devise);
			break;

		case Role.PROVIDER:
			createProviderComptes(utilisateur, devise);
			break;

		case Role.CLIENT:
			createClient(utilisateur, devise);
			break;

		default:
			break;
		}
	}

	public void createBanqueComptes(Utilisateur utilisateur, Devise devise){
		//Compte d'attente de retrait
		this.save(new Compte(TypeCompte.ATTENTE, devise, utilisateur));
		//Compte d'attente de destruction
		this.save(new Compte(TypeCompte.DESTRUCTION, devise, utilisateur));
		//Compte commission
		this.save(new Compte(TypeCompte.COMMISSION, devise, utilisateur));
		//Taxes
		this.save(new Compte(TypeCompte.TAXES, devise, utilisateur));

		this.save(new Compte(TypeCompte.PIVOT, devise, utilisateur));

//		this.save(new Compte(TypeCompte.PRINCIPALE, devise, utilisateur));
	}

	public void createProviderComptes(Utilisateur utilisateur, Devise devise){

		//		this.save(new Compte(TypeCompte.ATTENTE, devise, utilisateur));

		this.save(new Compte(TypeCompte.COMMISSION, devise, utilisateur));

		this.save(new Compte(TypeCompte.MARKTING, devise, utilisateur));
	}

	public void createPrincipaleRevenu(Utilisateur utilisateur, Devise devise){
		//Compte de revenu;
		this.save(new Compte(TypeCompte.COMMISSION, devise, utilisateur));
		//Compte principale
		this.save(new Compte(TypeCompte.PRINCIPALE, devise, utilisateur));
	}

	public void createClient(Utilisateur utilisateur, Devise devise){
		//Compte principale
		this.save(new Compte(TypeCompte.PRINCIPALE, devise, utilisateur));
	}

	public Collection<Compte> find(Utilisateur utilisateur) {
		return getRepository().findByUtilisateur(utilisateur);
	}
	
	public Compte findOne(Utilisateur utilisateur, TypeCompte type) {
		return getRepository().findFirst1ByUtilisateurAndType(utilisateur, type);
	}

	public void updateSolde(Transaction transaction) {
		Compte compte = transaction.getCompte();
		if(compte != null){
			BigDecimal montant = BigDecimal.valueOf(transaction.getMontant());
			if(transaction.isValidate())
				switch (transaction.getType()) {
				case DEBIT:
					Assert.isTrue(compte.getSolde().compareTo(montant) >= 0, "Solde inssufisant pour cette opération : "+compte.getType());
					compte.debiter(montant);
					getRepository().save(compte);
					break;

				case CREDIT:
					compte.crediter(montant);
					getRepository().save(compte);
					break;
				}
		}
	}
}
