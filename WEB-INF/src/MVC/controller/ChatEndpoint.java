package MVC.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static Set<Session> clients = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        for (Session client : clients) {
            if (client.isOpen() && client != session) {
                try {
                    client.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void broadcastMessage(String messageContent, int auteurId) {
        for (Session client : clients) {
            if (client.isOpen()) {
                try {
                    String message = "Utilisateur " + auteurId + ": " + messageContent; // Format du message
                    client.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}