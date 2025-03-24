package MVC.model.dao;

import MVC.model.pojo.Utilisateur;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO {
    
    // Méthode pour ajouter un utilisateur avec l'URL de l'image de profil
    public boolean ajouterUtilisateur(String username, String email, String password) {
        String sql = "INSERT INTO Utilisateur (nom, email, mot_de_passe) VALUES (?, ?, ?)";
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // Insérez le mot de passe tel quel
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
    
    public Utilisateur verifierUtilisateur(String email, String password) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND mot_de_passe = ?";
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password); // Comparez le mot de passe haché
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] profileImage = rs.getBytes("profile_image"); // Récupérer l'image en binaire
                return new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("email"), rs.getString("mot_de_passe"), profileImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public boolean updateUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE Utilisateur SET nom = ?, email = ?, mot_de_passe = ?, profile_image = ? WHERE id = ?";
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getMotDePasse());
    
            // Enregistrer l'image en binaire
            if (utilisateur.getProfileImage() != null) {
                pstmt.setBytes(4, utilisateur.getProfileImage());
            } else {
                pstmt.setNull(4, java.sql.Types.NULL); // Utilisez java.sql.Types.NULL pour définir à NULL
            }
    
            pstmt.setInt(5, utilisateur.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Utilisateur getUtilisateurById(int id) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM Utilisateur WHERE id = ?"; // Assurez-vous que le nom de la table est correct
    
        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] profileImage = rs.getBytes("profile_image"); // Récupérer l'image en binaire
                utilisateur = new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("email"), rs.getString("mot_de_passe"), profileImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }
}