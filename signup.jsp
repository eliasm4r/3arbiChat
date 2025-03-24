<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <title>Inscription - 3arbiChat</title>
    <style>
        body {
            background-color: #1a1a2e; /* couleur de fond sombre */
        }
        .bg-custom {
            background-color: #6a0572; 
        }
        .text-custom {
            color: #ff95ca; 
        }
        .border-custom {
            border-color: #ff95ca 
        }
        .placeholder-custom::placeholder {
            color: #d1d1d1;
        }
    </style>
</head>
<body>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
          <h2 class="mt-10 text-center text-4xl font-bold tracking-tight text-custom">Créer un compte</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form class="space-y-6" action="./Register" method="POST">
            <div>
              <label for="username" class="block text-sm/6 font-medium text-gray-300">Nom d'utilisateur</label>
              <div class="mt-2">
                <input type="text" name="username" id="username" required class="block w-full rounded-md border border-custom bg-gray-800 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 placeholder-custom focus:outline-2 focus:-outline-offset-2 focus:outline-custom sm:text-sm/6">
              </div>
            </div>

            <div>
              <label for="email" class="block text-sm/6 font-medium text-gray-300">Adresse e-mail</label>
              <div class="mt-2">
                <input type="email" name="email" id="email" required class="block w-full rounded-md border border-custom bg-gray-800 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 placeholder-custom focus:outline-2 focus:-outline-offset-2 focus:outline-custom sm:text-sm/6">
              </div>
            </div>

            <div>
              <label for="password" class="block text-sm/6 font-medium text-gray-300">Mot de passe</label>
              <div class="mt-2">
                <input type="password" name="passwd" id="passwd" required class="block w-full rounded-md border border-custom bg-gray-800 px-3 py-1.5 text-base text-white outline-1 -outline-offset-1 placeholder-custom focus:outline-2 focus:-outline-offset-2 focus:outline-custom sm:text-sm/6">
              </div>
            </div>

            <div>
              <button type="submit" class="flex w-full justify-center rounded-md bg-custom px-3 py-1.5 text-sm/6 font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-custom">S'inscrire</button>
            </div>
          </form>

          <div class="mt-10 text-center text-sm text-gray-400">
              Déjà inscrit?
              <form action="./login.jsp" method="post" class="inline">
                  <button type="submit" class="font-semibold text-custom hover:text-indigo-500">
                      Connectez-vous ici
                  </button>
              </form>
          </div>
        </div>
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