package ru.grigorev.javacore.server.service;

public interface AuthService {

    void start();

    String getNickByLoginPass(String login, String pass);

    void stop();

}
