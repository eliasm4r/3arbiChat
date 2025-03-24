<%@ page pageEncoding="UTF-8" %>
<%@ page import="MVC.model.pojo.Utilisateur" %>
<%
    // Vérifiez si l'utilisateur est connecté
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur == null) {
        response.sendRedirect("login.jsp"); // Redirigez vers la page de connexion
        return; // Arrêtez l'exécution de la JSP
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Modifier le profil - 3arbiChat</title>
    <link rel="stylesheet" type="text/css" href="css/accueil.css">
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
        .bg-light-pink {
            background-color: #ff95ca; 
        }
    </style>
</head>
<body class="bg-gray-800 text-white">
    <div class="max-w-4xl mx-auto mt-10 p-4 bg-gray-900 rounded-lg shadow-lg">
        <h2 class="text-title">Modifier le profil</h2>
        <div class="flex">
            <div class="form-section w-7/10 p-4">
                <form action="UpdateProfileServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="filCode" value="<%= request.getParameter("filCode") %>">
                    <div class="mt-2">
                        <label for="username" class="block text-sm font-medium">Pseudo</label>
                        <input type="text" name="username" id="username" value="<%= utilisateur.getNom() %>" class="mt-1 w-full p-2 bg-gray-700 text-white rounded" required>
                    </div>
                    <div class="mt-2">
                        <label for="email" class="block text-sm font-medium">Email</label>
                        <input type="email" name="email" id="email" value="<%= utilisateur.getEmail() %>" class="mt-1 w-full p-2 bg-gray-700 text-white rounded" required>
                    </div>
                    <div class="mt-2">
                        <label for="currentPassword" class="block text-sm font-medium">Ancien mot de passe</label>
                        <input type="password" name="currentPassword" id="currentPassword" class="mt-1 w-full p-2 bg-gray-700 text-white rounded">
                    </div>
                    <div class="mt-2">
                        <label for="password" class="block text-sm font-medium">Nouveau mot de passe</label>
                        <input type="password" name="password" id="password" class="mt-1 w-full p-2 bg-gray-700 text-white rounded">
                    </div>
                    <div class="mt-2">
                        <label for="confirmPassword" class="block text-sm font-medium">Confirmer le mot de passe</label>
                        <input type="password" name="confirmPassword" id="confirmPassword" class="mt-1 w-full p-2 bg-gray-700 text-white rounded">
                    </div>
                    <button type="submit" class="mt-4 bg-light-pink text-white px-4 py-2 rounded">Enregistrer les modifications</button>
                </form>
            </div>
            <div class="separator w-1 bg-gray-600"></div>
                <div class="file-upload-section w-3/10 p-4">
                    <div class="file-upload">
                        <h2 class="text-title">Photo de profil</h2>
                        <div class="profile-image">
                            <img src="ProfileImageServlet?id=<%= utilisateur.getId() %>" alt="Profile" onerror="this.style.display='none';" class="w-24 h-24 rounded-full">
                        </div>
                        <label for="fileInput" class="mt-2 bg-light-pink text-white px-2 py-1 rounded cursor-pointer">Choisir une photo de profil</label>
                        <input type="file" name="profileImage" accept="image/*" id="fileInput" class="mt-1 w-full p-2 bg-gray-700 text-white rounded">
                    </div>
                </div>
            </div>
        </div>
        <br>
        <a href="main.jsp?filCode=<%= request.getParameter("filCode") %>" class="mt-2 white">Retour</a>
    </div>
</body>
</html>


    <script>
        document.querySelector('form').addEventListener('submit', function(event) {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            let errorMessage = '';

            if (currentPassword && (!newPassword || !confirmPassword)) {
                errorMessage = "Veuillez remplir les champs 'Nouveau mot de passe' et 'Confirmer le mot de passe' si vous avez saisi un ancien mot de passe.";
            } else if (newPassword !== confirmPassword) {
                errorMessage = "Les mots de passe ne correspondent pas.";
            }

            if (errorMessage) {
                alert(errorMessage); // Affiche le message d'erreur dans une popup
                event.preventDefault(); // Empêche l'envoi du formulaire
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