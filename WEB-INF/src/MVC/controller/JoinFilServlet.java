package MVC.controller;

import MVC.model.dao.FilDiscussionDAO;
import MVC.model.pojo.FilDiscussion;
import MVC.model.pojo.Utilisateur;
import utils.ConnexionDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/JoinFilServlet")
public class JoinFilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code"); // Récupération du code
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");

        try (Connection connection = ConnexionDB.getConnection()) {
            FilDiscussionDAO filDAO = new FilDiscussionDAO(connection);
            FilDiscussion filDiscussion = filDAO.getFilByCode(code); // Récupération du fil par code

            if (filDiscussion != null) {
                // Ajoutez l'utilisateur au fil
                filDAO.joinFil(filDiscussion.getId(), utilisateur.getId());
                response.sendRedirect("main.jsp"); // Redirection vers la page principale
            } else {
                // Gérer le cas où le code est invalide
                request.setAttribute("error", "Code de fil invalide.");
                request.getRequestDispatcher("joinFil.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la recherche du fil", e);
        }
    }
}