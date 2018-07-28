package ru.grigorev.javacore.server;

import ru.grigorev.javacore.ChatCommands;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name = "";
    private boolean isAuthorized = false;

    public ClientHandler(final Server server, final Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            sendMessage(ChatCommands.GREETINGS_AND_AUTHORIZE);
            sendMessage(ChatCommands.ALLOWED_COMMANDS);
            sendMessage(ChatCommands.WARNING_TIMEOUT_KICK);
            getClientHandlerThread().start();
            getDisconnectTimeoutThread().start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public String getName() {
        return name;
    }

    public Thread getClientHandlerThread() {
        return new Thread(() -> {
            try {
                authorizingClients();
                readingMessages();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                server.unsubscribe(this);
                try {
                    closeAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendMessage(final String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void authorizingClients() throws IOException {
        while (true) {
            final String str = in.readUTF();
            if (str.startsWith(ChatCommands.COMMAND_AUTH)) {
                final String[] parts = str.split("\\s");
                final String nickname = server.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nickname != null) {
                    if (!server.isNickBusy(nickname)) {
                        name = nickname;
                        server.subscribe(this);
                        isAuthorized = true;
                        break;
                    } else sendMessage(ChatCommands.ACCOUNT_IN_USE);
                } else {
                    sendMessage(ChatCommands.WRONG_LOGIN_OR_PASSWORD);
                }
            }

            if (str.startsWith(ChatCommands.COMMAND_SIGN_UP)) {
                final String[] parts = str.split("\\s");
                if (parts.length != 4) {
                    sendMessage(ChatCommands.TRY_AGAIN);
                    continue;
                }
                final String nickname = parts[1];
                final String login = parts[2];
                final String password = parts[3];
                if (server.getAuthService().isLoginFree(login)) {
                    if (server.getAuthService().isNickFree(nickname)) {
                        server.getAuthService().addNewUser(nickname, login, password);
                        name = nickname;
                        server.subscribe(this);
                        isAuthorized = true;
                        break;
                    } else sendMessage(ChatCommands.NICKNAME_IS_IN_USE);
                } else sendMessage(ChatCommands.LOGIN_IS_IN_USE);
            }
            sendMessage(ChatCommands.NOT_AUTHORIZED);
        }
    }

    public void readingMessages() throws IOException {
        while (true) {
            final String str = in.readUTF();
            if (str.startsWith("/")) {
                if (str.startsWith(ChatCommands.COMMAND_END)) break;
                if (str.startsWith(ChatCommands.COMMAND_PRIVATE)) {
                    final String[] parts = str.split("\\s");
                    final String nick = parts[1];
                    final String message = str.substring(4 + nick.length());
                    server.sendMsgToClient(this, nick, message);
                }
                if (str.startsWith(ChatCommands.COMMAND_CLIENTS)) {
                    sendMessage(server.getClientList());
                }
            } else {
                server.broadcastMessage(name + ": " + str);
            }
        }
    }

    public Thread getDisconnectTimeoutThread() {
        return new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(120_000); // 2 minutes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isAuthorized) {
                    break;
                } else {
                    try {
                        sendMessage(ChatCommands.SERVER_HAS_CLOSED_CONNECTION);
                        closeAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void closeAll() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
