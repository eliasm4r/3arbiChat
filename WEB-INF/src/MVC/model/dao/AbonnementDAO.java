package MVC.model.dao;

import java.sql.*;
import java.util.*;


public class AbonnementDAO {
    private Connection connection;
    
    public AbonnementDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void ajouterAbonnement(int utilisateurId, int discussionId) throws SQLException {
        String sql = "INSERT INTO Abonne (uno, dno) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utilisateurId);
            stmt.setInt(2, discussionId);
            stmt.executeUpdate();
        }
    }
    
    public List<Integer> getDiscussionsParUtilisateur(int utilisateurId) throws SQLException {
        List<Integer> discussions = new ArrayList<>();
        String sql = "SELECT dno FROM Abonne WHERE uno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                discussions.add(rs.getInt("dno"));
            }
        }
        return discussions;
    }
}