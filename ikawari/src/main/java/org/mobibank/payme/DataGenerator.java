package org.mobibank.payme;

import java.util.ArrayList;
import java.util.List;

import org.mobibank.payme.backend.UtilisateurRepository;
import org.mobibank.payme.backend.data.Role;
import org.mobibank.payme.backend.data.entity.Utilisateur;
import org.mobibank.payme.backend.numerate.ValidationStatut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class DataGenerator implements HasLogger {

	private final PasswordEncoder passwordEncoder;

	private final List<Utilisateur> users = new ArrayList<>();
	private Utilisateur admin;

	@Autowired
	public DataGenerator(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public CommandLineRunner loadData(UtilisateurRepository users, PasswordEncoder passwordEncoder) {
		return args -> {
			if (users.count() != 0L) {
				getLogger().info("Des comptes utilisateurs existe déja");
				return;
			}

			getLogger().info("Création des compte utilisateur par défaut");
			getLogger().info("... generating users");
			createUsers(users);
			getLogger().info("... users generated");
		};
	}

	
	private void createUsers(UtilisateurRepository userRepository) {
		Utilisateur user = new Utilisateur("admin", passwordEncoder.encode("admin"), Role.ADMIN);
		user.setLocked(true);
		user.setValidationStatut(ValidationStatut.VALIDER);
		admin = userRepository.save(user);
		users.add(admin);
	}
}
