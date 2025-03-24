-- Requêtes SQL fréquentes pour PostgreSQL

-- 1. Récupérer tous les fils d'un utilisateur
SELECT Fil.id, Fil.titre, Fil.date_creation
FROM Fil
JOIN Abonne ON Fil.id = Abonne.id_fil
WHERE Abonne.id_utilisateur = 1;

-- 2. Récupérer tous les messages d'un fil
SELECT Message.id, Message.contenu, Message.date, Utilisateur.nom AS auteur
FROM Message
JOIN Utilisateur ON Message.id_utilisateur = Utilisateur.id
WHERE Message.id_fil = 1
ORDER BY Message.date;

-- 3. Ajouter un nouvel abonné à un fil
INSERT INTO Abonne (id_utilisateur, id_fil) VALUES (3, 2);

-- 4. Supprimer un message
DELETE FROM Message WHERE id = 1;

-- 5. Compter le nombre d'abonnés par fil
SELECT Fil.titre, COUNT(Abonne.id_utilisateur) AS nb_abonnes
FROM Fil
LEFT JOIN Abonne ON Fil.id = Abonne.id_fil
GROUP BY Fil.titre;

-- 6. Récupérer tous les utilisateurs abonnés à un fil donné
SELECT Utilisateur.nom, Utilisateur.email
FROM Utilisateur
JOIN Abonne ON Utilisateur.id = Abonne.id_utilisateur
WHERE Abonne.id_fil = 1; -- Remplacer 1 par l'id du fil

-- 7. Récupérer les fils créés par un utilisateur donné
SELECT id, titre, date_creation
FROM Fil
WHERE id_createur = 1; -- Remplacer 1 par l'id de l'utilisateur

-- 8. Récupérer les messages postés par un utilisateur donné
SELECT Message.id, Message.contenu, Message.date, Fil.titre AS fil
FROM Message
JOIN Fil ON Message.id_fil = Fil.id
WHERE Message.id_utilisateur = 1; -- Remplacer 1 par l'id de l'utilisateur

-- 9. Récupérer les fils sans abonnés
SELECT Fil.id, Fil.titre
FROM Fil
LEFT JOIN Abonne ON Fil.id = Abonne.id_fil
WHERE Abonne.id_utilisateur IS NULL;

-- 10. Récupérer les messages contenant un mot-clé spécifique
SELECT Message.id, Message.contenu, Message.date, Utilisateur.nom AS auteur
FROM Message
JOIN Utilisateur ON Message.id_utilisateur = Utilisateur.id
WHERE Message.contenu ILIKE '%projet%'; -- Remplacer 'projet' par le mot-clé recherché

-- 11. Récupérer les messages les plus récents d'un fil (limité à 5)
SELECT Message.id, Message.contenu, Message.date, Utilisateur.nom AS auteur
FROM Message
JOIN Utilisateur ON Message.id_utilisateur = Utilisateur.id
WHERE Message.id_fil = 1 -- Remplacer 1 par l'id du fil
ORDER BY Message.date DESC
LIMIT 5;

-- 12. Récupérer le nombre total de messages par fil
SELECT Fil.titre, COUNT(Message.id) AS nb_messages
FROM Fil
LEFT JOIN Message ON Fil.id = Message.id_fil
GROUP BY Fil.titre;

-- 13. Récupérer les utilisateurs qui n'ont pas encore posté de message
SELECT Utilisateur.nom, Utilisateur.email
FROM Utilisateur
LEFT JOIN Message ON Utilisateur.id = Message.id_utilisateur
WHERE Message.id IS NULL;

-- 14. Récupérer les fils avec le nombre d'abonnés et de messages
SELECT Fil.titre, COUNT(DISTINCT Abonne.id_utilisateur) AS nb_abonnes, COUNT(Message.id) AS nb_messages
FROM Fil
LEFT JOIN Abonne ON Fil.id = Abonne.id_fil
LEFT JOIN Message ON Fil.id = Message.id_fil
GROUP BY Fil.titre;

-- 15. Supprimer un abonné d'un fil
DELETE FROM Abonne
WHERE id_utilisateur = 1 AND id_fil = 2; -- Remplacer par l'id de l'utilisateur et du fil