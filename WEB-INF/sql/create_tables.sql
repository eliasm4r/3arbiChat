DROP TABLE IF EXISTS Abonne CASCADE;
DROP TABLE IF EXISTS Message CASCADE;
DROP TABLE IF EXISTS Fil CASCADE;
DROP TABLE IF EXISTS Utilisateur CASCADE;

-- Table Utilisateur
CREATE TABLE Utilisateur (
    id SERIAL PRIMARY KEY,            -- Identifiant unique auto-incrémenté
    nom VARCHAR(50) NOT NULL,         -- Nom de l'utilisateur
    email VARCHAR(100) UNIQUE NOT NULL, -- Adresse email unique
    mot_de_passe VARCHAR(255) NOT NULL, -- Mot de passe
    profile_image BYTEA               -- Colonne pour stocker l'image de profil en binaire
);

-- Table Fil
CREATE TABLE Fil (
    id SERIAL PRIMARY KEY,            -- Identifiant unique auto-incrémenté
    titre VARCHAR(100) NOT NULL,      -- Titre du fil
    date_creation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Date de création
    id_createur INT NOT NULL,         -- Référence au créateur (Utilisateur)
    fil_code VARCHAR(255),            -- Code unique pour le fil
    CONSTRAINT fk_createur FOREIGN KEY (id_createur) REFERENCES Utilisateur(id)
);

-- Table Message
CREATE TABLE Message (
    id SERIAL PRIMARY KEY,            -- Identifiant unique auto-incrémenté
    contenu TEXT NOT NULL,            -- Contenu du message (TEXT supporte les emojis avec UTF-8)
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Date d'envoi
    id_utilisateur INT NOT NULL,      -- Référence à l'utilisateur (auteur)
    id_fil INT NOT NULL,              -- Référence au fil de discussion
    CONSTRAINT fk_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id),
    CONSTRAINT fk_fil FOREIGN KEY (id_fil) REFERENCES Fil(id)
);

-- Table Abonne
CREATE TABLE Abonne (
    id_utilisateur INT NOT NULL,      -- Référence à l'utilisateur
    id_fil INT NOT NULL,              -- Référence au fil de discussion
    PRIMARY KEY (id_utilisateur, id_fil), -- Clé primaire composite
    CONSTRAINT fk_abonne_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id),
    CONSTRAINT fk_abonne_fil FOREIGN KEY (id_fil) REFERENCES Fil(id)
);