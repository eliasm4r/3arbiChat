package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePasswords {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }

    public static void updatePasswordInDatabase(String email, String newPassword) {
        String hashedPassword = hashPassword(newPassword);
        String sql = "UPDATE Utilisateur SET mot_de_passe = ? WHERE email = ?";

        try (Connection connection = ConnexionDB.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Mot de passe mis à jour pour l'utilisateur : " + email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[][] users = {
            {"elias@example.com", "elias"},
            {"anis@example.com", "anis"},
            {"ilyes@example.com", "ilyes"},
            {"benjamin@example.com", "benjamin"},
            {"imad@example.com", "imad"},
            {"hugo@example.com", "hugo"}
        };

        for (String[] user : users) {
            updatePasswordInDatabase(user[0], user[1]);
        }
    }
}