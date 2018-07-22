package ru.grigorev.javacore;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Dmitriy Grigorev
 */
public final class Client extends ClientBehavior {
    private Socket socket;
    private Scanner inFromServerStream;
    private PrintWriter outToServerStream;

    public Client(final String ip, final int port) {
        try {
            initClientConnection(ip, port);
        } catch (IOException e) {
            System.out.println("Ошибка подключения!");
            e.printStackTrace();
        }

        final Thread readThread = readMessages(inFromServerStream);
        final Thread sendThread = sendMessages(outToServerStream);
        readThread.start();
        sendThread.start();

        try {
            readThread.join();
            sendThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
            outToServerStream.close();
            inFromServerStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initClientConnection(final String ip, final int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Подключился к серверу");
        inFromServerStream = new Scanner(socket.getInputStream());
        outToServerStream = new PrintWriter(socket.getOutputStream());
        System.out.println("Введите сообщение серверу (\"end\" - завершает работу программы): ");
    }
}