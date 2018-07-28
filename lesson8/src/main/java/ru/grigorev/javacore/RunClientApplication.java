package ru.grigorev.javacore;

import ru.grigorev.javacore.client.Client;

/**
 * @author Dmitriy Grigorev
 */
public class RunClientApplication {
    public static void main(String[] args) {
        final Client client = new Client("localhost", 8189);
        client.start();
    }
}
