# Gestion-de-championnat-API

Gestion des requêtes API d'une application de gestion de championnat avec Spring.
Réalisé pour les tables ci-dessous.

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/9a57e53c-8b42-4df1-ae0f-a82f13535397)

# Détail

Pour lancer la base de donnée Docker depuis la racine du projet : ```docker-compose up -d```


Toute les requêtes sont disponibles via ce lien web Postman : https://www.postman.com/almoska/workspace/my-workspace.

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/eeb8e8a7-f1b1-4964-a2c7-2c1ef6ab6cd3)

# Un peu de réalisme

Les mots de passes des utilisateurs sont hashé via bcrypt

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/56d70977-26c5-4c02-ac5e-d87ee6e8e74a)


Les tentatives de modification d'un objet déjà existant en utilisant une requête POST avec un ID sont bloquées pour tout les objets, et retourne une bad request personalisée.

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/9a33bf26-29e7-4597-abc4-c22ae1a5f999)


Les tentativent des créer un championnat avec un date de début après lla date de fin retournes également une bad request

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/39cb08d5-654b-48e6-9052-f43863eb290e)


De manière général, j'ai essayé de faire des message personnalisés. On peux les reconnaitre sur Postman avec la fin du titre [entre crochets]

![image](https://github.com/Alvin-Kita/Gestion-de-championnat-API/assets/117522876/90f6e139-35b5-4659-82b1-b7cc7d54878c)













