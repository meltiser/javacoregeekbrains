package ru.grigorev.javacore;

import ru.grigorev.javacore.server.Server;

/**
 * @author Dmitriy Grigorev
 */
public class RunServerApplication {
    public static void main(String[] args) {
        final Server server = new Server(8189);
        server.start();
    }
}
