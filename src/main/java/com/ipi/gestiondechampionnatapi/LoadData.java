package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Configuration
public class LoadData {
    private final Logger log = LoggerFactory.getLogger(LoadData.class);

    /**
     * Initialise la database avec un utilisateur de test
     * @param repository le référentiel d'utilisateurs utilisé pour accéder à la base de données
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) throws ParseException {
        log.info("Chargement des données utilisateurs");

        if (repository.count() == 0) {

            // Objet Date
            LocalDate dateCreation = LocalDate.parse("2024-04-02");

            // Créer un encodeur de mots de passe BCrypt
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Hacher le mot de passe
            String hashedPassword = passwordEncoder.encode("P@sswordT0$trongF0rYu19!");

            // Création de l'objet User
            User userTest = new User(dateCreation, hashedPassword, "test@email.fr", "Dutest", "Toto");

            // Sauvegarde de l'utilisateur dans la base donnée
            return args -> log.info("Chargement de l'utilisateur : {}", repository.save(userTest));
        } else {
            return args -> log.info("Données déjà chargées");
        }
    }
}
