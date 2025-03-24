package MVC.controller;

import MVC.model.dao.FilDiscussionDAO;
import MVC.model.pojo.FilDiscussion;
import MVC.model.pojo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ConnexionDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/UpdateFilServlet")
public class UpdateFilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filIdStr = request.getParameter("filId");
        String titre = request.getParameter("titre");
        String nouveauCode = request.getParameter("filCode");

        // Vérifiez si filIdStr est nul ou vide
        if (filIdStr == null || filIdStr.isEmpty()) {
            response.sendRedirect("confirmation.jsp?error=L'ID du fil est manquant.");
            return;
        }

        int filId;
        try {
            filId = Integer.parseInt(filIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("confirmation.jsp?error=L'ID du fil est invalide.");
            return;
        }

        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur == null) {
            response.sendRedirect("login.jsp");
            return; // Arrêtez l'exécution si l'utilisateur n'est pas connecté
        }

        try (Connection connection = ConnexionDB.getConnection()) {
            FilDiscussionDAO filDiscussionDAO = new FilDiscussionDAO(connection);

            // Vérifiez si le code existe déjà
            if (filDiscussionDAO.isCodeExists(nouveauCode, filId)) {
                response.sendRedirect("confirmation.jsp?error=Le code d'accès est déjà pris par un autre fil.");
                return;
            }

            // Mettez à jour le fil
            FilDiscussion filDiscussion = new FilDiscussion(filId, titre, new java.sql.Timestamp(System.currentTimeMillis()), utilisateur.getId(), nouveauCode);
            filDiscussionDAO.updateFil(filDiscussion);

            // Redirection vers la page de confirmation
            response.sendRedirect("confirmation.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de la mise à jour du fil de discussion", e);
        }
    }
}