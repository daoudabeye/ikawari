package org.mobibank.payme.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.mobibank.payme.backend.UtilisateurRepository;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.ui.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UtilisateurRepository utilisateurRepository;
	private final LoginAttemptService loginAttemptService;
	
	private Utilisateur current;
	private String actualIp;
	
	//One Time Password generator
	private OtpGenerator otp;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	public UserDetailsServiceImpl(UtilisateurRepository userRepository, LoginAttemptService loginAttemptService) {
		this.utilisateurRepository = userRepository;
		this.loginAttemptService = loginAttemptService;
		otp = new OtpGenerator();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("ip blocked");
		}

		Utilisateur user = utilisateurRepository.findByUsername(username);
		if (null == user) 
			throw new UsernameNotFoundException("Aucun utilisateur trouvé avec ce identifiant : " + username);
		else if(user.isBlacklisted())
			throw new RuntimeException("Compte utilisateur blacklister : " + username);
		else if(!user.isValidate())
			throw new RuntimeException("Statut de votre compte : " + user.getValidationStatut());
		else {
			this.current = user;
			this.actualIp = ip;
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
		}
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null){
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

	public Utilisateur getCurrent() {
		return current;
	}
	
	public String getActualIp() {
		return actualIp;
	}

	public OtpGenerator getOtp() {
		return otp;
	}
}