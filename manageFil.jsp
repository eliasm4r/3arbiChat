<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="MVC.model.dao.FilDiscussionDAO" %>
<%@ page import="MVC.model.pojo.FilDiscussion" %>
<%@ page import="MVC.model.pojo.Utilisateur" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Gérer le Fil - 3arbiChat</title>
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
        .bg-light-pink {
            background-color: #ff95ca;
        }
        input[type="text"] {
            color: black; /* Couleur du texte en noir */
            background-color: white; /* Fond blanc pour l'input */
        }
        .title {
            color: #ff95ca; /* Couleur rose */
            text-align: center; /* Centrer le texte */
            font-weight: bold; /* Texte en gras */
        }
    </style>
</head>
<body class="bg-gray-800 text-white">
    <%
        HttpSession currentSession = request.getSession(false);
        Utilisateur utilisateur = null;

        if (currentSession != null) {
            utilisateur = (Utilisateur) currentSession.getAttribute("utilisateur");
        }

        if (utilisateur == null) {
            response.sendRedirect("login.jsp");
            return; // Arrêtez l'exécution de la page
        }

        String filCode = request.getParameter("filCode"); // Récupérer le code du fil
        FilDiscussionDAO filDAO = new FilDiscussionDAO();
        FilDiscussion fil = filDAO.getFilByCode(filCode); // Utiliser getFilByCode

        if (fil == null) {
            out.println("Fil non trouvé.");
            return; // Arrêtez l'exécution si le fil n'est pas trouvé
        }

        // Récupérer la liste des utilisateurs abonnés
        List<Utilisateur> utilisateurs = filDAO.getUtilisateursByFilId(fil.getId());
    %>

    <div class="max-w-md mx-auto mt-10 p-4 bg-gray-900 rounded">
        <h2 class="title">Gérer le Fil: <%= fil.getTitre() %></h2>
        <p>Participants: <%= utilisateurs.size() %></p>
        <ul class="mt-2">
            <% 
            // Afficher les participants à partir du deuxième
            for (int i = 0; i < utilisateurs.size(); i++) { 
                Utilisateur user = utilisateurs.get(i); 
            %>
                <li><%= user.getNom() %> (<%= user.getEmail() %>)</li>
            <% } %>
        </ul>
        <form action="UpdateFilServlet" method="POST">
            <input type="hidden" name="filId" value="<%= fil.getId() %>"> <!-- Champ caché pour l'ID du fil -->
            <label for="filName" class="block mt-4">Nom du Fil:</label>
            <input type="text" id="filName" name="titre" value="<%= fil.getTitre() %>" class="mt-2 w-full p-2 rounded" required>
            <label for="newFilCode" class="block mt-4">Nouveau Code d'Accès:</label>
            <input type="text" id="newFilCode" name="filCode" value="<%= fil.getFilCode() %>" class="mt-2 w-full p-2 rounded" required>
            <button type="submit" class="mt-4 bg-light-pink text-white px-4 py-2 rounded">Mettre à jour</button>
        </form>
        <form action="DeleteFilServlet" method="POST">
            <input type="hidden" name="filId" value="<%= fil.getId() %>"> <!-- Utiliser l'ID -->
            <button type="submit" class="mt-4 bg-red-600 text-white px-4 py-2 rounded">Supprimer le Fil</button>
        </form>
        <br>
        <a href="main.jsp" class="mt-2 text-white">Retour</a>
    </div>
</body>
</html>

<script>
    document.querySelector('form').addEventListener('submit', function(event) {
            const inputs = document.querySelectorAll('input[type="text"], input[type="email"], input[type="password"]');

            inputs.forEach(input => {
                const value = input.value;
                if (value.includes('<') || value.includes('>')) {
                    alert("Les caractères '<' et '>' ne sont pas autorisés.");
                    event.preventDefault(); // Empêche l'envoi du formulaire
                }
            });
        });
</script>