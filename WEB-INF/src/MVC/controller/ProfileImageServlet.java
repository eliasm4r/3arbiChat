package MVC.controller;

import MVC.model.dao.UtilisateurDAO;
import MVC.model.pojo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException; // Importation correcte

@WebServlet("/ProfileImageServlet")
public class ProfileImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur utilisateur = utilisateurDAO.getUtilisateurById(userId);
        
        response.setContentType("image/jpeg"); // Définir le type de contenu
        if (utilisateur != null && utilisateur.getProfileImage() != null) {
            response.getOutputStream().write(utilisateur.getProfileImage()); // Écrire l'image dans la réponse
            response.getOutputStream().flush(); // Assurez-vous de vider le flux
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}