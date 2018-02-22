package org.mobibank.payme.security.google2fa;

import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

//@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
        final Utilisateur user = utilisateurService.find(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        // to verify verification code
        if (user.isUsing2FA()) {
            //final Totp totp = new Totp(user.getSecret());
            if (!isValidLong(verificationCode) /*|| !totp.verify(verificationCode)*/) {
                throw new BadCredentialsException("Invalid verfication code");
            }

        }
        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
