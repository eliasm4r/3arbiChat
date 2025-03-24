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

@WebServlet("/GetMessagesServlet")
public class GetMessagesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filIdStr = request.getParameter("filId");
        int filId = Integer.parseInt(filIdStr); // Convertir l'ID du fil

        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getMessagesByFilId(filId); // Récupérer les messages

        // Construire la réponse JSON manuellement
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            jsonBuilder.append("{")
                .append("\"id\":").append(message.getId()).append(",")
                .append("\"contenu\":\"").append(message.getContenu()).append("\",")
                .append("\"auteurId\":").append(message.getAuteurId()).append(",")
                .append("\"filId\":").append(message.getFilId())
                .append("}");
            if (i < messages.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");

        // Définir le type de contenu et envoyer la réponse
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }
}