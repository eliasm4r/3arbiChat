package MVC.model.dao;

import MVC.model.pojo.FilDiscussion;
import MVC.model.pojo.Utilisateur;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilDiscussionDAO {
    private Connection connection;

    public FilDiscussionDAO() {
        this.connection = ConnexionDB.getConnection();
    }

    public FilDiscussionDAO(Connection connection) {
        this.connection = connection;
    }

    public List<FilDiscussion> getFilsByUserId(int userId) {
        List<FilDiscussion> fils = new ArrayList<>();
        String sql = "SELECT Fil.id, Fil.titre, Fil.date_creation, Fil.id_createur, Fil.fil_code " +
                     "FROM Fil " +
                     "WHERE id_createur = ? OR id IN (SELECT id_fil FROM Abonne WHERE id_utilisateur = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fils.add(new FilDiscussion(
                    rs.getInt("id"), 
                    rs.getString("titre"), 
                    rs.getTimestamp("date_creation"),
                    rs.getInt("id_createur"),
                    rs.getString("fil_code") // Champ pour le code
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fils;
    }

    public void creerFilDiscussion(FilDiscussion fil, int userId) {
        String sql = "INSERT INTO Fil (titre, id_createur, fil_code) VALUES (?, ?, ?)";
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fil.getTitre());
            pstmt.setInt(2, fil.getIdCreateur());
            pstmt.setString(3, fil.getFilCode());
            pstmt.executeUpdate();
            
            // Ne pas ajouter l'utilisateur comme participant ici
            System.out.println("Fil ajouté avec succès : " + fil.getTitre());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void joinFil(int filId, int userId) {
        System.out.println("Tentative d'ajout de l'utilisateur " + userId + " au fil " + filId);
        if (!isUserSubscribed(filId, userId)) { // Vérifiez si l'utilisateur est déjà abonné
            String sql = "INSERT INTO Abonne (id_fil, id_utilisateur) VALUES (?, ?)";
            try (Connection conn = ConnexionDB.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, filId);
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
                System.out.println("Utilisateur " + userId + " ajouté au fil " + filId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("L'utilisateur " + userId + " est déjà abonné au fil " + filId);
        }
    }

    public int getLastInsertedFilId() {
        String sql = "SELECT LASTVAL()";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public FilDiscussion getFilByCode(String code) {
        String sql = "SELECT * FROM Fil WHERE fil_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new FilDiscussion(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getTimestamp("date_creation"),
                    rs.getInt("id_createur"),
                    rs.getString("fil_code") // Champ pour le code
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Aucun fil trouvé
    }

    public void updateFil(FilDiscussion fil) {
        String sql = "UPDATE Fil SET titre = ?, fil_code = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fil.getTitre());
            pstmt.setString(2, fil.getFilCode());
            pstmt.setInt(3, fil.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteFilByCode(String filCode) {
        String sql = "DELETE FROM Fil WHERE fil_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, filCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions
        }
    }
    
    public FilDiscussion getFilById(int id) {
        String sql = "SELECT * FROM Fil WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new FilDiscussion(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getTimestamp("date_creation"),
                    rs.getInt("id_createur"),
                    rs.getString("fil_code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Aucun fil trouvé
    }

    public void deleteFilById(int id) {
        deleteMessagesByFilId(id); // Supprimer les messages associés
        deleteAbonnesByFilId(id); // Supprimer les abonnés associés
        String sql = "DELETE FROM Fil WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions
        }
    }

    public void deleteMessagesByFilId(int filId) {
        String sql = "DELETE FROM Message WHERE id_fil = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, filId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Messages supprimés : " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions
        }
    }

    public void deleteAbonnesByFilId(int filId) {
        String sql = "DELETE FROM Abonne WHERE id_fil = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, filId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Abonnés supprimés : " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions
        }
    }

    public List<Utilisateur> getUtilisateursByFilId(int filId) {
        Set<Utilisateur> utilisateurs = new HashSet<>(); // Utiliser un Set pour éviter les doublons
        String sql = "SELECT u.id, u.nom, u.email, u.mot_de_passe, u.profile_image " +
                    "FROM Utilisateur u " +
                    "JOIN Abonne a ON u.id = a.id_utilisateur " +
                    "WHERE a.id_fil = ? " +
                    "UNION " +
                    "SELECT u.id, u.nom, u.email, u.mot_de_passe, u.profile_image " +
                    "FROM Utilisateur u " +
                    "WHERE u.id = (SELECT id_createur FROM Fil WHERE id = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, filId);
            pstmt.setInt(2, filId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("mot_de_passe"),
                    rs.getBytes("profile_image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Convertir le Set en List et trier si nécessaire
        List<Utilisateur> sortedUtilisateurs = new ArrayList<>(utilisateurs);
        sortedUtilisateurs.sort(Comparator.comparingInt(Utilisateur::getId)); // Trier par ID
        return sortedUtilisateurs;
    }

    public boolean isCodeExists(String code, int filId) {
        if (connection == null) {
            throw new IllegalStateException("Connexion à la base de données non initialisée.");
        }
    
        String sql = "SELECT COUNT(*) FROM Fil WHERE fil_code = ? AND id != ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setInt(2, filId); // Exclure le fil en cours de mise à jour
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retourne vrai si le code existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retourne faux si une erreur se produit
    }

    public boolean isUserSubscribed(int filId, int userId) {
        String sql = "SELECT COUNT(*) FROM Abonne WHERE id_fil = ? AND id_utilisateur = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, filId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retourne vrai si l'utilisateur est déjà abonné
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retourne faux si une erreur se produit
    }
}