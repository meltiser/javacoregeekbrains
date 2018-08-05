package ru.grigorev.javacore.server;

import ru.grigorev.javacore.ChatCommands;
import ru.grigorev.javacore.server.service.AuthService;
import ru.grigorev.javacore.server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket server;
    private List<ClientHandler> clients;
    private AuthService authService;
    private Socket socket;
    private boolean isRunning;

    public Server(final int port) {
        try {
            server = new ServerSocket(port);
            authService = new BaseAuthService();
            clients = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Ошибка при инициализации сервера");
            e.printStackTrace();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized void sendMsgToClient(final ClientHandler nickFrom, final String nickTo, final String message) {
        for (final ClientHandler client : clients) {
            if (client.getName().equals(nickTo)) {
                client.sendMessage(ChatCommands.PRIVATE_FROM + nickFrom.getName() + ": " + message);
                nickFrom.sendMessage(ChatCommands.PRIVATE_TO + nickTo + ": " + message);
                return;
            }
        }
        nickFrom.sendMessage(nickTo + ChatCommands.USER_IS_ABSENT);
    }

    public synchronized void broadcastClientList() {
        String message = ChatCommands.CLIENTS_IN_CHAT;
        for (ClientHandler client : clients) {
            message += client.getName() + " ";
        }
        broadcastMessage(message);
    }

    public synchronized String getClientList() {
        String message = ChatCommands.CLIENTS_IN_CHAT;
        for (ClientHandler client : clients) {
            message += client.getName() + " ";
        }
        return message;
    }

    public synchronized void unsubscribe(final ClientHandler client) {
        clients.remove(client);
        broadcastMessage(client.getName() + ChatCommands.LEFT_THE_CHAT);
        broadcastClientList();
    }

    public synchronized void subscribe(final ClientHandler client) {
        clients.add(client);
        broadcastMessage(client.getName() + ChatCommands.ENTERED_THE_CHAT);
        broadcastClientList();
    }

    public synchronized boolean isNickBusy(final String nick) {
        for (final ClientHandler client : clients) {
            if (client.getName().equals(nick)) return true;
        }
        return false;
    }

    public synchronized void broadcastMessage(final String message) {
        for (final ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void start() {
        isRunning = true;
        try {
            while (isRunning) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при подключении клиента к серверу");
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
