<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="MVC.model.pojo.FilDiscussion" %>
<%@ page import="MVC.model.dao.FilDiscussionDAO" %>
<%@ page import="MVC.model.pojo.Utilisateur" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="MVC.model.dao.MessageDAO" %>
<%@ page import="MVC.model.pojo.Message" %>
<%@ page import="MVC.model.dao.UtilisateurDAO" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>3arbiChat - Accueil</title>
    <link rel="stylesheet" type="text/css" href="css/accueil.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@joeattardi/emoji-button@4.6.2/dist/index.min.css">
    <script src="https://cdn.jsdelivr.net/npm/@joeattardi/emoji-button@4.6.2/dist/index.min.js"></script>
    <style>
        .emoji-grid {
            display: grid;
            grid-template-columns: repeat(6, 1fr); /* 6 colonnes */
            gap: 10px; /* Espacement entre les emojis */
        }

        .emoji {
            cursor: pointer;
            font-size: 20px;
            text-align: center;
        }

        .emoji:hover {
            background-color: #f0f0f0; /* Couleur de survol */
            border-radius: 5px;
        }
        .message-area {
            border: 1px solid #ccc;
            padding: 10px;
            margin-top: 10px;
            height: 300px;
            overflow-y: auto;
            display: flex;
            flex-direction: column-reverse; /* Pour faire défiler les messages vers le bas */
            position: relative; /* Pour le positionnement absolu du bouton */
        }

        .message-item {
            display: flex;
            align-items: flex-start; /* Aligne les éléments au début */
            margin-bottom: 20px; /* Plus d'espace entre les messages */
        }

        .message-profile {
            margin-right: 10px; /* Espace entre l'image de profil et le message */
        }

        .message-content {
            background-color: rgb(36, 36, 36); /* Couleur de fond du message */
            padding: 10px;
            border-radius: 5px; /* Coins arrondis */
            max-width: 70%; /* Limite la largeur du message */
        }

        .message-author {
            color: rgb(255, 0, 242); /* Couleur différente pour le pseudo */
            font-weight: bold; /* Texte en gras */
        }

        .message-text {
            display: block; /* Pour s'assurer que le texte du message est sur une nouvelle ligne */
            margin-top: 5px; /* Espace entre le pseudo et le message */
        }

        .sidebar {
            background-color: #2c2c3e; /* Couleur de fond de la sidebar */
        }

        #filList {
            list-style-type: none;
            padding: 0;
            background-color: #2c2c3e;
            max-height: 520px; /* Hauteur maximale pour la liste des fils */
            overflow-y: auto; /* Permet le défilement */
        }

        #filList li {
            margin: 10px 0;
        }

        #filList button {
            display: block;
            width: 100%; /* Prend toute la largeur */
            background-color: rgb(26, 157, 245); /* Couleur violet */
            color: black; /* Couleur du texte en noir */
            padding: 15px; /* Augmentation de la hauteur */
            border: none; /* Pas de bordure */
            border-radius: 5px; /* Coins arrondis */
            cursor: pointer; /* Curseur en forme de main */
            transition: background-color 0.3s; /* Transition pour le survol */
            font-weight: bold; /* Texte en gras */
            font-size: 1.1em; /* Augmentation de la taille du texte */
        }

        #filList button:hover {
            background-color: #5a0462; /* Couleur au survol plus foncée */
        }

        .manage-button:hover {
            background-color: #6a0572; /* Couleur au survol */
        }

        .title-container {
            display: flex;
            justify-content: space-between; /* Aligne le titre à gauche et le bouton à droite */
            align-items: center; /* Aligne verticalement au centre */
        }

        .title-container h2 {
            margin: 0; /* Supprime les marges autour du titre */
        }
    </style>
</head>
<body>
    <%
        // Vérifiez si l'utilisateur est connecté
        HttpSession currentSession = request.getSession(false);
        Utilisateur utilisateur = null;

        if (currentSession != null) {
            utilisateur = (Utilisateur) currentSession.getAttribute("utilisateur");
        }

        if (utilisateur == null) {
            response.sendRedirect("login.jsp");
            return; // Arrêtez l'exécution de la page
        }

        // Récupération des fils de discussion
        FilDiscussionDAO filDAO = new FilDiscussionDAO();
        List<FilDiscussion> fils = filDAO.getFilsByUserId(utilisateur.getId());
        String filIdStr = request.getParameter("filId"); // Utiliser l'ID pour rejoindre le fil
        String filCode = request.getParameter("filCode"); // Utiliser le code pour rejoindre le fil

        // Récupération du fil par ID
        FilDiscussion filDiscussion = filIdStr != null ? filDAO.getFilById(Integer.parseInt(filIdStr)) : null;

        // Si aucun fil n'est trouvé par ID, essayer de le trouver par code
        if (filDiscussion == null && filCode != null) {
            filDiscussion = filDAO.getFilByCode(filCode);
            if (filDiscussion != null) {
                filIdStr = String.valueOf(filDiscussion.getId()); // Mettre à jour filIdStr avec l'ID du fil trouvé
            }
        }

        // Récupération des messages si un fil est sélectionné
        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = filDiscussion != null ? messageDAO.getMessagesByFilId(filDiscussion.getId()) : null;

        // Récupération du titre du fil sélectionné
        String filTitre = filDiscussion != null ? filDiscussion.getTitre() : "Choisissez un fil";
    %>

    <div class="sidebar">
        <img id="logo" src="img/logo.png" alt="Logo">
        <h2>Fils de discussion</h2>
        <ul id="filList">
            <%
                for (FilDiscussion fil : fils) {
            %>
                <li>
                    <form action="main.jsp" method="GET">
                        <input type="hidden" name="filId" value="<%= fil.getId() %>"> <!-- Utiliser l'ID -->
                        <input type="hidden" name="filCode" value="<%= fil.getFilCode() %>"> <!-- Utiliser le code -->
                        <button type="submit"><%= fil.getTitre() %></button>
                    </form>
                </li>
            <%
                }
            %>
        </ul>
        <div class="fixed-buttons">
            <a href="createFil.jsp" class="action-button">Créer un fil</a>
            <a href="joinFil.jsp" class="action-button">Rejoindre un fil</a>
        </div>
    </div>

    <div class="content">
        <div class="header">
            <div class="profile-image">
                <% if (utilisateur.getProfileImage() != null) { %>
                    <img src="ProfileImageServlet?id=<%= utilisateur.getId() %>" alt="Profile" onerror="this.style.display='none';">
                <% } else { %>
                    <div class="initials"></div>
                <% } %>
            </div>
            <div class="welcome-message">
                Salut, <%= utilisateur.getNom() %> !
            </div>
            <div class="header-buttons">
                <form action="editProfile.jsp" method="GET" class="edit-profile-form">
                    <input type="hidden" name="filCode" value="<%= request.getParameter("filCode") %>"> <!-- Champ caché pour le filCode -->
                    <button type="submit" class="edit-profile-button">Modifier le profil</button>
                </form>
                <form action="logout" method="GET" class="logout-form">
                    <button type="submit" class="logout-button">Déconnexion</button>
                </form>
            </div>
        </div>

        <!-- Conteneur pour le titre et le bouton -->
        <div class="title-container">
            <h2><%= filTitre %></h2>
            <form action="manageFil.jsp" method="GET">
                <input type="hidden" name="filId" value="<%= filDiscussion != null ? filDiscussion.getId() : "" %>">
                <input type="hidden" name="filCode" value="<%= filDiscussion != null ? filDiscussion.getFilCode() : "" %>">
                <button type="submit" class="manage-button">Gérer le fil</button>
            </form>
        </div>

        <div id="messageArea" class="message-area">
            <% if (messages != null && !messages.isEmpty()) { %>
                <ul>
                    <% for (Message message : messages) { %>
                        <li class="message-item">
                            <div class="message-profile">
                                <% UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
                                Utilisateur auteur = utilisateurDAO.getUtilisateurById(message.getAuteurId()); %>
                                <% if (auteur != null && auteur.getProfileImage() != null) { %>
                                    <img src="ProfileImageServlet?id=<%= auteur.getId() %>" alt="Profile" class="profile-image">
                                <% } else { %>
                                    <div class="initials"></div>
                                <% } %>
                            </div>
                            <div class="message-content">
                                <strong class="message-author"><%= auteur != null ? auteur.getNom() : "Inconnu" %>:</strong>
                                <span class="message-text"><%= message.getContenu() %></span>
                            </div>
                        </li>
                    <% } %>
                </ul>
            <% } %>
        </div>
        <div class="input-container" style="display: flex; width: 100%; position: relative;">
            <form action="SendMessageServlet" method="POST" style="width: 100%; display: flex; position: relative;">
                <input type="hidden" name="filId" value="<%= filDiscussion != null ? filDiscussion.getId() : "" %>">
                <input type="hidden" name="filCode" value="<%= filDiscussion != null ? filDiscussion.getFilCode() : "" %>">
                <!-- Zone d'input -->
                <input type="text" id="messageInput" name="messageContent" class="message-input" placeholder="Écrivez un message..." required style="flex: 1; padding-right: 40px;">
                <!-- Bouton d'emoji -->
                <button id="emojiButton" class="emoji-button" type="button" 
                    style="position: absolute; right: 270px; top: 50%; transform: translateY(-50%); background: none; border: none; cursor: pointer; font-size: 20px;">😊
                </button>
                <!-- Bouton d'envoi -->
                <button type="submit" class="send-button" style="flex: 0 0 20%; margin-left: 10px;">Envoyer</button>
            </form>
            <!-- Conteneur pour les emojis -->
            <div id="emojiPicker" class="emoji-picker" 
                style="display: none; position: absolute; bottom: 50px; right: 270px; background: white; border: 1px solid #ccc; border-radius: 5px; padding: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); max-height: 200px; overflow-y: auto;">
                <div class="emoji-grid">
                    <span class="emoji">😀</span>
                    <span class="emoji">😂</span>
                    <span class="emoji">😍</span>
                    <span class="emoji">😎</span>
                    <span class="emoji">😭</span>
                    <span class="emoji">👍</span>
                    <span class="emoji">❤️</span>
                    <span class="emoji">🔥</span>
                    <span class="emoji">🎉</span>
                    <span class="emoji">🥳</span>
                    <span class="emoji">🤔</span>
                    <span class="emoji">😅</span>
                    <span class="emoji">🙌</span>
                    <span class="emoji">✨</span>
                    <span class="emoji">🌟</span>
                    <span class="emoji">💯</span>
                    <span class="emoji">🥰</span>
                    <span class="emoji">😋</span>
                    <span class="emoji">🤩</span>
                    <span class="emoji">😇</span>
                    <span class="emoji">😜</span>
                    <span class="emoji">😡</span>
                    <span class="emoji">🤯</span>
                    <span class="emoji">😴</span>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

<script>
    const emojiButton = document.getElementById('emojiButton');
    const emojiPicker = document.getElementById('emojiPicker');
    const messageInput = document.getElementById('messageInput');

    // Afficher/masquer le conteneur d'emojis
    emojiButton.addEventListener('click', (event) => {
        event.preventDefault(); // Empêche le comportement par défaut du bouton
        emojiPicker.style.display = emojiPicker.style.display === 'none' ? 'block' : 'none';
    });

    // Ajouter l'emoji sélectionné à l'input
    emojiPicker.addEventListener('click', (event) => {
        if (event.target.classList.contains('emoji')) {
            const emoji = event.target.textContent;
            messageInput.value += emoji; // Ajoute l'emoji à l'input
            emojiPicker.style.display = 'none'; // Masque le conteneur d'emojis
        }
    });

    // Cacher le conteneur d'emojis si on clique en dehors
    document.addEventListener('click', (event) => {
        if (!emojiPicker.contains(event.target) && event.target !== emojiButton) {
            emojiPicker.style.display = 'none';
        }
    });

    document.querySelector('form').addEventListener('submit', function(event) {
            const inputs = document.querySelectorAll('input[type="text"], input[type="email"], input[type="password"]');

            inputs.forEach(input => {
                const value = input.value;
                input.value = value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
            });
        });
</script>