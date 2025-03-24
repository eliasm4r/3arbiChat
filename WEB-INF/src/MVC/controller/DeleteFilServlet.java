package MVC.controller;

import MVC.model.dao.FilDiscussionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DeleteFilServlet")
public class DeleteFilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filIdStr = request.getParameter("filId");
        int filId;

        try {
            filId = Integer.parseInt(filIdStr); // Convertir l'ID en entier
        } catch (NumberFormatException e) {
            response.sendRedirect("main.jsp?error=Fil ID invalide");
            return;
        }

        FilDiscussionDAO filDAO = new FilDiscussionDAO();
        filDAO.deleteFilById(filId); // Utiliser la méthode pour supprimer par ID

        response.sendRedirect("main.jsp"); // Redirige vers la page principale après suppression
    }
}