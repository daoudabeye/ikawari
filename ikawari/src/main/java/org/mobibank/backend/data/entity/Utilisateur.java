package org.mobibank.backend.data.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mobibank.backend.numerate.ValidationStatut;

/**
 * 
 * @author BEYE
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name = Utilisateur.TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u")
public class Utilisateur extends AbstractEntity {
	public static final String TABLE_NAME = "utilisateur";
	public static final int MAX_TENTATIVE  = 5;

	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(unique = true)
	private String username;

	@Size(max = 255)
	@Column(nullable = true)
	private String telephone;

	@Email(message="Adresse mail incorrect")
	private String email;

	@NotNull
	@NotEmpty
	@Size(min = 4, max = 255)
	private String password;

	private String role;

	@Column(name="last_ip")
	private String lastIp;

	private LocalDateTime lastLogin;

	@Min(value=0, message = "doit etre superieur ou egal à zéro (0)")
	private int passwordLife = 0;

	@Min(value=0, message = "doit etre superieur ou egal à zéro (0)")
	private int tentative = 0;

	private boolean locked = false;

	private boolean blacklisted = false;
	
	private boolean enabled = false;

	//Authentifation en 2 etapes
    private boolean isUsing2FA = true;
    
    private String secret;
    
	//mapping 1:n utilisateur -> createur
	//Liste des comptes crées par un utilisateur
	@OneToMany
	private Collection<Utilisateur> crees;

	//mapping n:1 crees -> createur
	//Utilisateur ayant crée ce compte
	@ManyToOne
	@JoinColumn	
	private Utilisateur createur;

	@OneToMany(mappedBy = "utilisateur")
	private List<Compte> comptes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private Entite entite;

	@ElementCollection(fetch = FetchType.EAGER)
	private Map<String, String> iban;

	@Enumerated
	private ValidationStatut validationStatut;
	
	public Utilisateur() {
		super();
		this.validationStatut = ValidationStatut.INSTANCE;
//		this.secret = Base32.random();
	}

	public Utilisateur(String username, String password, String role) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		Objects.requireNonNull(role);

		this.username = username;
		this.password = password;
		this.role = role;
		this.validationStatut = ValidationStatut.INSTANCE;
	}
	
	public void loginAttempt(){
		this.tentative--;
		if(tentative <= 0)
			this.blacklisted = true;
	}
	
	public void loginSuccess(String ip){
		this.tentative = MAX_TENTATIVE;
		this.lastLogin = LocalDateTime.now();
		this.lastIp = ip;
	}

	public boolean onInstance(){
		if(ValidationStatut.INSTANCE.equals(this.validationStatut))
			return true;
		else
			return false;
	}
	
	public boolean isReject(){
		if(ValidationStatut.REJETER.equals(this.validationStatut))
			return true;
		else
			return false;
	}
	
	public boolean isValidate(){
		if(ValidationStatut.VALIDER.equals(this.validationStatut))
			return true;
		else
			return false;
	}
	
	@Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(getId()).append(", identifiant=").append(username)
        	.append(", telephone=").append(", roles=").append(role).append("]");
        return builder.toString();
    }

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Entite getEntite() {
		return entite;
	}

	public void setEntite(Entite entite) {
		this.entite = entite;
	}

	public List<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}

	public Map<String, String> getIban() {
		return iban;
	}

	public void setIban(Map<String, String> iban) {
		this.iban = iban;
	}

	public Collection<Utilisateur> getCrees() {
		return crees;
	}

	public void setCrees(Collection<Utilisateur> crees) {
		this.crees = crees;
	}

	public Utilisateur getCreateur() {
		return createur;
	}

	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getPasswordLife() {
		return passwordLife;
	}

	public void setPasswordLife(int passwordLife) {
		this.passwordLife = passwordLife;
	}

	public int getTentative() {
		return tentative;
	}

	public void setTentative(int tentative) {
		this.tentative = tentative;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	public ValidationStatut getValidationStatut() {
		return validationStatut;
	}

	public void setValidationStatut(ValidationStatut validationStatut) {
		this.validationStatut = validationStatut;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	//Statut de la methode d'authentification par défaut
	//Activer ou désactiver par l'utilisateur
	public boolean isUsing2FA() {
		return isUsing2FA;
	}

	public void setUsing2FA(boolean isUsing2FA) {
		this.isUsing2FA = isUsing2FA;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
