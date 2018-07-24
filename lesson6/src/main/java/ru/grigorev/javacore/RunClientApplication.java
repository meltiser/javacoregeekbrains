package ru.grigorev.javacore;

/**
 * @author Dmitriy Grigorev
 */
public final class RunClientApplication {
    public static void main(String[] args) {
        final Client client = new Client("localhost", 8189);
    }
}