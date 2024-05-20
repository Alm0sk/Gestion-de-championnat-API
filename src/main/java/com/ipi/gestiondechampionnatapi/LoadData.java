package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
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
    CommandLineRunner initDatabaseUsers(UserRepository repository) throws ParseException {
        log.info("Chargement des données utilisateurs");

        if (repository.count() == 0) {

            // Objet Date
            LocalDate dateCreation = LocalDate.parse("2024-04-01");

            // Créer un encodeur de mots de passe BCrypt
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Créer le mot de passe hashé
            String hashedPassword = passwordEncoder.encode("P@sswordT0$trongF0rYu19!");

            // Création de l'objet User
            User userTest = new User(
                    dateCreation,
                    hashedPassword,
                    "test@email.fr",
                    "Dutest",
                    "Toto"
            );

            // Sauvegarde de l'utilisateur dans la base donnée
            return args -> log.info("Chargement de l'utilisateur : {}", repository.save(userTest));
        } else {
            return args -> log.info("Données utilisateurs déjà chargées");
        }
    }

    /**
     * Initialise la database avec un championat de test
     * @param repository le référentiel championship utilisé pour accéder à la base de données
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    CommandLineRunner initDatabaseChampionships(ChampionshipRepository repository) throws ParseException {
        log.info("Chargement des données championnats");

        if (repository.count() == 0) {

            // Objets Date
            LocalDate dateCreation = LocalDate.parse("2024-04-02");
            LocalDate dateFin = LocalDate.parse("2024-04-12");

            // Création de l'objet Championship
            Championship championshipTest = new Championship(
                    "championnat test",
                    dateCreation,
                    dateFin,
                    3,
                    0,
                    1
            );

            // Sauvegarde du championship dans la base donnée
            return args -> log.info("Chargement du championnat : {}", repository.save(championshipTest));
        } else {
            return args -> log.info("Données championnats déjà chargées");
        }
    }

    @Bean
    CommandLineRunner initDatabaseTeams(TeamRepository repository) throws ParseException {
        log.info("Chargement des données équipes");

        if (repository.count() == 0) {

            // Objets Date
            LocalDate dateCreationTeam1 = LocalDate.parse("2024-04-02");
            LocalDate dateCreationTeam2 = LocalDate.parse("2024-04-05");

            // Création des objets Team
            Team teamTest1 = new Team(
                    "TeamTest1",
                    dateCreationTeam1
            );

            Team teamTest2 = new Team(
                    "TeamTest2",
                    dateCreationTeam2
            );

            // Sauvegarde des teams dans la base donnée
            return args -> {
                log.info("Chargement de l'équipe : {}", repository.save(teamTest1));
                log.info("Chargement de l'équipe : {}", repository.save(teamTest2));
            };
        } else {
            return args -> log.info("Données des équipe déjà chargées");
        }
    }

}
