package MVC.controller;

import MVC.model.dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import MVC.model.pojo.Utilisateur;

@WebServlet("/login") // Assurez-vous que ce chemin est correct
public class ConnexionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("passwd");

        // Hachage du mot de passe pour la vérification
        String hashedPassword = hashPassword(password);

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur utilisateur = utilisateurDAO.verifierUtilisateur(email, hashedPassword); // Utilisez le mot de passe haché

        if (utilisateur != null) {
            // Stockez l'utilisateur dans la session
            request.getSession().setAttribute("utilisateur", utilisateur);
            // Rediriger vers main.jsp
            response.sendRedirect("main.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Identifiants invalides");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}