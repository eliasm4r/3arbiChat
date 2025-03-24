package MVC.model.dao;

import MVC.model.pojo.Message;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection connection;

    public MessageDAO() {
        this.connection = ConnexionDB.getConnection();
    }

    public List<Message> getMessagesByFilId(int filId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE id_fil = ?"; // Utilisez 'id_fil' pour correspondre à votre table
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, filId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Assurez-vous que le constructeur de Message correspond aux colonnes de votre table
                messages.add(new Message(rs.getInt("id"), rs.getString("contenu"), rs.getInt("id_utilisateur"), rs.getInt("id_fil")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void ajouterMessage(Message message) {
        String sql = "INSERT INTO message (contenu, id_utilisateur, id_fil) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, message.getContenu());
            pstmt.setInt(2, message.getAuteurId());
            pstmt.setInt(3, message.getFilId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}