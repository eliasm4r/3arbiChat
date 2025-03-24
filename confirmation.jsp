<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Confirmation - 3arbiChat</title>
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
    </style>
</head>
<body class="bg-gray-800 text-white">
    <div class="max-w-md mx-auto mt-10 p-4 bg-gray-900 rounded">
        <%
            String errorMessage = request.getParameter("error");
            if (errorMessage != null) {
        %>
            <h2 class="text-lg font-bold text-red-500">Erreur</h2>
            <p class="mt-2">Le code d'accès est déjà pris par un autre fil.</p>
        <%
            } else {
        %>
            <h2 class="text-lg font-bold">Le fil a bien été créé !</h2>
            <p class="mt-2">Vous pouvez maintenant discuter dans votre nouveau fil.</p>
        <%
            }
        %>
        <br>
        <a href="main.jsp" class="mt-4 bg-pink-500 text-white px-4 py-2 rounded">Retour à la page principale</a>
    </div>
</body>
</html>

<script>
    document.querySelector('form').addEventListener('submit', function(event) {
            const inputs = document.querySelectorAll('input[type="text"], input[type="email"], input[type="password"]');

            inputs.forEach(input => {
                const value = input.value;
                input.value = value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
            });
        });
</script>