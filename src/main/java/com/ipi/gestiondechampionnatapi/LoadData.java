package com.ipi.gestiondechampionnatapi;

import com.ipi.gestiondechampionnatapi.models.Championship;
import com.ipi.gestiondechampionnatapi.models.Day;
import com.ipi.gestiondechampionnatapi.models.Game;
import com.ipi.gestiondechampionnatapi.models.Stadium;
import com.ipi.gestiondechampionnatapi.models.Team;
import com.ipi.gestiondechampionnatapi.models.User;
import com.ipi.gestiondechampionnatapi.repository.ChampionshipRepository;
import com.ipi.gestiondechampionnatapi.repository.DayRepository;
import com.ipi.gestiondechampionnatapi.repository.GameRepository;
import com.ipi.gestiondechampionnatapi.repository.StadiumRepository;
import com.ipi.gestiondechampionnatapi.repository.TeamRepository;
import com.ipi.gestiondechampionnatapi.repository.UserRepository;
import com.ipi.gestiondechampionnatapi.service.TeamChampionshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.expression.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class LoadData {
    private final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private StadiumRepository stadiumRepository;

    /**
     * Initialise la database avec un utilisateur de test
     * @param repository le référentiel d'utilisateurs utilisé pour accéder à la base de données
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    @Order(1)
    CommandLineRunner initDatabaseUsers(UserRepository repository) throws ParseException {
        log.info("Chargement des données utilisateurs");

        if (repository.count() == 0) {

            // Objet Date
            LocalDate dateCreation = LocalDate.parse("2024-04-01");

            // Créer un encodeur de mots de passe BCrypt
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Créer le mot de passe hashé
            String hashedPassword = passwordEncoder.encode("admin");

            // Création de l'objet User
            User userTest = new User(
                    dateCreation,
                    hashedPassword,
                    "admin@email.fr",
                    "Lechef",
                    "Toto"
            );

            // Sauvegarde de l'utilisateur dans la base donnée
            return args -> log.info("Chargement de l'utilisateur : {}", repository.save(userTest));
        } else {
            return args -> log.info("Données utilisateurs déjà chargées");
        }
    }


    /**
     * Initialise la database avec des championats
     * @param repository le référentiel championship utilisé pour accéder à la base de données
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    @Order(2)
    CommandLineRunner initDatabaseChampionships(ChampionshipRepository repository) throws ParseException {
        log.info("Chargement des données championnats");

        if (repository.count() == 0) {

            // Objets Date
            LocalDate dateCreation = LocalDate.parse("2025-01-01");
            LocalDate dateFin = LocalDate.parse("2026-01-01");

            // Création de l'objet Championship
            Championship championshipLeague1 = new Championship(
                    "League 1",
                    "https://www.lfp.fr/assets/LFP_REVEAL_WEB_LFP_RS_1280x1600_b0591cf653.jpg",
                    dateCreation,
                    dateFin,
                    3,
                    0,
                    1,
                    "League 1"
            );

            Championship championshipLeague2 = new Championship(
                    "League 2",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/a/a4/Logo_Ligue_2_2024.png/250px-Logo_Ligue_2_2024.png",
                    dateCreation,
                    dateFin,
                    3,
                    0,
                    1,
                    "League 2"
            );

            // Sauvegarde du championship dans la base donnée
            return args -> {
        log.info("Chargement du championnat : {}", repository.save(championshipLeague1));
        log.info("Chargement du championnat : {}", repository.save(championshipLeague2));
    };
} else {
        return args -> log.info("Données des équipe déjà chargées");
        }
                }


    /**
     * Initialise la database avec 10 équipes par championnat de test
     * @param teamRepository le référentiel Team utilisé pour accéder à la base de données
     * @param championshipRepository le référentiel Championship pour associer les équipes
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    @Order(3)
    CommandLineRunner initDatabaseTeams(TeamRepository teamRepository, ChampionshipRepository championshipRepository, TeamChampionshipService teamChampionshipService) throws ParseException {
        log.info("Chargement des données équipes");

        if (teamRepository.count() == 0) {

            // Dates de fondation réelles des équipes League 1
            LocalDate[] league1CreationDates = {
                    LocalDate.parse("1970-08-12"), // Paris Saint-Germain
                    LocalDate.parse("1899-08-31"), // Olympique de Marseille  
                    LocalDate.parse("1950-08-03"), // Olympique Lyonnais
                    LocalDate.parse("1924-08-01"), // AS Monaco
                    LocalDate.parse("1944-09-23"), // Lille OSC
                    LocalDate.parse("1904-08-01"), // OGC Nice
                    LocalDate.parse("1901-03-10"), // Stade Rennais FC
                    LocalDate.parse("1906-06-15"), // RC Strasbourg
                    LocalDate.parse("1974-06-16"), // Montpellier HSC
                    LocalDate.parse("1943-04-21")  // FC Nantes
            };

            // Dates de fondation réelles des équipes League 2  
            LocalDate[] league2CreationDates = {
                    LocalDate.parse("1881-10-01"), // Bordeaux
                    LocalDate.parse("1913-03-10"), // Caen
                    LocalDate.parse("1997-06-20"), // Grenoble
                    LocalDate.parse("1929-07-11"), // Rodez
                    LocalDate.parse("1920-08-01"), // Pau FC
                    LocalDate.parse("1913-08-02"), // Valenciennes
                    LocalDate.parse("1912-01-01"), // En Avant Guingamp
                    LocalDate.parse("1905-07-27"), // AJ Auxerre
                    LocalDate.parse("1905-08-07"), // SC Bastia
                    LocalDate.parse("1901-03-15")  // Amiens SC
            };

            // Noms des équipes pour League 1
            String[] league1Teams = {
                    "Paris Saint-Germain", "Olympique de Marseille", "Olympique Lyonnais", 
                    "AS Monaco", "Lille OSC", "OGC Nice", "Stade Rennais FC", 
                    "RC Strasbourg", "Montpellier HSC", "FC Nantes"
            };

            // Noms des équipes pour League 2
            String[] league2Teams = {
                    "Bordeaux", "Caen", "Grenoble", "Rodez", "Pau FC", 
                    "Valenciennes", "Guingamp", "Auxerre", "Bastia", "Amiens"
            };

            // Logos des équipes
            String[] league1Logos = {
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/f/ff/Logo_Paris_Saint-Germain_2024.svg/1200px-Logo_Paris_Saint-Germain_2024.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/4/43/Logo_Olympique_de_Marseille.svg",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/a/a5/Logo_Olympique_Lyonnais_-_2022.svg/1762px-Logo_Olympique_Lyonnais_-_2022.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/5/58/Logo_AS_Monaco_FC_-_2021.svg/1182px-Logo_AS_Monaco_FC_-_2021.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/6/62/Logo_LOSC_Lille_2018.svg/1200px-Logo_LOSC_Lille_2018.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/b/b1/Logo_OGC_Nice_2013.svg/1200px-Logo_OGC_Nice_2013.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/e/e9/Logo_Stade_Rennais_FC.svg/1200px-Logo_Stade_Rennais_FC.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/7/70/Racing_Club_de_Strasbourg_Alsace_%28RC_Strasbourg_-_RCS_-_RCSA%29_logo_officiel.svg",
                    "https://upload.wikimedia.org/wikipedia/commons/3/3c/HSC_Montpellier_Logo.png",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Logo_FC_Nantes_%28avec_fond%29_-_2019.svg/1200px-Logo_FC_Nantes_%28avec_fond%29_-_2019.svg.png"
            };

            String[] league2Logos = {
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/7/76/Logo_des_Girondins_de_Bordeaux.svg/1200px-Logo_des_Girondins_de_Bordeaux.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/archive/7/79/20200813190357%21Logo_SM_Caen_2016.svg",
                    "https://upload.wikimedia.org/wikipedia/fr/c/c3/Logo_Grenoble_Foot_38_-_2018.svg",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/6/6a/Logo_Rodez_AF_2017.svg/langfr-250px-Logo_Rodez_AF_2017.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/f/fa/Logo_Pau_FC_-_2022.svg/2048px-Logo_Pau_FC_-_2022.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/a/ac/Logo_Valenciennes_FC.svg/1200px-Logo_Valenciennes_FC.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/9/95/En_avant_Guingamp_%28logo_2019%29.svg/langfr-250px-En_avant_Guingamp_%28logo_2019%29.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/f/f6/Logo_AJ_Auxerre_2024.svg/langfr-250px-Logo_AJ_Auxerre_2024.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/e/e0/Logo_Sporting_Club_Bastia_2024.svg/1200px-Logo_Sporting_Club_Bastia_2024.svg.png",
                    "https://upload.wikimedia.org/wikipedia/fr/thumb/b/bf/Logo_Amiens_SC_-_2022.svg/langfr-250px-Logo_Amiens_SC_-_2022.svg.png"
            };

            // Entraîneurs pour League 1
            String[] league1Coaches = {
                    "Luis Enrique", "Roberto De Zerbi", "Pierre Sage", 
                    "Adi Hütter", "Bruno Genesio", "Franck Haise", 
                    "Julien Stéphan", "Liam Rosenior", "Jean-Louis Gasset", "Antoine Kombouaré"
            };

            // Entraîneurs pour League 2
            String[] league2Coaches = {
                    "Albert Riera", "Nicolas Seube", "Oswald Tanchot", 
                    "Didier Santini", "Fabien Lefèvre", "Olivier Guegan", 
                    "Sylvain Didot", "Christophe Pélissier", "Benoît Tavenot", "Omar Daf"
            };

            // Présidents réels pour League 1
            String[] league1Presidents = {
                    "Nasser Al-Khelaïfi", // Paris Saint-Germain
                    "Pablo Longoria", // Olympique de Marseille
                    "John Textor", // Olympique Lyonnais
                    "Dmitri Rybolovlev", // AS Monaco
                    "Olivier Létang", // Lille OSC
                    "Jean-Pierre Rivère", // OGC Nice
                    "Olivier Cloarec", // Stade Rennais FC
                    "Marc Keller", // RC Strasbourg
                    "Laurent Nicollin", // Montpellier HSC
                    "Waldemar Kita" // FC Nantes
            };

            // Présidents réels pour League 2
            String[] league2Presidents = {
                    "Gérard Lopez", // Bordeaux
                    "Pierre-Antoine Capton", // Caen
                    "Alain Martel", // Grenoble
                    "Pierre-Olivier Murat", // Rodez
                    "Bernard Pontneau", // Pau FC
                    "Eddy Zdziech", // Valenciennes
                    "Bertrand Desplat", // Guingamp
                    "James Zhou", // Auxerre
                    "Claude Ferrandi", // Bastia
                    "Bernard Joannin" // Amiens
            };

            // Statuts juridiques réels pour League 1
            String[] league1Status = {
                    "SASP", // Paris Saint-Germain
                    "SASP", // Olympique de Marseille
                    "SASP", // Olympique Lyonnais
                    "SASP", // AS Monaco
                    "SASP", // Lille OSC
                    "SASP", // OGC Nice
                    "SASP", // Stade Rennais FC
                    "SASP", // RC Strasbourg
                    "SASP", // Montpellier HSC
                    "SASP"  // FC Nantes
            };

            // Statuts juridiques pour League 2
            String[] league2Status = {
                    "SASP", // Bordeaux
                    "SASP", // Caen
                    "Association", // Grenoble
                    "Association", // Rodez
                    "Association", // Pau FC
                    "SASP", // Valenciennes
                    "Association", // Guingamp
                    "SASP", // Auxerre
                    "Association", // Bastia
                    "Association"  // Amiens
            };

            // Stades pour League 1
            String[] league1Stadiums = {
                    "Parc des Princes", "Groupama Stadium", "Orange Vélodrome", 
                    "Allianz Riviera", "Stade Pierre-Mauroy", "Stade Auguste-Delaune II",
                    "Stade de la Beaujoire", "Stade Saint-Symphorien", "Stade de la Mosson", "Stade Gabriel Montpied"
            };
            
            String[] league1StadiumAddresses = {
                    "24 Rue du Commandant Guilbaud, 75016 Paris", 
                    "10 Avenue Simone Veil, 69150 Décines-Charpieu",
                    "3 Boulevard Michelet, 13008 Marseille",
                    "405 Route de Grenoble, 06200 Nice",
                    "261 Boulevard de Tournai, 59650 Villeneuve-d'Ascq",
                    "12 Rue de l'École d'Artillerie, 51100 Reims",
                    "5 Boulevard de la Beaujoire, 44300 Nantes",
                    "12 Rue Saint-Symphorien, 57050 Metz",
                    "Av. de Heidelberg, 34000 Montpellier",
                    "Rue du Clos Perret, 63100 Clermont-Ferrand"
            };
            
            int[] league1StadiumCapacities = {47929, 59186, 67394, 35624, 50186, 21029, 35322, 26671, 32900, 11980};
            
            String[] league1StadiumPhones = {
                    "01.47.43.71.71", "08.92.69.69.69", "04.91.76.56.00",
                    "04.92.00.64.00", "03.20.76.28.00", "03.26.91.31.10",
                    "02.51.80.50.00", "03.87.74.42.00", "04.67.13.05.00", "04.73.99.84.84"
            };

            // Stades pour League 2
            String[] league2Stadiums = {
                    "Stade Océane", "Stade Marcel-Picot", "Stade de l'Abbé-Deschamps",
                    "Stade Francis-Le Blé", "Stade Raymond Kopa", "Stade des Costières",
                    "Stade Gaston Gérard", "Stade du Roudourou", "Stade de la Licorne", "Stade Armand-Cesari"
            };
            
            String[] league2StadiumAddresses = {
                    "Boulevard de Leningrad, 76600 Le Havre",
                    "4 Allée du Stade Marcel Picot, 54500 Vandœuvre-lès-Nancy",
                    "10 Route de Perrigny, 89000 Auxerre",
                    "26 Rue de Quimper, 29200 Brest",
                    "73 Boulevard Pierre de Coubertin, 49000 Angers",
                    "16 Rue Général Perrier, 30000 Nîmes",
                    "3 Boulevard Chanoine Kir, 21000 Dijon",
                    "2 Rue du Stade, 22200 Guingamp",
                    "1 Rue du Docteur Lamaze, 80000 Amiens",
                    "Route de Furiani, 20600 Bastia"
            };
            
            int[] league2StadiumCapacities = {25178, 20087, 23467, 15097, 18752, 18364, 15995, 18378, 12097, 16480};
            
            String[] league2StadiumPhones = {
                    "02.35.22.25.00", "03.83.18.21.25", "03.86.78.32.20",
                    "02.98.45.30.30", "02.41.96.90.00", "04.66.87.22.22",
                    "03.80.42.12.25", "02.96.40.51.45", "03.22.71.54.00", "04.95.55.43.13"
            };

            return args -> {
                // Créer les équipes pour League 1 (ID 1)
                for (int i = 0; i < 10; i++) {
                    // Créer le stade pour l'équipe
                    Stadium stadium = new Stadium(
                            league1Stadiums[i],
                            league1StadiumAddresses[i],
                            league1StadiumCapacities[i],
                            league1StadiumPhones[i]
                    );
                    Stadium savedStadium = stadiumRepository.save(stadium);
                    
                    Team team = new Team(
                            league1Teams[i],
                            league1CreationDates[i],
                            league1Logos[i],
                            league1Coaches[i],
                            league1Presidents[i],
                            league1Status[i],
                            "Ville " + league1Teams[i],
                            "01 00 00 00 0" + i,
                            "https://www." + league1Teams[i].toLowerCase().replace(" ", "") + ".fr"
                    );
                    team.setStadium(savedStadium);
                    
                    // Sauvegarder l'équipe d'ajouter l'association
                    Team savedTeam = teamRepository.save(team);
                    
                    // Associer l'équipe au championnat League 1  la sauvegarde
                    Optional<Championship> league1 = championshipRepository.findByName("League 1");
                    log.info("Recherche championnat League 1 pour équipe {} - trouvé: {}", 
                            league1Teams[i], league1.isPresent());
                    if (league1.isPresent()) {
                        Championship championship = league1.get();
                        
                        boolean associated = teamChampionshipService.associateTeamToChampionship(savedTeam, championship);
                        if (associated) {
                            log.info("Association ajoutée: équipe {} -> championnat {}", 
                                    league1Teams[i], championship.getName());
                        }
                    }
                    
                    log.info("Chargement de l'équipe League 1 : {} - Championnats associés: {}", 
                            savedTeam.getName(), savedTeam.getChampionships().size());
                }

                // Créer les équipes pour League 2 (ID 2)
                for (int i = 0; i < 10; i++) {
                    // Créer le stade pour l'équipe
                    Stadium stadium = new Stadium(
                            league2Stadiums[i],
                            league2StadiumAddresses[i],
                            league2StadiumCapacities[i],
                            league2StadiumPhones[i]
                    );
                    Stadium savedStadium = stadiumRepository.save(stadium);
                    
                    Team team = new Team(
                            league2Teams[i],
                            league2CreationDates[i],
                            league2Logos[i],
                            league2Coaches[i],
                            league2Presidents[i],
                            league2Status[i],
                            "Ville " + league2Teams[i],
                            "02 00 00 00 0" + i,
                            "https://www." + league2Teams[i].toLowerCase().replace(" ", "") + ".fr"
                    );
                    team.setStadium(savedStadium);
                    
                    // Sauvegarder l'équipe d'ajouter l'association
                    Team savedTeam = teamRepository.save(team);
                    
                    // Associer l'équipe au championnat League 2
                    Optional<Championship> league2 = championshipRepository.findByName("League 2");
                    log.info("Recherche championnat League 2 pour équipe {} - trouvé: {}", 
                            league2Teams[i], league2.isPresent());
                    if (league2.isPresent()) {
                        Championship championship = league2.get();
                       
                        boolean associated = teamChampionshipService.associateTeamToChampionship(savedTeam, championship);
                        if (associated) {
                            log.info("Association ajoutée: équipe {} -> championnat {}", 
                                    league2Teams[i], championship.getName());
                        }
                    }
                    
                    log.info("Chargement de l'équipe League 2 : {} - Championnats associés: {}", 
                            savedTeam.getName(), savedTeam.getChampionships().size());
                }
            };
        } else {
            return args -> log.info("Données des équipes déjà chargées");
        }
    }


    /**
     * Initialise la base de données avec des jours de test
     *
     * @param dayRepository le référentiel Day utilisé pour accéder à la base de données
     * @param championshipRepository le référentiel Championship pour associer les journées
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    @Order(4)
    CommandLineRunner initDatabaseDays(DayRepository dayRepository, ChampionshipRepository championshipRepository) throws ParseException {
        log.info("Chargement des données jours");

        if (dayRepository.count() == 0) {

            return args -> {
                // Créer 10 journées pour League 1 (ID 1)
                for (int i = 1; i <= 10; i++) {
                    Day day = new Day("Journée " + i + " - League 1", 1L);
                    log.info("Chargement de la journée League 1 : {}", dayRepository.save(day));
                }

                // Créer 10 journées pour League 2 (ID 2)
                for (int i = 1; i <= 10; i++) {
                    Day day = new Day("Journée " + i + " - League 2", 2L);
                    log.info("Chargement de la journée League 2 : {}", dayRepository.save(day));
                }
            };
        } else {
            return args -> log.info("Données jours déjà chargées");
        }
    }

    /**
     * Initialise la base de données avec 20 matchs par championnat
     *
     * @param gameRepository le référentiel Game utilisé pour accéder à la base de données
     * @param teamRepository le référentiel Team pour récupérer les équipes
     * @param dayRepository le référentiel Day pour récupérer les journées
     * @return instance de {@link CommandLineRunner} qui exécute l'initialisation
     * @throws ParseException gestion d'erreur
     */
    @Bean
    @Order(5)
    CommandLineRunner initDatabaseGames(GameRepository gameRepository, TeamRepository teamRepository, DayRepository dayRepository) throws ParseException {
        log.info("Chargement des données matchs");

        // Forcer la recréation des matchs
        return args -> {
            // Vider d'abord tous les matchs existants
            gameRepository.deleteAll();
            
            // Récupérer toutes les équipes et journées (elles sont maintenant sauvegardées)
            var allTeams = teamRepository.findAll();
            var allDays = dayRepository.findAll();

            log.info("Nombre d'équipes trouvées: {}", allTeams.size());
            log.info("Nombre de journées trouvées: {}", allDays.size());

            // Séparer les équipes par championnat (les 10 premières pour League 1, les 10 suivantes pour League 2)
            var league1Teams = allTeams.subList(0, Math.min(10, allTeams.size()));
            var league2Teams = allTeams.subList(Math.min(10, allTeams.size()), Math.min(20, allTeams.size()));

            // Séparer les journées par championnat
            var league1Days = allDays.stream().filter(day -> day.getChampionshipId() == 1L).toList();
            var league2Days = allDays.stream().filter(day -> day.getChampionshipId() == 2L).toList();

            log.info("Équipes League 1: {}, Journées League 1: {}", league1Teams.size(), league1Days.size());
            log.info("Équipes League 2: {}, Journées League 2: {}", league2Teams.size(), league2Days.size());

            // Créer 20 matchs pour League 1
            createGamesForLeague(gameRepository, league1Teams, league1Days, "League 1");

            // Créer 20 matchs pour League 2
            createGamesForLeague(gameRepository, league2Teams, league2Days, "League 2");
        };
    }

    /**
     * Méthode utilitaire pour créer des matchs fixes pour une ligue donnée
     */
    private void createGamesForLeague(GameRepository gameRepository, java.util.List<Team> teams, java.util.List<Day> days, String leagueName) {
        if (teams.size() < 2 || days.isEmpty()) {
            log.warn("Pas assez d'équipes ou de journées pour créer des matchs pour {}", leagueName);
            return;
        }

        if (leagueName.equals("League 1")) {
            createLeague1Games(gameRepository, teams, days);
        } else if (leagueName.equals("League 2")) {
            createLeague2Games(gameRepository, teams, days);
        }
    }

    /**
     * Crée les matchs fixes pour League 1
     */
    private void createLeague1Games(GameRepository gameRepository, java.util.List<Team> teams, java.util.List<Day> days) {
        // Matchs prédéfinis pour League 1 avec des scores réalistes
        // Chaque journée a exactement 5 matchs, chaque équipe joue une fois par journée
        MatchData[] league1Matches = {
            // Journée 1 (index 0) - 5 matchs, 10 équipes différentes
            new MatchData(0, 1, 2, 1, 0), // PSG vs OM
            new MatchData(2, 3, 4, 3, 0), // OL vs Monaco
            new MatchData(4, 5, 0, 2, 0), // Lille vs Nice
            new MatchData(6, 7, 1, 1, 0), // Rennes vs Strasbourg
            new MatchData(8, 9, 2, 0, 0), // Montpellier vs Nantes
            
            // Journée 2 (index 1) - 5 matchs, 10 équipes différentes
            new MatchData(0, 2, 2, 3, 1), // PSG vs OL
            new MatchData(1, 3, 1, 1, 1), // OM vs Monaco
            new MatchData(4, 6, 2, 1, 1), // Lille vs Rennes
            new MatchData(5, 7, 0, 0, 1), // Nice vs Strasbourg
            new MatchData(8, 9, 1, 3, 1), // Montpellier vs Nantes
            
            // Journée 3 (index 2) - 5 matchs, 10 équipes différentes
            new MatchData(0, 4, 1, 2, 2), // PSG vs Lille
            new MatchData(1, 2, 2, 7, 2), // OM vs OL
            new MatchData(3, 5, 1, 0, 2), // Monaco vs Nice
            new MatchData(6, 8, 3, 1, 2), // Rennes vs Montpellier
            new MatchData(7, 9, 0, 1, 2), // Strasbourg vs Nantes
            
            // Journée 4 (index 3) - 5 matchs, 10 équipes différentes
            new MatchData(0, 5, 2, 0, 3), // PSG vs Nice
            new MatchData(1, 4, 1, 1, 3), // OM vs Lille
            new MatchData(2, 7, 2, 0, 3), // OL vs Strasbourg
            new MatchData(3, 6, 2, 1, 3), // Monaco vs Rennes
            new MatchData(8, 9, 1, 0, 3), // Montpellier vs Nantes
        };

        createMatchesFromData(gameRepository, teams, days, league1Matches, "League 1");
    }

    /**
     * Crée les matchs fixes pour League 2
     */
    private void createLeague2Games(GameRepository gameRepository, java.util.List<Team> teams, java.util.List<Day> days) {
        // Matchs prédéfinis pour League 2 avec des scores réalistes
        // Chaque journée a exactement 5 matchs, chaque équipe joue une fois par journée
        MatchData[] league2Matches = {
            // Journée 1 (index 0) - 5 matchs, 10 équipes différentes
            new MatchData(0, 1, 1, 0, 0), // Bordeaux vs Caen
            new MatchData(2, 3, 2, 1, 0), // Grenoble vs Rodez
            new MatchData(4, 5, 1, 1, 0), // Pau vs Valenciennes
            new MatchData(6, 7, 3, 0, 0), // Guingamp vs Auxerre
            new MatchData(8, 9, 0, 2, 0), // Bastia vs Amiens
            
            // Journée 2 (index 1) - 5 matchs, 10 équipes différentes
            new MatchData(0, 2, 2, 0, 1), // Bordeaux vs Grenoble
            new MatchData(1, 3, 1, 2, 1), // Caen vs Rodez
            new MatchData(4, 6, 0, 1, 1), // Pau vs Guingamp
            new MatchData(5, 7, 1, 1, 1), // Valenciennes vs Auxerre
            new MatchData(8, 9, 3, 1, 1), // Bastia vs Amiens
            
            // Journée 3 (index 2) - 5 matchs, 10 équipes différentes
            new MatchData(0, 4, 1, 1, 2), // Bordeaux vs Pau
            new MatchData(1, 2, 2, 1, 2), // Caen vs Grenoble
            new MatchData(3, 5, 1, 0, 2), // Rodez vs Valenciennes
            new MatchData(6, 8, 2, 2, 2), // Guingamp vs Bastia
            new MatchData(7, 9, 1, 0, 2), // Auxerre vs Amiens
            
            // Journée 4 (index 3) - 5 matchs, 10 équipes différentes
            new MatchData(0, 5, 0, 1, 3), // Bordeaux vs Valenciennes
            new MatchData(1, 4, 1, 2, 3), // Caen vs Pau
            new MatchData(2, 6, 2, 0, 3), // Grenoble vs Guingamp
            new MatchData(3, 7, 1, 1, 3), // Rodez vs Auxerre
            new MatchData(8, 9, 0, 3, 3), // Bastia vs Amiens
        };

        createMatchesFromData(gameRepository, teams, days, league2Matches, "League 2");
    }

    /**
     * Crée les matchs à partir des données prédéfinies
     */
    private void createMatchesFromData(GameRepository gameRepository, java.util.List<Team> teams, 
                                     java.util.List<Day> days, MatchData[] matchesData, String leagueName) {
        for (MatchData matchData : matchesData) {
            Team team1 = teams.get(matchData.team1Index);
            Team team2 = teams.get(matchData.team2Index);
            Day day = days.get(matchData.dayIndex);
            
            Game game = new Game(matchData.score1, matchData.score2, team1, team2, day);
            log.info("Chargement du match {} : {} {} - {} {}", leagueName, 
                    team1.getName(), matchData.score1, matchData.score2, team2.getName());
            gameRepository.save(game);
        }
    }

    /**
     * Classe interne pour représenter les données d'un match
     */
    private static class MatchData {
        final int team1Index;
        final int team2Index;
        final int score1;
        final int score2;
        final int dayIndex;

        MatchData(int team1Index, int team2Index, int score1, int score2, int dayIndex) {
            this.team1Index = team1Index;
            this.team2Index = team2Index;
            this.score1 = score1;
            this.score2 = score2;
            this.dayIndex = dayIndex;
        }
    }

    /**
     * Bean pour vérifier que toutes les équipes sont bien associées à leurs championnats
     * Note: Cette vérification utilise des requêtes spécialisées pour éviter LazyInitializationException
     * et permet aux équipes d'avoir plusieurs championnats à l'avenir
     */
    @Bean
    @Order(6)
    @Transactional(readOnly = true)
    CommandLineRunner verifyTeamChampionshipAssociations(TeamRepository teamRepository, ChampionshipRepository championshipRepository) {
        return args -> {
            log.info("=== VÉRIFICATION DES ASSOCIATIONS ÉQUIPES-CHAMPIONNATS ===");
            
            // Compter le total d'équipes
            long totalTeams = teamRepository.count();
            log.info("Nombre total d'équipes dans la base: {}", totalTeams);
            
            // Vérifier les associations pour chaque championnat
            var league1 = championshipRepository.findByName("League 1");
            var league2 = championshipRepository.findByName("League 2");
            
            int totalAssociatedTeams = 0;
            
            if (league1.isPresent()) {
                var league1Teams = teamRepository.findTeamsByChampionshipId(league1.get().getId());
                log.info("✓ League 1 contient {} équipes:", league1Teams.size());
                for (Team team : league1Teams) {
                    log.info("  - {}", team.getName());
                }
                totalAssociatedTeams += league1Teams.size();
            }
            
            if (league2.isPresent()) {
                var league2Teams = teamRepository.findTeamsByChampionshipId(league2.get().getId());
                log.info("✓ League 2 contient {} équipes:", league2Teams.size());
                for (Team team : league2Teams) {
                    log.info("  - {}", team.getName());
                }
                totalAssociatedTeams += league2Teams.size();
            }
            
            // Résumé final
            log.info("=== RÉSUMÉ ===");
            log.info("Total équipes associées: {}/{}", totalAssociatedTeams, totalTeams);
            
            if (totalAssociatedTeams == totalTeams) {
                log.info("✅ Toutes les équipes sont correctement associées!");
            } else {
                log.warn("⚠️  {} équipes ne sont pas associées à un championnat", 
                        totalTeams - totalAssociatedTeams);
            }
            
            log.info("=== FIN DE LA VÉRIFICATION ===");
        };
    }

}
