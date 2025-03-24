<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Connexion réussie - 3arbiChat</title>
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
        .text-custom {
            color: #ff95ca; 
        }
    </style>
</head>
<body>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 class="mt-10 text-center text-4xl font-bold tracking-tight text-custom">Connexion réussie</h2>
            <p class="mt-4 text-center text-lg text-gray-300">Bienvenue, <%= request.getParameter("username") %>!</p>
        </div>
    </div>
</body>
</html>