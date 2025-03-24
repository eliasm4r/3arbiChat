package MVC.controller;

import MVC.model.dao.MessageDAO;
import MVC.model.pojo.Message;
import MVC.model.pojo.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String messageContent = request.getParameter("messageContent");
        String filIdStr = request.getParameter("filId");
        String filCode = request.getParameter("filCode");

        if (filIdStr == null || filIdStr.isEmpty()) {
            response.sendRedirect("main.jsp?error=Fil ID manquant");
            return;
        }

        int filId;
        try {
            filId = Integer.parseInt(filIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("main.jsp?error=Fil ID invalide");
            return;
        }

        int auteurId = ((Utilisateur) session.getAttribute("utilisateur")).getId();

        // Nettoyer le contenu du message pour empêcher les balises HTML
        messageContent = sanitizeInput(messageContent);

        // Vérifier si le message est vide après nettoyage
        if (messageContent == null || messageContent.trim().isEmpty()) {
            response.sendRedirect("main.jsp?error=Message vide");
            return;
        }

        // Créer un nouvel objet Message
        Message message = new Message(0, messageContent, auteurId, filId);
        MessageDAO messageDAO = new MessageDAO();
        messageDAO.ajouterMessage(message); // Ajouter le message à la base de données

        // Envoyer le message via WebSocket
        ChatEndpoint.broadcastMessage(messageContent, auteurId); // Méthode à créer dans ChatEndpoint

        // Rediriger vers le fil de discussion
        response.sendRedirect("main.jsp?filId=" + filId + "&filCode=" + filCode);
    }

    /**
     * Méthode pour nettoyer les entrées utilisateur et empêcher les balises HTML.
     * Remplace les caractères spéciaux par leurs équivalents HTML.
     */
    private String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}