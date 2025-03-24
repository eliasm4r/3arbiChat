package MVC.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import MVC.model.dao.UtilisateurDAO;

@WebServlet("/Register")
public class InscriptionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("passwd");

        String hashedPassword = hashPassword(password);

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        boolean success = utilisateurDAO.ajouterUtilisateur(username, email, hashedPassword); 

        if (success) {
            // Rediriger vers la page de connexion après l'inscription
            response.sendRedirect("login.jsp?success=true");
        } else {
            // Gérer l'échec de l'inscription
            response.sendRedirect("signup.jsp?error=Utilisateur déjà existant");
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