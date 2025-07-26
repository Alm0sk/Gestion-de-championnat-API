# Gestion-de-championnat-API

Gestion des requêtes API d'une application de gestion de championnat avec Spring.
Réalisé pour les tables ci-dessous.

*Application reprise d'une ancienne version de l'application de gestion de championnat, réalisée durant la formation CDEV de l'IPI en 2023-2024*

## 🛠️ Technologies utilisées

- **Backend :** Spring Boot, Spring Security, Spring Data JPA
- **Frontend :** Thymeleaf, Bootstrap 5, HTML/CSS
- **Base de données :** MySQL
- **Build :** Maven
- **Containerisation :** Docker & Docker Compose

## 📋 Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- Docker et Docker Compose
- Ports 3306, 8080 et 8081 disponibles

## 📊 Modèle de base de données

![image](assets/p2/db_model.png)

## 🚀 Installation et lancement

### 1. Base de données

*Les commandes suivantes sont à lancer dans le terminal à la racine du projet.
Elles ont étés utilisées dans une contexte de developpement sur un PC Arch Linux.*

J'ai fais le choix d'utiliser Docker pour la base de données MySQL, afin de :

- Permettre une configuration rapide et qui nécéssite peu d'installation.
- Éviter les conflits de version entre les environnements de développement.
- Avoir une application "kubernetes ready" pour une éventuelle mise en production.

Commande d'initialisation de la base de donnée :

```bash
docker compose up -d
```

*Pour l'arrêter :*

```bash
docker compose down
```

### Notes

Pour plus de confort lors de mes tests, j'ai ajouté phpmyadmin à la base de données, qui est accessible à l'adresse : [http://localhost:8081](http://localhost:8081)

- **Utilisateur :** `root`
- **Mot de passe :** `my_secret_password`

![image](assets/p2/phpmyadmin.png)

### 2. Lancer l'application

Commande d'exécution de l'application :

```bash
mvn spring-boot:run
```

L'application est accessible ensuite à l'adresse : [http://localhost:8080](http://localhost:8080)

## 💻 L'application

*Les données visibles dans la partie ci-dessous sont les données de par défaut générées au lancement de l'application par la class [LoadData](src/main/java/com/ipi/gestiondechampionnatapi/LoadData.java).*

### Partie front-end

L'application propose une interface utilisateur moderne permettant de :

- **Consulter les championnats** : Liste des championnats disponibles avec leurs informations

![Consulter les championnats](assets/p2/consulter_championnats.png)

- **Afficher les détails d'un championnat** : Classement des équipes, résultats des matchs

![Afficher les détails d'un championnat](assets/p2/afficher_details_championnat.png)

- **Visualiser les équipes** : Informations détaillées sur chaque équipe participante (en cliquant sur une équipe dans le classement)

![Visualiser les équipes](assets/p2/visualiser_equipes.png)

- **Suivre les résultats** : Historique des matchs et scores

![Classement d'un championnats](assets/p2/classement_championnat.png)

**Technologies utilisées :** Thymeleaf, Bootstrap, HTML5/CSS3

### Partie back office

#### Authentification

Accessible à l'adresse : [http://localhost:8080/backoffice](http://localhost:8080/backoffice), cette partie de l'application est réservée aux administrateurs, et nécessite une authentification. Si l'on n'est pas authentifié, on est redirigé vers la page de connexion */login*.

La class LoadData crée un utilisateur administrateur pour les tests :

```java
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
```

adresse email : `admin@email.fr`
mot de passe : `admin`

Ici, l'authentification est gérée par Spring Security, qui utilise BCrypt pour le hachage des mots de passe. de cette manière, les mots de passe ne sont jamais stockés en clair dans la base de données (Une bonne pratique serait de changer le mot de passe par défaut après la première connexion).

![admin dans phpMyAdmin](assets/p2/admin_phpmyadmin.png)

### Interface d'administration

Interface d'administration sécurisée pour la gestion complète des données :

- **Authentification** : Connexion sécurisée requise pour acceder au back office (page de login et d'acceuil ci-dessous)

![Authentification](assets/p2/authentification.png)

- **Gestion des championnats** : Création, modification et suppression
(Page de gestion et de création de championnats)

![Gestion des championnats](assets/p2/gestion_championnats.png)

- **Gestion des équipes** : Administration des équipes et leurs informations
(Page de gestion et de création d'équipes)
![Gestion des équipes](assets/p2/gestion_equipes.png)

- **Gestion des matchs** : Création de nouveaux matchs et saisie des résultats
(Page de gestion et de création de matchs)
![Gestion des matchs](assets/p2/gestion_matchs.png)

- **Dashboard administratif** : Vue d'ensemble avec statistiques
![Dashboard administratif](assets/p2/dashboard_admin.png)

## ✨ Cas pratique

Dans cette section, nous allons explorer un cas pratique d'utilisation de l'API de gestion de championnats. Nous allons simuler :

- la création d'un championnat,
- la création d'une équipe,
- l'affectation de l'équipe crée au championnat,
- l'affectation d'une équipe déjà existante et affecté, à ce deuxième championnat,
- et l'ajout d'un match.

### Cas pratique : Création d'un championnat

A partir du backoffice, dans la section "Gestion des championnats", on peut créer un nouveau championnat à partir du bouton "Nouveau championnat".

![Création d'un championnat](assets/p2/creation_championnat.png)

En remplissant et validant le formulaire, le nouveau championnat est créé et visible dans la liste des championnats.

![Championnats créés](assets/p2/championnats_crees.png)

![Liste des championnats](assets/p2/liste_championnats.png)

### Cas pratique : Ajout d'équipes

Une fois le championnat créé, on peut ajouter des équipes à ce championnat. Pour cela, on accède à la page de gestion des équipes, et on peut créer une nouvelle équipe ou en sélectionner une déjà existante.

Nous allons déjà créer une équipe, par exemple le "fc Saturne"

![Création d'une équipe](assets/p2/creation_equipe.png)

Ensuite nous allons affecter l'Olympique Lyonnais au championnat que nous venons de créer, en plus de la League 1.

![Affectation d'une équipe](assets/p2/affectation_equipe.png)

Au final, nous retrouvons bien notre nouvel équipe affectée au championnat, aisi que l'Olympique Lyonnais qui est déjà affecté à la League 1 et maintenant également à la "Coupe de l'espace".

![Équipe affectée au championnat](assets/p2/equipe_affectee_championnat.png)

### Cas pratique : Ajout d'un match

On peux maintenant ajouté le match entre les deux équipes, en créant la journée qui n'existe pas encore, et en ajoutant le match.

![Création d'une journée](assets/p2/creation_journee.png)

![Ajout d'un match](assets/p2/ajout_match.png)

![Liste des matchs](assets/p2/liste_matchs.png)


### Cas pratique : Conclusion

One retrouve bien le match dans la liste des matchs du championnat, avec les équipes et la journée associée.

Et bien sûr, on peux également modifier toutes les informations.

![Match ajouté](assets/p2/match_ajoute_acceuil.png)

![Match ajouté dans le championnat](assets/p2/match_ajoute_championnat.png)

![Match ajouté classement](assets/p2/match_ajoute_classement.png)

## 🔌 Requêtes API

*L'application ayant été commencée l'année dernière pour un projet sur les API, j'ai concervé un historique de requètes API ci-dessous que je laisse à titre informatif.*

Toutes les requêtes sont disponibles au format JSON dans le repertoire "requetes" à la racine du projet, et via ce [lien web Postman](https://www.postman.com/almoska/workspace/my-workspace).

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/eeb8e8a7-f1b1-4964-a2c7-2c1ef6ab6cd3)

## 🔒 Sécurité et validation

Les mots de passes des utilisateurs sont hashés via bcrypt

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/56d70977-26c5-4c02-ac5e-d87ee6e8e74a)

Les tentatives de modification d'un objet déjà existant en utilisant une requête POST avec un ID sont bloquées pour tous les objets, et retournent une bad request personalisée.

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/9a33bf26-29e7-4597-abc4-c22ae1a5f999)

Les tentatives de créer un championnat avec une date de début après la date de fin retournent également une bad request

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/39cb08d5-654b-48e6-9052-f43863eb290e)

De manière générale, j'ai essayé de faire des messages personnalisés. On peut les reconnaitre sur Postman avec la fin du titre [entre crochets]

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/90f6e139-35b5-4659-82b1-b7cc7d54878c)
