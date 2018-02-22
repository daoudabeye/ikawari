package org.mobibank.payme.backend.data;

import java.io.UnsupportedEncodingException;

import org.mobibank.payme.backend.data.entity.PasswordResetToken;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.data.entity.VerificationToken;

public interface IUserService {

    Utilisateur getUser(String verificationToken);

    void createVerificationTokenForUser(Utilisateur user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(Utilisateur user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    Utilisateur getUserByPasswordResetToken(String token);

    Utilisateur getUserByID(long id);

    void changeUserPassword(Utilisateur user, String password);

    boolean checkIfValidOldPassword(Utilisateur user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(Utilisateur user) throws UnsupportedEncodingException;

    Utilisateur updateUser2FA(boolean use2FA);

}
