<%@ page pageEncoding="UTF-8" %>
<%@ page import="MVC.model.pojo.Utilisateur" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Rejoindre un Fil - 3arbiChat</title>
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
        .bg-light-pink {
            background-color: #ff95ca;
        }
        input[type="text"] {
            color: black; /* Couleur du texte en noir */
        }
    </style>
</head>
<body class="bg-gray-800 text-white">
    <div class="max-w-md mx-auto mt-10 p-4 bg-gray-900 rounded">
        <h2 class="text-lg font-bold">Rejoindre un Fil de Discussion</h2>
        <form action="JoinFilServlet" method="post">
            <input type="text" name="code" class="mt-2 w-full p-2" placeholder="Code du fil" required>
            <button type="submit" class="mt-4 bg-light-pink text-white px-4 py-2">Rejoindre</button>
        </form>
        <br>
        <a href="main.jsp" class="mt-2 white">Retour</a>
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