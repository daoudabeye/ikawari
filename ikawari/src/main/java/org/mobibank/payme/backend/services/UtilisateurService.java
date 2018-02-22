package org.mobibank.payme.backend.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.mobibank.payme.BeanLocator;
import org.mobibank.payme.backend.PasswordResetTokenRepository;
import org.mobibank.payme.backend.UtilisateurRepository;
import org.mobibank.payme.backend.VerificationTokenRepository;
import org.mobibank.payme.backend.data.IUserService;
import org.mobibank.payme.backend.data.entity.PasswordResetToken;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.data.entity.VerificationToken;
import org.mobibank.payme.backend.numerate.Devise;
import org.mobibank.payme.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


@Service
public class UtilisateurService implements CrudService<Utilisateur>  , IUserService {

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	public static String APP_NAME = "SpringRegistration";


	private static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "L'utilisateur est verouillé et ne peut etre modifier ni supprimé";
	private final PasswordEncoder passwordEncoder;
	private final CompteService compteService;

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	public UtilisateurService(PasswordEncoder passwordEncoder, CompteService compteService) {
		this.passwordEncoder = passwordEncoder;
		this.compteService = compteService;
	}

	public Utilisateur getCurrentUser() {
		Utilisateur current = getRepository().findByUsername(SecurityUtils.getUsername());

		Assert.notNull(current, "Utilisateur courant non identifié");
		return current;
	}

	@Override
	public Page<Utilisateur> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCaseOrRoleLikeIgnoreCase(repositoryFilter,
					repositoryFilter, repositoryFilter, repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByEmailLikeIgnoreCaseOrUsernameLikeIgnoreCaseOrTelephoneLikeIgnoreCase(repositoryFilter, 
					repositoryFilter, repositoryFilter);
		} else {
			return getRepository().count();
		}
	}

	@Override
	public UtilisateurRepository getRepository() {
		return BeanLocator.find(UtilisateurRepository.class);
	}

	@Override
	public Page<Utilisateur> find(Pageable pageable) {
		return getRepository().findBy(pageable);
	}

	public String encodePassword(String value) {
		return passwordEncoder.encode(value);
	}

	@Override
	@Transactional
	public Utilisateur save(Utilisateur entity) {
		throwIfUserLocked(entity.getId());
		return getRepository().save(entity);
	}

	@Override
	@Transactional
	public void delete(long userId) {
		throwIfUserLocked(userId);
		getRepository().delete(userId);
	}

	private void throwIfUserLocked(Long userId) {
		if (userId == null) {
			return;
		}

		Utilisateur dbUser = getRepository().findOne(userId);
		if (dbUser.isLocked()) {
			throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
		}
	}

	public void loginFailed(String username){
		Utilisateur user = getRepository().findByUsername(username);
		user.loginAttempt();
		getRepository().save(user);
	}

	public void loginSucceeded(String username, String ip) {
		Utilisateur user = getRepository().findByUsername(username);
		user.loginSuccess(ip);

		getRepository().save(user);
	}

	public boolean isPasswordMatch(String password, String encodedPassword) {
		return passwordEncoder.matches(password, encodedPassword);
	}

	@Transactional
	public void updatePassword(Utilisateur user, String value) {
		user.setPassword(passwordEncoder.encode(value));
		getRepository().save(user);
	}

	public boolean exist(String value) {
		if(getRepository().findByUsername(value) != null)
			return true;
		else if (getRepository().findByTelephone(value) == null)
			return true;

		return  false ;
	}

	/**
	 * Crée les comptes wallet
	 * @param utilisateur
	 * @param devise
	 */
	public void createWallet(Utilisateur utilisateur, Devise devise) {
		compteService.create(utilisateur, devise);
	}

	public Utilisateur find(String key) {
		if(key == null)
			return null;
		if(key.isEmpty())
			return null;
		return getRepository().findByUsernameOrTelephone(key, key);
	}
	
	public Utilisateur findByNumber(String number) {
		if(number == null)
			return null;
		if(number.isEmpty())
			return null;
		return getRepository().findByTelephone(number);
	}
	
	public Utilisateur findByUsername(String username) {
		if(username == null)
			return null;
		if(username.isEmpty())
			return null;
		return getRepository().findByUsername(username);
	}

	@Override
	public Utilisateur getUser(final String verificationToken) {
		final VerificationToken token = tokenRepository.findByToken(verificationToken);
		if (token != null) {
			return token.getUser();
		}
		return null;
	}

	@Override
	public VerificationToken getVerificationToken(final String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	@Override
	public void createVerificationTokenForUser(final Utilisateur user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Override
	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = tokenRepository.save(vToken);
		return vToken;
	}

	@Override
	public void createPasswordResetTokenForUser(final Utilisateur user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token);
	}

	@Override
	public Utilisateur getUserByPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token).getUser();
	}

	@Override
	public Utilisateur getUserByID(final long id) {
		return getRepository().findOne(id);
	}

	@Override
	public void changeUserPassword(final Utilisateur user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		getRepository().save(user);
	}

	@Override
	public boolean checkIfValidOldPassword(final Utilisateur user, final String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final Utilisateur user = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			tokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}

		user.setEnabled(true);
		// tokenRepository.delete(verificationToken);
		getRepository().save(user);
		return TOKEN_VALID;
	}

	@Override
	public String generateQRUrl(Utilisateur user) throws UnsupportedEncodingException {
		return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
	}

	@Override
	public Utilisateur updateUser2FA(boolean use2FA) {
		final Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur currentUser = (Utilisateur) curAuth.getPrincipal();
		currentUser.setUsing2FA(use2FA);
		currentUser = getRepository().save(currentUser);
		final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return currentUser;
	}

}
