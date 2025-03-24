-- Insérer des utilisateurs
INSERT INTO Utilisateur (nom, email, mot_de_passe) VALUES
('Elias', 'elias@example.com', 'de7ee76e7373c19bf7418e69cadecba96107bbb6d99524c516e1d37605dca829'),
('Anis', 'anis@example.com', '84fe5cc65e8bb989726f0419fe7430a2f8f416cf6d5f8c61ab14bfff78909105'),
('Ilyes', 'ilyes@example.com', '08484f7877f8e7b7b62ee2972ea781193970f441919e7e89bcf1111c4786faab'),
('Benjamin', 'benjamin@example.com', '8ee9938e4b960a50540f1ca9299facc5a5f342d0848b402c322fd14592e4bc32'),
('Imad', 'imad@example.com', '85b11195aa8c202f4975f7be55674fa8ab4d48380a7c2cc4bb828821d61e60a7'),
('Hugo', 'hugo@example.com', '0478721f1106c2a631a90181bac7efc77767a3903eb9220687bff8a14e940fa7');

-- Insérer des fils de discussion
INSERT INTO Fil (titre, id_createur, fil_code) VALUES
('Espada', 1, 'espada'), -- Elias crée le fil Espada
('League of Legends', 2, 'league'), -- Anis crée le fil League of Legends
('Projet IUT', 3, 'projet'); -- Ilyes crée le fil Projet IUT

-- Insérer des messages avec emojis
INSERT INTO Message (contenu, date, id_utilisateur, id_fil) VALUES
-- Messages dans le fil Espada
('Salut les gars ! 😃', '2025-02-10 10:00:00', 1, 1), -- Elias dans Espada
('J’ai envie de chier ! 💩', '2025-02-10 10:05:00', 1, 1), -- Elias dans Espada
('Encore ? T’abuse ! 😂', '2025-02-10 10:06:00', 5, 1), -- Imad répond dans Espada
('T’es vraiment cacaman mec ! 🎉', '2025-02-10 10:10:00', 4, 1), -- Benjamin dans Espada

-- Messages dans le fil League of Legends
('Quelqu’un a testé le nouveau patch ? 🚀', '2025-02-09 15:00:00', 2, 2), -- Anis dans League of Legends
('Oui, le buff de Yasuo est abusé ! 😡', '2025-02-09 15:10:00', 6, 2), -- Hugo dans League of Legends
('Vraiment trop broken ce champion. 🤔', '2025-02-09 15:20:00', 3, 2), -- Ilyes dans League of Legends

-- Messages dans le fil Projet IUT
('Quelqu’un a commencé le projet ? 😃', '2025-02-08 09:00:00', 3, 3), -- Ilyes dans Projet IUT
('Oui, j’ai fait la partie SQL hier soir. 🚀', '2025-02-08 09:30:00', 2, 3), -- Anis dans Projet IUT
('On se réunit quand pour avancer ? 👨‍💻', '2025-02-08 10:00:00', 5, 3); -- Imad dans Projet IUT

-- Insérer des abonnés
INSERT INTO Abonne (id_utilisateur, id_fil) VALUES
-- Abonnés au fil Espada
(1, 1), -- Elias s'abonne à Espada
(2, 1), -- Anis s'abonne à Espada
(3, 1), -- Ilyes s'abonne à Espada
(4, 1), -- Benjamin s'abonne à Espada
(5, 1), -- Imad s'abonne à Espada
(6, 1), -- Hugo s'abonne à Espada

-- Abonnés au fil League of Legends
(2, 2), -- Anis s'abonne à League of Legends
(3, 2), -- Ilyes s'abonne à League of Legends
(6, 2), -- Hugo s'abonne à League of Legends

-- Abonnés au fil Projet IUT
(1, 3), -- Elias s'abonne à Projet IUT
(2, 3), -- Anis s'abonne à Projet IUT
(3, 3), -- Ilyes s'abonne à Projet IUT
(5, 3); -- Imad s'abonne à Projet IUT