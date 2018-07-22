package ru.grigorev.javacore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Dmitriy Grigorev
 */
public abstract class ClientBehavior {
    private boolean isRunning = true;
    private String message = "";

    public Thread readMessages(final Scanner inStream) {
        return new Thread(() -> {
            try {
                while (isRunning) {
                    if (inStream.hasNext()) {
                        message = inStream.nextLine();
                        if (message.equalsIgnoreCase("end")) {
                            isRunning = false;
                        }
                        System.out.println(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Thread sendMessages(final PrintWriter outToClientStream) {
        return new Thread(() -> {
            final BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            while (isRunning) {
                try {
                    message = keyboard.readLine();
                    outToClientStream.println(message);
                    outToClientStream.flush();
                    if (message.equalsIgnoreCase("end")) {
                        isRunning = false;
                        keyboard.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}