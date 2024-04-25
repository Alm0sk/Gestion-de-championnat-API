package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Logger;

@Configuration
public class LoadData {
    private final Logger log = (Logger) LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) throws ParseException {
        log.info("Chargement des données");

        if (repository.count() == 0) {

            /*
             * **********************
             * CREATION D'UN OBJET
             * **********************
             */

            // Initialisation de l'objet date à partir d'un string
            LocalDate creationDate = LocalDate.parse("2024-04-24");

            // Objet ==> user
            User user = new User(
                    creationDate,
                    "SuperPass",
                    "test@email.fr",
                    "Dutest",
                    "Toto"
            );

            return args -> {
                // Sauvegarde de l'user dans la base de donnée
                log.info("Chargement de " + repository.save(user));
            };
        }
        else {
            return args -> {
                log.info("Données déjà chargés");
            };
        }
    }
}
