package MVC.controller;

import MVC.model.dao.UtilisateurDAO;
import MVC.model.pojo.Utilisateur;
import utils.PasswordHasher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/UpdateProfileServlet")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        
        // Vérification de l'ancien mot de passe
        if (!currentPassword.isEmpty() && !PasswordHasher.verifyPassword(currentPassword, utilisateur.getMotDePasse())) {
            request.setAttribute("error", "Le mot de passe actuel est faux.");
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            return;
        }
        
        // Vérification des nouveaux mots de passe
        if (!newPassword.isEmpty() && !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Les nouveaux mots de passe ne correspondent pas.");
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            return;
        }
        
        // Mise à jour des informations
        utilisateur.setNom(username);
        utilisateur.setEmail(email);
        if (!newPassword.isEmpty()) {
            utilisateur.setMotDePasse(PasswordHasher.hashPassword(newPassword)); // Hash du nouveau mot de passe
        }
        
        // Récupération de la photo de profil
        Part filePart = request.getPart("profileImage");
        if (filePart != null && filePart.getSize() > 0) {
            byte[] imageBytes = filePart.getInputStream().readAllBytes();
            utilisateur.setProfileImage(imageBytes); // Mettre à jour l'image de profil
        } else {
            System.out.println("Aucune image téléchargée.");
        }
        
        // Mise à jour dans la base de données
        boolean updated = utilisateurDAO.updateUtilisateur(utilisateur);
        System.out.println("Mise à jour réussie : " + updated);
        if (!updated) {
            request.setAttribute("error", "Erreur lors de la mise à jour du profil.");
            request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            return;
        }
        
        // Redirection vers main.jsp
        response.sendRedirect("main.jsp?filCode=" + request.getParameter("filCode"));

        System.out.println("filePart est null : " + (filePart == null));
        System.out.println("Taille du fichier : " + (filePart != null ? filePart.getSize() : "N/A"));
    }
}