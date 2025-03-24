package MVC.controller;

import MVC.model.dao.MessageDAO;
import MVC.model.pojo.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/DisplayMessagesServlet")
public class DisplayMessagesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filIdStr = request.getParameter("filId");
        int filId = Integer.parseInt(filIdStr); // Convertir l'ID du fil

        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getMessagesByFilId(filId); // Récupérer les messages

        // Stocker les messages dans la requête
        request.setAttribute("messages", messages);

        // Rediriger vers la JSP
        request.getRequestDispatcher("main.jsp").forward(request, response);
    }
}