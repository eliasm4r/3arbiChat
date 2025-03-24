package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    static public Connection getConnection() {
        String url = "jdbc:postgresql://psqlserv/but2";
        String username = "eliasmarucaetu";
        String pswd = "moi";
        String jdbcDriver = "org.postgresql.Driver";

        try {
            Class.forName(jdbcDriver); // Charger le driver JDBC
            return DriverManager.getConnection(url, username, pswd);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        // Test de connexion à la base de données
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Connexion réussie !");
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Échec de la connexion.");
        }
    }
}