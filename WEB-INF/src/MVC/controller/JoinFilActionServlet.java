package MVC.controller;

import MVC.model.dao.FilDiscussionDAO;
import MVC.model.pojo.Utilisateur;
import utils.ConnexionDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/JoinFilActionServlet")
public class JoinFilActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int filId = Integer.parseInt(request.getParameter("filId"));
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");

        try (Connection connection = ConnexionDB.getConnection()) {
            FilDiscussionDAO filDAO = new FilDiscussionDAO(connection);
            filDAO.joinFil(filId, utilisateur.getId());
            response.sendRedirect("main.jsp");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'adhésion au fil de discussion", e);
        }
    }
}