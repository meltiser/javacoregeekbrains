package ru.grigorev.javacore.server;

import ru.grigorev.javacore.server.service.AuthService;
import ru.grigorev.javacore.server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final int PORT = 8189;
    private ServerSocket server;
    private List<ClientHandler> clients;
    private AuthService authService;

    public MyServer() {
        try {
            server = new ServerSocket(PORT);
            Socket socket = null;
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    public static void main(String[] args) {
        new MyServer();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }

    // реализация дз
    public synchronized void sendPrivateMessage(final ClientHandler o, final String message) {
        o.sendMsg(message);
    }

    public synchronized ClientHandler getClientByNick(final String nick) {
        for (final ClientHandler client : clients) {
            if (nick.equalsIgnoreCase(client.getName())) {
                return client;
            }
        }
        return null;
    }
}
