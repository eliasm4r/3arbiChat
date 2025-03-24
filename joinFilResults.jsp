<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="MVC.model.pojo.FilDiscussion" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Résultats de Recherche - 3arbiChat</title>
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
        .bg-custom {
            background-color: #6a0572; 
        }
        .bg-light-pink {
            background-color: #ff95ca;
        }
    </style>
</head>
<body class="bg-gray-800 text-white">
    <div class="max-w-md mx-auto mt-10 p-4 bg-gray-900 rounded">
        <h2 class="text-lg font-bold">Résultats de Recherche</h2>
        <ul>
            <% 
                List<FilDiscussion> fils = (List<FilDiscussion>) request.getAttribute("fils");
                for (FilDiscussion fil : fils) {
            %>
                <li class="mt-2">
                    <%= fil.getTitre() %>
                    <form action="JoinFilActionServlet" method="post" class="inline">
                        <input type="hidden" name="filId" value="<%= fil.getId() %>">
                        <button type="submit" class="bg-light-pink text-white px-2 py-1">Rejoindre</button>
                    </form>
                </li>
            <% } %>
        </ul>
        <a href="main.jsp" class="mt-2 text-blue-300">Retour</a>
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