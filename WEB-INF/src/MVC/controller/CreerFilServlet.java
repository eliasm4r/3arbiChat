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

@WebServlet("/CreerFilServlet")
public class CreerFilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titre = request.getParameter("titre");
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        if (utilisateur != null) {
            try (Connection connection = ConnexionDB.getConnection()) {
                FilDiscussionDAO filDiscussionDAO = new FilDiscussionDAO(connection);

                // Génération du code à partir du premier mot du titre
                String baseCode = titre.split(" ")[0].toLowerCase();
                String code = baseCode;
                int suffix = 0;

                // Vérifiez si le code existe déjà
                while (filDiscussionDAO.isCodeExists(code, -1)) {
                    suffix++;
                    code = baseCode + suffix;
                }

                // Créez un nouvel objet FilDiscussion
                FilDiscussion filDiscussion = new FilDiscussion(0, titre, new java.sql.Timestamp(System.currentTimeMillis()), utilisateur.getId(), code);
                filDiscussionDAO.creerFilDiscussion(filDiscussion, utilisateur.getId()); // Passer l'ID de l'utilisateur

                // Redirection vers la page de confirmation
                response.sendRedirect("confirmation.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Erreur lors de la création du fil de discussion", e);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}