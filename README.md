# Gestion-de-championnat-API

Gestion des requêtes API d'une application de gestion de championnat avec Spring.
Réalisé pour les tables ci-dessous.

![image](assets/p2/db_model.png)

# Lancer la base de donnée

Pour lancer la base de donnée, il faut avoir Docker et Docker Compose installés sur votre machine

Pour lancer la base de donnée Docker depuis la racine du projet : 

```bash
docker-compose up -d
```

*Pour l'arrêter :*

```bash
docker-compose down
```

Pour plus de confort lors de mes tests, j'ai ajouté phpmyadmin à la base de donnée, qui est accessible à l'adresse : [http://localhost:8081](http://localhost:8080)

utilisateur : `root`<br>
mot de passe : `my_secret_password`

![image](assets/p2/phpmyadmin.png)

# Lancer l'application

Pour lancer l'application, il faut utiliser la commande :

```bash
mvn spring-boot:run
```

L'application est accessible ensuite à l'adresse : [http://localhost:8080](http://localhost:8080)


# Requêtes API

*L'application ayant été commencée l'année dernière pour un projet sur les API, j'ai concervé un historique de requètes API ci-dessous que je laisse à titre informatif.*

Toutes les requêtes sont disponibles au format JSON dans le repertoire "requetes" à la racine du projet, et via ce lien web Postman : https://www.postman.com/almoska/workspace/my-workspace.

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/eeb8e8a7-f1b1-4964-a2c7-2c1ef6ab6cd3)

# Un peu de réalisme

Les mots de passes des utilisateurs sont hashés via bcrypt

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/56d70977-26c5-4c02-ac5e-d87ee6e8e74a)


Les tentatives de modification d'un objet déjà existant en utilisant une requête POST avec un ID sont bloquées pour tous les objets, et retournent une bad request personalisée.

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/9a33bf26-29e7-4597-abc4-c22ae1a5f999)


Les tentatives de créer un championnat avec une date de début après la date de fin retournent également une bad request

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/39cb08d5-654b-48e6-9052-f43863eb290e)


De manière générale, j'ai essayé de faire des messages personnalisés. On peut les reconnaitre sur Postman avec la fin du titre [entre crochets]

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/90f6e139-35b5-4659-82b1-b7cc7d54878c)

