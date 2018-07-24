package ru.grigorev.javacore;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Dmitriy Grigorev
 */
public final class Server extends ClientBehavior {
    private ServerSocket server;
    private Socket socket;
    private Scanner inFromClientStream;
    private PrintWriter outToClientStream;

    public Server(final int port) {
        try {
            initServer(port);
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера");
            e.printStackTrace();
        }

        final Thread readingThread = readMessages(inFromClientStream);
        final Thread sendingThread = sendMessages(outToClientStream);
        readingThread.start();
        sendingThread.start();

        try {
            readingThread.join();
            sendingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
            server.close();
            inFromClientStream.close();
            outToClientStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initServer(final int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Сервер запущен, ожидаем подключения...");
        socket = server.accept();
        System.out.println("Клиент подключился");
        inFromClientStream = new Scanner(socket.getInputStream());
        outToClientStream = new PrintWriter(socket.getOutputStream());
        System.out.println("Введите сообщение клиенту (\"end\" - завершает работу программы): ");
    }
}